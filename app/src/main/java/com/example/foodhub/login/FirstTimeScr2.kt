package com.example.foodhub.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentFirstTimeScr1Binding
import com.example.foodhub.databinding.FragmentFirstTimeScr2Binding

class FirstTimeScr2 : Fragment() {


private lateinit var binding: FragmentFirstTimeScr2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_time_scr2, container, false)

        binding.firstNextBtn2.setOnClickListener {
            findNavController().navigate(R.id.preLogin)
        }

        binding.firstBackBtn.setOnClickListener {
            findNavController().navigate(R.id.firstTimeScr1)
        }


        return binding.root
    }

}