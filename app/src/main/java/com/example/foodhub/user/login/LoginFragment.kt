package com.example.foodhub.user.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentLoginBinding
import com.example.foodhub.user.MainActivity

class LoginFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        addAdmin()

        binding.loginButton.setOnClickListener {
            validateUser()
        }

        binding.loginScrBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_preLogin)
        }

        return binding.root
    }

    private fun validateUser() {
        val readID = binding.loginIDTE.text.toString()
        val readPW = binding.loginPWTE.text.toString()
        val user = mUserViewModel.getUser(readID)
        if (inputCheck(readID, readPW)){
                if (readID == user.loginID && readID != "admin") {
                    if (readPW == user.password) {
                        requireActivity().run {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                } else if (readID == user.loginID && readID == "admin") {
                    if (readPW == user.password) {
                        requireActivity().run {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Wrong ID or Password", Toast.LENGTH_LONG)
                        .show()
                }
        }else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun addAdmin() {
        val user = User("admin", "admin")
        mUserViewModel.addUser(user)
    }

    private fun inputCheck(id: String, pw: String): Boolean {
        return !(TextUtils.isEmpty(id) && TextUtils.isEmpty(pw))
    }

}