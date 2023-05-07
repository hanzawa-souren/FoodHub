package com.example.foodhub.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentMyProfileBinding
import com.example.foodhub.user.adapters.ProfileAdapter
import com.example.foodhub.user.viewmodels.ProfileModel

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

        bindingProfile = DataBindingUtil.inflate(inflater,
            R.layout.fragment_my_profile, container, false)
        return bindingProfile.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        val rview = bindingProfile.profileStats
        rview.layoutManager = LinearLayoutManager(requireContext())
        rview.setHasFixedSize(true)
        rview.adapter = ProfileAdapter(setDataList())
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
    private fun setDataList() : ArrayList<ProfileModel>{
        var arrayList : ArrayList<ProfileModel> = ArrayList()
        arrayList.add(ProfileModel(R.drawable.date_range_fill,"Date Joined","17 March 2023"))
        arrayList.add(ProfileModel(R.drawable.volunteer_activism,"Events Volunteered","0"))
        arrayList.add(ProfileModel(R.drawable.payments,"Total Donations","0"))


        return arrayList
    }

}