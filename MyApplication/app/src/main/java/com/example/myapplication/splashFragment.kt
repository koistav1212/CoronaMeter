package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class splashFragment : Fragment() {
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     Handler().postDelayed({
         if(onBoardingFinished())
         {
             findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
         }
         else
         {
             findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
         }

     },3000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    private fun onBoardingFinished():Boolean {
        val screenPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    return screenPref.getBoolean("Finished",false )
    }
}