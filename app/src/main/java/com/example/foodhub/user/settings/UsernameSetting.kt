package com.example.foodhub.user.settings

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentUsernameSettingBinding
import com.example.foodhub.login.User
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.DonateViewModal
import com.example.foodhub.user.fragments.SettingsFragment
import com.example.foodhub.user.viewmodels.UserVolunteeredWorkViewModel

class UsernameSetting : Fragment() {

    private var _binding: FragmentUsernameSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DonateViewModal by activityViewModels()
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserVolunteeredWork: UserVolunteeredWorkViewModel
    private lateinit var user : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsernameSettingBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserVolunteeredWork = ViewModelProvider(this)[UserVolunteeredWorkViewModel::class.java]
        binding.changeUsernameBtn.setOnClickListener{
            updateUsername()
            findNavController().navigate(R.id.settingsFragment)
        }

        @Suppress("DEPRECATION")
        user = activity?.intent?.getParcelableExtra("User")!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
    }

    private fun updateUsername() {
        val newName = binding.changeUsernameTE.text.toString()
        if (inputCheck(newName)){
            for (x in 1..10){
                if(dbCheck(newName)){
                    mUserViewModel.updateDonationUser(user.loginID, newName)
                    mUserViewModel.updateUsername(user.id, newName)
                    mUserVolunteeredWork.updateVWUserID(user.loginID, newName)
                    viewModel.name.value = newName

                }else {
                    Toast.makeText(requireContext(), "Name already used", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }

    private fun dbCheck(id: String) : Boolean {
        val readUser = mUserViewModel.updateUsernameCheck(id)
        if (readUser == null){
            return true
        }else {
            return false
        }
    }


}