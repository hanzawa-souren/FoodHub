package com.example.foodhub.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentFirstTimeScr1Binding

class FirstTimeScr1 : Fragment() {

    private lateinit var binding: FragmentFirstTimeScr1Binding
    private lateinit var pref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_time_scr1, container, false)


        //Getting the sharedPreferences
        val appContext = requireContext().applicationContext
        var pref = appContext.getSharedPreferences("firstTime", Context.MODE_PRIVATE)
        val prefEditor = pref.edit()
        if (pref.getBoolean("firstTime", true)) { // use default of true
//            prefEditor.putBoolean("firstTime", false)
//            prefEditor.apply()
        } else {
            findNavController().navigate(R.id.preLogin)
        }

        binding.firstNextBtn.setOnClickListener {
            findNavController().navigate(R.id.firstTimeScr2)
        }


        return binding.root
    }



}