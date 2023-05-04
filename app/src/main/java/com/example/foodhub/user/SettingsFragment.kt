package com.example.foodhub.user

import android.content.Context

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foodhub.R

import com.example.foodhub.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var bindingSettings: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }
    private class CustomAdapter(context:Context) : BaseAdapter() {
        val settingOptions = arrayOf("Username","Profile Picture","Phone Number","Email","Password")

        val optionImage = arrayOf(
            R.drawable.setting_ic_username,
            R.drawable.setting_ic_profilepic,
            R.drawable.setting_ic_call,
            R.drawable.setting_ic_email,
            R.drawable.setting_ic_password
        )
        private val myContext:Context
        init {
            myContext = context
        }
        override fun getCount(): Int {
            return settingOptions.size
        }

        override fun getItem(p0: Int): Any {
            return "empty"
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(myContext)
            val rowMain = layoutInflater.inflate(R.layout.row_setting,viewGroup,false)
            val option = rowMain.findViewById<TextView>(R.id.option)
            option.text = settingOptions.get(position)
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
        return bindingSettings.root
        // calling the action bar

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingMenu : ListView = bindingSettings.settingMenu
        settingMenu.adapter = CustomAdapter(requireContext())
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
    }


}