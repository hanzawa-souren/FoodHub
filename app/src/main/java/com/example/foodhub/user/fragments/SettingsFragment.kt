package com.example.foodhub.user.fragments

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R

import com.example.foodhub.databinding.FragmentSettingsBinding
import com.example.foodhub.login.LoginActivity
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.MainActivity

class SettingsFragment : Fragment() {

    private lateinit var bindingSettings: FragmentSettingsBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }
    private class CustomAdapter(context:Context) : BaseAdapter() {
        val settingOptions = arrayOf("Username","Profile Picture","Phone Number","Password","Logout")

        val optionImage = arrayOf(
            R.drawable.setting_ic_username,
            R.drawable.setting_ic_profilepic,
            R.drawable.setting_ic_call,
            R.drawable.setting_ic_password,
            R.drawable.setting_ic_logout
        )
        private val myContext:Context
        init {
            myContext = context
        }
        override fun getCount(): Int {
            return settingOptions.size
        }

        override fun getItem(p0: Int): Any {
            return settingOptions[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(myContext)
            val rowMain = layoutInflater.inflate(R.layout.row_setting,viewGroup,false)
            val option = rowMain.findViewById<TextView>(R.id.option)
            option.text = settingOptions[position]
            val optionImageShown = rowMain.findViewById<ImageView>(R.id.option_image)
            optionImageShown.setImageResource(optionImage[position])
            return rowMain


        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false)

        bindingSettings = DataBindingUtil.inflate(inflater,
            R.layout.fragment_settings, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        bindingSettings.button.setOnClickListener{
            deleteUser()
            backToLogin()
        }
        return bindingSettings.root
        // calling the action bar

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingMenu : ListView = bindingSettings.settingMenu

        settingMenu.adapter = CustomAdapter(requireContext())

        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        settingMenu.setOnItemClickListener { parent, view, position, id ->
            // Do something with the clicked item
            val selectedItem = parent.getItemAtPosition(position)
            when (selectedItem) {
                "Username" -> view.findNavController().navigate(R.id.usernameSetting)
                "Password" -> view.findNavController().navigate(R.id.passwordSettings)
                "Logout" -> backToLogin()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
    }

    fun deleteUser() {
        var userID = ""
        requireActivity().run {
            val sharedPreference =  getSharedPreferences("tempUser", Context.MODE_PRIVATE)
            userID = sharedPreference.getString("Username", "").toString()
            val editor = sharedPreference.edit()
            editor.clear()

        }
        mUserViewModel.deleteUser(userID)
    }

    fun backToLogin() {
        requireActivity().run {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}