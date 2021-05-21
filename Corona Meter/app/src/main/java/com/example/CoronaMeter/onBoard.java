package com.example.CoronaMeter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class onBoard extends AppCompatActivity {
    public static final int numpages=3;
    private ViewPager viewPager;
    private ScreenSlidePageAdapter screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onborad);
           getSupportActionBar().hide();
        viewPager=findViewById(R.id.pager);
        screen=new ScreenSlidePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(screen);
    }
    class ScreenSlidePageAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    onBoard1 tab1=new onBoard1();
                    return tab1;
                case 1:
                    onBoard2 tab2=new onBoard2();
                    return tab2;
                case 2:
                    onBoard3 tab3=new onBoard3();
                    return tab3;

            }
            return null;
        }

        @Override
        public int getCount() {
            return numpages;
        }
    }
    String prevStarted = "yes";
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            moveToSecondary();
        }
    }

    public void moveToSecondary(){
        // use an intent to travel from one activity to another.
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
