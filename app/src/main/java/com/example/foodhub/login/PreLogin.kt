package com.example.foodhub.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentLoginBinding
import com.example.foodhub.databinding.FragmentPreLoginBinding
import com.example.foodhub.user.MainActivity

class PreLogin : Fragment() {

    private lateinit var binding : FragmentPreLoginBinding

    private lateinit var lContext: LoginActivity
    private var doubleBackToExitPressedOnce = false

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

    @Suppress("DEPRECATION")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        lContext = context as LoginActivity

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    activity?.finishAffinity()
                }
                else {
                    Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                }
                doubleBackToExitPressedOnce = true
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}