package com.example.splashscreen;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class SplashFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        new Handler().postDelayed(new Runnable() {
            public void run() {
                Navigation.findNavController(getView()).navigate(R.id.viewPagerFragment);
            }
        }, 5000);




                return inflater.inflate(R.layout.fragment_splash, container, false);
    }
}