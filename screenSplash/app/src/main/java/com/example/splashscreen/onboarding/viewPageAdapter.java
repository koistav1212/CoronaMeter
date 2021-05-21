package com.example.splashscreen.onboarding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class viewPageAdapter extends FragmentStateAdapter {
ArrayList <Fragment> list;

    public viewPageAdapter(List<Fragment> fragmentList, FragmentManager fm, Lifecycle lifecycle) {

        super(fm,lifecycle);
    }

 ArrayList Fragmentlist=list;

    @NonNull
    @Override
    public Fragment createFragment(int position)  {

        return (Fragment) Fragmentlist.get(position);
    }

    @Override
    public int getItemCount() {
        return Fragmentlist.size();
    }
}
