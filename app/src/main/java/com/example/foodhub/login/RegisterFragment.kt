package com.example.foodhub.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        binding.regConfirmButton.setOnClickListener {
            insertUser()
        }

        val spinner = binding.phoneSpinner
        spinner?.adapter = ArrayAdapter.createFromResource(this.requireActivity(), R.array.phone_Country, android.R.layout.simple_spinner_item) as SpinnerAdapter

        binding.regScrBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_preLogin)
        }

        return binding.root
    }

    private fun insertUser() {
        val loginID = binding.regIDTE.text.toString()
        val pw = binding.regPWTE.text.toString()
        if (inputCheck(loginID, pw)){
            val user = User(loginID, pw)
            mUserViewModel.addUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_preLogin)
        }
        else {
            Toast.makeText(requireContext(), "Please fill up all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(id: String, pw: String): Boolean {
        return !(TextUtils.isEmpty(id) && TextUtils.isEmpty(pw))
    }

}