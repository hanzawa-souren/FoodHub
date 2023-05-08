package com.example.foodhub.user.settings

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentPasswordSettingsBinding
import com.example.foodhub.login.User
import com.example.foodhub.login.UserViewModel

class passwordSettings : Fragment() {

    private var _binding: FragmentPasswordSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordSettingsBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.changePasswordBtn.setOnClickListener{
            updatePassword()
            findNavController().navigate(R.id.settingsFragment)
        }

        @Suppress("DEPRECATION")
        user = activity?.intent?.getParcelableExtra("User")!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
    }


    private fun updatePassword() {
        val newPass = binding.changePasswordTE.text.toString()
        if (inputCheck(newPass)){
            mUserViewModel.updatePassword(user.loginID, newPass)
        }

    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }
}