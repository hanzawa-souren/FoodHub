package com.example.foodhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foodhub.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {

    private lateinit var bindingProfile: FragmentMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_profile, container, false)

        bindingProfile = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        return bindingProfile.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}