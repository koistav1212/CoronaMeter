package com.example.myapplication.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class Secondscreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_secondscreen, container, false)

        val viewPager =activity?.findViewById<ViewPager2>(R.id.viewPager)
        view.findViewById<TextView>(R.id.next2).setOnClickListener { viewPager?.currentItem=2 }
        return view
    }

}