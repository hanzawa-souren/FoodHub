package com.example.foodhub.user.settings

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentUsernameSettingBinding
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.MainActivity

class UsernameSetting : Fragment() {

    private var _binding: FragmentUsernameSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsernameSettingBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.changeUsernameBtn.setOnClickListener{
            updateUsername()
            findNavController().navigate(R.id.settingsFragment)
        }

        return binding.root
    }

    private fun updateUsername() {
        val newName = binding.changeUsernameTE.text.toString()
        var curName : String = ""
        requireActivity().run {
            val sharedPreference =  getSharedPreferences("tempUser",Context.MODE_PRIVATE)
            curName = sharedPreference.getString("Username", "").toString()
        }
        if (inputCheck(newName)){
            mUserViewModel.updateUsername(curName, newName)
        }

    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }


}