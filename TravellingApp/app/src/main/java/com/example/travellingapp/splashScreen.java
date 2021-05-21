package com.example.travellingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashScreen extends AppCompatActivity {
    TextView splash_dialogue, app_name;
    ImageView splash_image;
    Animation top_anim, bottom_anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash_dialogue = findViewById(R.id.splash_dialogue1);
        app_name = findViewById(R.id.app_name1);
        splash_image = findViewById(R.id.splash_img);
        top_anim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom_anim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        splash_image.setAnimation(top_anim);
        app_name.setAnimation(bottom_anim);
        splash_dialogue.setAnimation(bottom_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashScreen.this,
                        MainActivity.class);
               startActivity(intent);
                  finish();
                //the current activity will get finished.
            }
        }, 3000);

}
}