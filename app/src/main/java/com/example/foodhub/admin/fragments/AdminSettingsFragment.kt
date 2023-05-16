package com.example.foodhub.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentAdminSettingsBinding

class AdminSettingsFragment : Fragment() {

    private lateinit var bindingAdminSettings: FragmentAdminSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAdminSettings = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_settings, container, false)
        return bindingAdminSettings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Settings"
    }
}