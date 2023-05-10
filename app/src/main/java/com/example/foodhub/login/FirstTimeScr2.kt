package com.example.foodhub.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentFirstTimeScr1Binding
import com.example.foodhub.databinding.FragmentFirstTimeScr2Binding

class FirstTimeScr2 : Fragment() {


    private var _binding: FragmentFirstTimeScr2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstTimeScr2Binding.inflate(inflater, container, false)

        binding.firstNextBtn2.setOnClickListener {
            findNavController().navigate(R.id.preLogin)
        }

        binding.firstBackBtn.setOnClickListener {
            findNavController().navigate(R.id.firstTimeScr1)
        }


        return binding.root
    }

}