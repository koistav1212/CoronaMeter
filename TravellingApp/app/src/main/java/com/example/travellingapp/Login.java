package com.example.travellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button signup,go;
    ImageView splash_image;
    TextView splash_dialogue,app_name;
    TextInputLayout regnum,regpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        signup=findViewById(R.id.signup);
        splash_image=findViewById(R.id.splash_img1);
        splash_dialogue=findViewById(R.id.splash_dialogue);
        app_name=findViewById(R.id.app_name);
        regnum=findViewById(R.id.phn_no2);
        go=findViewById(R.id.go_btn1);
        regpassword=findViewById(R.id.password);
go.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        loginuser(v);
    }
});

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,SignUp.class);
                Pair pair[]=new Pair[7];
                pair[0]=new Pair<View,String>(splash_image,"logo_image");
                pair[1]=new Pair<View,String>(app_name,"app_name");
                pair[2]=new Pair<View,String>(splash_dialogue,"splash_dialogue");
                pair[3]=new Pair<View,String>(regnum,"phn_no");
                pair[4]=new Pair<View,String>(regpassword,"password");
                pair[5]=new Pair<View,String>(signup,"signup_tran");
                pair[6]=new Pair<View,String>(go,"go_tran");
              // ActivityOptions options=  ActivityOptions.makeSceneTransitionAnimation(Login.this,pair);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean validatenum()
    {
        String val=regnum.getEditText().getText().toString();

        if(val.isEmpty())
        {
            regnum.setError("Field can not be Empty");
            return false;
        }
          else
        { regnum.setError(null);
            regnum.setErrorEnabled(false);
            return  true;

        }
    }
    private boolean validatepass()
    {
        String val=regpassword.getEditText().getText().toString();

        if(val.isEmpty())
        {
            regpassword.setError("Field can not be Empty");
            return false;
        }

        else
        { regpassword.setError(null);
            regpassword.setErrorEnabled(false);
            return  true;

        }
    }
    public void loginuser(View v)
    {
        if(!validatepass()|!validatenum())
            return;
        else
            isUser();
    }

    private void isUser() {
      final String entered_num=regnum.getEditText().getText().toString();
       final String entered_password=regpassword.getEditText().getText().toString();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users");
        Query checkUser=reference.orderByChild("phn_no").equalTo(entered_num);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if(snapshot.exists())
                   {
                       regnum.setError(null);
                       regnum.setErrorEnabled(false);
                       String passwordDB=snapshot.child(entered_num).child("password").getValue(String.class);

                       if (passwordDB.equals(entered_password)) {
                           regpassword.setError(null);
                           regpassword.setErrorEnabled(false);

                           String usernameDB=snapshot.child(entered_num).child("username").getValue(String.class);
                       String namedDB=snapshot.child(entered_num).child("name").getValue(String.class);
                       String phnDB=snapshot.child(entered_num).child("phn_no").getValue(String.class);
                       String emailDB=snapshot.child(entered_num).child("email").getValue(String.class);
                       Intent intent=new Intent(getApplicationContext(),userProfile.class);
                       intent.putExtra("name",namedDB);
                       intent.putExtra("password",passwordDB);
                       intent.putExtra("username",usernameDB);
                       intent.putExtra("phone_no",phnDB);
                       intent.putExtra("email",emailDB);

                      startActivity(intent);
                       } else {
                           regpassword.setError("Wrong PassWord");
                          regpassword.requestFocus();
                       }

                   }
                   else
                   {
                       regnum.setError("No such User Found!");
                       regnum.requestFocus();
                   }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}