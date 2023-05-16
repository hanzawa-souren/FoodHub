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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.admin.AdminMainActivity
import com.example.foodhub.databinding.FragmentLoginBinding
import com.example.foodhub.user.MainActivity


class LoginFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var binding: FragmentLoginBinding
    private var firstCheck = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

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
        val readID = binding.loginIDTE.text.toString()
        val readPW = binding.loginPWTE.text.toString()

        if (inputCheck(readID, readPW)) {
            if (readID == "admin") {
                if (readPW == "admin") {

                    requireActivity().run {
                        startActivity(Intent(this, AdminMainActivity::class.java))
                        finish()
                    }
                } else {
                    binding.loadingOverlay.visibility = View.INVISIBLE
                    binding.loadingProgress.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Wrong Password or Name", Toast.LENGTH_SHORT).show()
                }
            }else {
                binding.loadingOverlay.visibility = View.VISIBLE
                binding.loadingProgress.visibility = View.VISIBLE
                mUserViewModel.getUser(readID).observe(viewLifecycleOwner, Observer { user ->
                    if (user != null) {
                        if (readID == user.loginID) {
                            if (readPW == user.password) {
                                requireActivity().run {
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("User", user)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                binding.loadingOverlay.visibility = View.INVISIBLE
                                binding.loadingProgress.visibility = View.INVISIBLE
                                Toast.makeText(requireContext(), "Wrong Password or Name", Toast.LENGTH_SHORT).show()

                            }
                        }

                    }
                    else {
                        binding.loadingOverlay.visibility = View.INVISIBLE
                        binding.loadingProgress.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                    }
                })
//                while (tries <= 70) {
//                    user = mUserViewModel.loginUser(readID)
//                    if (user != null) {
//                        nouser = false
//                        if (readID == user.loginID) {
//                            if (readPW == user.password) {
//                                wrong = false
//                                requireActivity().run {
//                                    val intent = Intent(this, MainActivity::class.java)
//                                    intent.putExtra("User", user)
//                                    startActivity(intent)
//                                    finish()
//                                }
//                            } else {
//                                wrong = true
//                            }
//                        }
//
//                    }
//                    else {
//                        nouser = true
//                    }
//                    tries++
//                }

            }

        }
        else {
            binding.loadingOverlay.visibility = View.INVISIBLE
            binding.loadingProgress.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), "Please input all fields", Toast.LENGTH_SHORT)
                .show()
        }
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