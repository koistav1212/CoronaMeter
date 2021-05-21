package com.example.splashscreen.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.example.splashscreen.R;
import com.example.splashscreen.onboarding.screens.firstScreen;
import com.example.splashscreen.onboarding.screens.secondScreen;
import com.example.splashscreen.onboarding.screens.thirdScreen;

import java.util.ArrayList;
import java.util.List;


public class viewPagerFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view= inflater.inflate(R.layout.fragment_view_pager, container, false);
         List fragmentList=new ArrayList();
        fragmentList.add(new firstScreen());
        fragmentList.add(new secondScreen());
        fragmentList.add(new thirdScreen());

        viewPageAdapter adapter= new viewPageAdapter(fragmentList,requireActivity().getSupportFragmentManager(),getLifecycle());
        ViewPager2 viewPager2=view.findViewById(R.id.viewpager);
        viewPager2.setAdapter(adapter);


       return view;
    }
}