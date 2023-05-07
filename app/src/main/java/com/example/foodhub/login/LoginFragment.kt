package com.example.foodhub.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentLoginBinding
import com.example.foodhub.user.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

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
            hideKeyboard()

        }

        binding.loginScrBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_preLogin)
        }

        return binding.root
    }

    private fun validateUser() {
        var warn : Boolean = false
        val readID = binding.loginIDTE.text.toString()
        val readPW = binding.loginPWTE.text.toString()
        var user = mUserViewModel.getUser(readID)
        if (inputCheck(readID, readPW)){
            binding.loadingOverlay.visibility = View.VISIBLE
            binding.loadingProgress.visibility = View.VISIBLE
            var retry = 0
            while (user.loginID != readID || retry <= 2) {
                user = mUserViewModel.getUser(readID)
                if (readID == user.loginID && readID != "admin") {
                    if (readPW == user.password) {
                        warn = false
                        requireActivity().run {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }else {
                        warn = true
                    }
                } else if (readID == user.loginID && readID == "admin") {
                    if (readPW == user.password) {
                        warn = false
                        requireActivity().run {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                    else {
                        warn = true
                    }
                }
                else{
                    warn = true
                }
                retry++
            }
            if (warn) {
                binding.loadingOverlay.visibility = View.INVISIBLE
                binding.loadingProgress.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Wrong Password or Name", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addAdmin() {
        val user = User(1,"admin", "admin")
        mUserViewModel.addUser(user)
    }

    private fun inputCheck(id: String, pw: String): Boolean {
        return !(TextUtils.isEmpty(id) && TextUtils.isEmpty(pw))
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}