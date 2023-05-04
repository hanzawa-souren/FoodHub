package com.example.foodhub.user.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentLoginBinding
import com.example.foodhub.databinding.FragmentPreLoginBinding

class PreLogin : Fragment() {

    private var _binding: FragmentPreLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPreLoginBinding.inflate(inflater, container, false)

        binding.preLogLoginBtn.setOnClickListener{
            findNavController().navigate(R.id.action_preLogin_to_loginFragment)
        }

        binding.preLogRegButton.setOnClickListener{
            findNavController().navigate(R.id.action_preLogin_to_registerFragment)
        }

        return binding.root
    }

}