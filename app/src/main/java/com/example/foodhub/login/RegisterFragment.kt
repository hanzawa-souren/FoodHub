package com.example.foodhub.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    private lateinit var binding: FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.regConfirmButton.setOnClickListener {
            hideKeyboard()
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
        val id = 0
        val loginID = binding.regIDTE.text.toString()
        val pw = binding.regPWTE.text.toString()
        val spinner = binding.phoneSpinner
        val phCountry = spinner.selectedItem.toString()
        val phRead = binding.editTextNumber.text.toString()
        val phNum = StringBuilder().append(phCountry).append(phRead).toString()
        if (inputCheck(loginID, pw)){
            if (telCheck(phRead)) {
                val user = User(id, loginID, pw, phNum)
                mUserViewModel.addUser(user)
                findNavController().navigate(R.id.action_registerFragment_to_preLogin)
            }
            else {
                Toast.makeText(requireContext(), "Phone Number Error", Toast.LENGTH_LONG).show()
            }
        }
        else {
            Toast.makeText(requireContext(), "Please fill up all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(id: String, pw: String): Boolean {
        return !(TextUtils.isEmpty(id) && TextUtils.isEmpty(pw))
    }

    private fun telCheck(tel: String) : Boolean{
        var count = 0
        for (i in 0..tel.length) {
            count++
        }
        Log.d("", count.toString())
        return count >= 10
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}