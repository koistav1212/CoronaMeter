
package com.example.test7.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.test7.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        view.findViewById<Button>(R.id.btn1).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_firstFragment)
        }
        view.findViewById<Button>(R.id.btn2).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_secondFragment)
        }
        return view
    }

}


