package com.example.CoronaMeter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class splashScreen extends AppCompatActivity {
LottieAnimationView lottie;
TextView app_name,description;
ImageView app_logo,back;
public static final int numpages=3;
private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       getSupportActionBar().hide();
        lottie=findViewById(R.id.lottie);
        app_name=findViewById(R.id.app_name);
        description=findViewById(R.id.description);
        app_logo=findViewById(R.id.app_logo);
        back=findViewById(R.id.img);
        back.animate().translationY(-3000).setDuration(1000).setStartDelay(2000);
        lottie.animate().translationY(2000).setDuration(1000).setStartDelay(2000);
        app_logo.animate().translationY(1400).setDuration(1000).setStartDelay(2000);
        description.animate().translationY(1400).setDuration(1000).setStartDelay(2000);
        app_name.animate().translationY(1400).setDuration(1000).setStartDelay(2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashScreen.this, onBoard.class);
                startActivity(i);
                finish();
            }
        }, 2750);
    }


}