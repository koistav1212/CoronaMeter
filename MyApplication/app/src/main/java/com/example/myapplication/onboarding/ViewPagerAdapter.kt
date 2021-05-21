package com.example.myapplication.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(list: ArrayList<Fragment>, fm: FragmentManager, lifecycle :Lifecycle) :FragmentStateAdapter(fm,lifecycle) {

   private val fragmetList = list
    override fun getItemCount(): Int {
       return fragmetList.size
    }

    override fun createFragment(position: Int): Fragment {
    return fragmetList[position]
    }

}