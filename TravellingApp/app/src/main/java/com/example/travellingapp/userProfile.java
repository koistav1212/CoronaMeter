package com.example.travellingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class userProfile extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    ImageView profileImageView;
    Button update;
    EditText fullName,email,password,fullNameLabel,usernameLabel,phoneNo,username;
    String user_username,user_name,user_email,user_phoneNo,user_password;
 DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profileImageView = findViewById(R.id.profileImageView);
//Hooks
        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email2);
        phoneNo = findViewById(R.id.phn3);
        update=findViewById(R.id.update);
        password = findViewById(R.id.password2);
        reference = FirebaseDatabase.getInstance().getReference("users");
        fullNameLabel = findViewById(R.id.full_name_label);
        usernameLabel = findViewById(R.id.user_name_label);
 showAllUserData();
       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               update(v);
           }
       });
    }
    private void showAllUserData() {
        Intent intent = getIntent();
        user_username = intent.getStringExtra("username");
       user_name = intent.getStringExtra("name");
        user_email = intent.getStringExtra("email");
         user_phoneNo = intent.getStringExtra("phone_no");
         user_password = intent.getStringExtra("password");
        fullNameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        fullName.setText(user_name);
        email.setText(user_email);
        phoneNo.setText(user_phoneNo);
        password.setText(user_password);

    }

    public void update(View view){
        if(isNameChanged()==true || isPasswordChanged()==true)
        {
            Toast.makeText(this, "Data Has been modified", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Data is same and can't be modified", Toast.LENGTH_SHORT).show();
    }
    private boolean isPasswordChanged(){
        if(!user_password.equals(password.getText().toString()))
        {
            reference.child(user_phoneNo).child("password").setValue(password.getText().toString());
            user_password=password.getText().toString();
            return true;
        }else{
            return false;
        }
    }
    private boolean isNameChanged(){
        if(!user_name.equals(fullName.getText().toString())){
            reference.child(user_phoneNo).child("name").setValue(fullName.getText().toString());
            user_name=fullName.getText().toString();
            return true;
        }else{
            return false;
        }

    }
}




















