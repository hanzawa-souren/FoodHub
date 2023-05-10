package com.example.foodhub.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentLoginBinding
import com.example.foodhub.databinding.FragmentPreLoginBinding

class PreLogin : Fragment() {

    private lateinit var binding : FragmentPreLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pre_login, container, false)

        binding.preLogLoginBtn.setOnClickListener{
            findNavController().navigate(R.id.action_preLogin_to_loginFragment)
        }

        binding.preLogRegButton.setOnClickListener{
            findNavController().navigate(R.id.action_preLogin_to_registerFragment)
        }

        return binding.root
    }

}