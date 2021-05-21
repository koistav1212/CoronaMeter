package com.example.CoronaMeter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class myaccount extends AppCompatActivity {
    Button signup,go;
    TextView splash_dialogue,app_name;
    TextInputLayout regnum,regpassword;
    private static final String TAG = "LoginRegisterActivity";
    int AUTHUI_REQUEST_CODE = 10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                startActivity(new Intent(this, userProfile.class));

            }

        }

        public void handleLoginRegister(View view) {

            List<AuthUI.IdpConfig> providers = Arrays.asList(

                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build()
            );

            Intent intent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                    .setLogo(R.drawable.speedometer)
                    .setAlwaysShowSignInMethodScreen(true)
                    .setIsSmartLockEnabled(false)
                    .build();

            startActivityForResult(intent, AUTHUI_REQUEST_CODE);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == AUTHUI_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // We have signed in the user or we have a new user
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.d(TAG, "onActivityResult: " + user.toString());
                    //Checking for User (New/Old)
                    if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                        //This is a New User
                    } else {
                        //This is a returning user
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);


                } else {
                    // Signing in failed
                    IdpResponse response = IdpResponse.fromResultIntent(data);
                    if (response == null) {
                        Log.d(TAG, "onActivityResult: the user has cancelled the sign in request");
                    } else {
                        Log.e(TAG, "onActivityResult: ", response.getError());
                    }
                }
            }
        }
    }
