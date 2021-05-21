package com.example.faceauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class  SecondActivity extends AppCompatActivity {
ImageView myPhoto;
TextView myName,myEmail;
Button signout;
 FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser mUser=mAuth.getCurrentUser();
        myPhoto=findViewById(R.id.myPhoto);
        myEmail=findViewById(R.id.myEmail);
        myName=findViewById(R.id.myName);
        signout=findViewById(R.id.sign_out_btn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mAuth.signOut();
              LoginManager.getInstance().logOut();
                finish();
            }
        });
if(mUser!=null)
{
    String name=mUser.getDisplayName();
    String email=mUser.getEmail();
    String photoUrl=mUser.getPhotoUrl().toString();
    Glide.with(this).load(photoUrl).into(myPhoto);
    myName.setText(name);
    myEmail.setText(email);

}
    }
}