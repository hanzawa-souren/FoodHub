package com.example.foodhub.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.admin.AdminMainActivity
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
            hideKeyboard()

        }

        binding.loginScrBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_preLogin)
        }

        return binding.root
    }

    private fun validateUser() {
        var tries = 0
        var wrong: Boolean = false
        var nouser : Boolean = false
        val readID = binding.loginIDTE.text.toString()
        val readPW = binding.loginPWTE.text.toString()
        if (inputCheck(readID, readPW)) {
            binding.loadingOverlay.visibility = View.VISIBLE
            binding.loadingProgress.visibility = View.VISIBLE
            while (tries <= 30) {
                var user :User? = mUserViewModel.getUser(readID)
                if (user != null) {
                    if (readID == user.loginID && readID != "admin") {
                        if (readPW == user.password) {
                            wrong = false
                            requireActivity().run {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("User", user)
                                startActivity(intent)
                                finish()
                            }
                        }
                        else {
                            wrong = true
                        }
                    } else if (readID == user.loginID && readID == "admin") {
                        if (readPW == user.password) {
                            wrong = false
                            requireActivity().run {
                                startActivity(Intent(this, AdminMainActivity::class.java))
                                finish()
                            }
                        }
                        else {
                            wrong = true
                        }
                    }
                } else  {
                    nouser = true
                }
                tries++
            }
            if (wrong) {
                binding.loadingOverlay.visibility = View.INVISIBLE
                binding.loadingProgress.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Wrong Password or Name", Toast.LENGTH_SHORT)
                    .show()
            }
            if (nouser){
                binding.loadingOverlay.visibility = View.INVISIBLE
                binding.loadingProgress.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.loadingOverlay.visibility = View.INVISIBLE
            binding.loadingProgress.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), "Please input all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addAdmin() {
        val user = User(1,"admin", "admin")
        mUserViewModel.addUser(user)
    }

    private fun inputCheck(id: String, pw: String): Boolean {
        var decision : Boolean
        decision = !(TextUtils.isEmpty(id) && TextUtils.isEmpty(pw))
        return decision
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}