package com.example.foodhub.user.fragments

import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentMyProfileBinding
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.ProfileModel

import com.example.foodhub.user.adapters.ProfileAdapter
import com.example.foodhub.user.viewmodels.DonateViewModal


class MyProfileFragment : Fragment() {

    private lateinit var bindingProfile: FragmentMyProfileBinding
    private lateinit var mUserViewModel: UserViewModel
    private val viewModel: DonateViewModal by activityViewModels()
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


        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        (activity as AppCompatActivity).supportActionBar?.hide()
        val rview = bindingProfile.profileStats

        var user = mUserViewModel.getUser(viewModel.userID.value?:"")

        bindingProfile.profileName.text = user?.loginID
        val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
        rview.layoutManager = LinearLayoutManager(requireContext())
        rview.setHasFixedSize(true)
        var day : String= user?.day.toString()

        var year : String= user?.year.toString()
        var month: String = months[user?.month!!]
        var dateJoined = "$day $month $year"
        Toast.makeText(requireContext(), "$dateJoined", Toast.LENGTH_SHORT).show()
        rview.adapter = ProfileAdapter(setDataList(),dateJoined)



    }
    private fun setDataList() : ArrayList<ProfileModel>{
        var arrayList : ArrayList<ProfileModel> = ArrayList()
        arrayList.add(ProfileModel(R.drawable.date_range_fill,"Date Joined","17 March 2023"))
        arrayList.add(ProfileModel(R.drawable.volunteer_activism,"Events Volunteered","0"))
        arrayList.add(ProfileModel(R.drawable.payments,"Total Donations","0"))


        return arrayList
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.top_toolbar).visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.top_toolbar).visibility = View.VISIBLE
    }

}