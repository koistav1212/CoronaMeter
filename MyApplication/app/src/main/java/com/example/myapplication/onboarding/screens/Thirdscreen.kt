package com.example.myapplication.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R

class Thirdscreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val view = inflater.inflate(R.layout.fragment_thirdscreen,container, false)


        view.findViewById<TextView>(R.id.finish).setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_homeFragment)
            onBoardingFinished()
        }

        return view
    }
  private fun onBoardingFinished()
  {
      val screenPref=requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
      val editor=screenPref.edit()
      editor.putBoolean("Finished",true)
      editor.apply()
  }
}