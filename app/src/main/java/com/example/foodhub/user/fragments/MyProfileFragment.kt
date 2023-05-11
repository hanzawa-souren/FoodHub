package com.example.foodhub.user.fragments

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
import com.example.foodhub.login.User
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.DonateViewModal
import com.example.foodhub.user.models.ProfileModel

import com.example.foodhub.user.adapters.ProfileAdapter


class MyProfileFragment : Fragment() {

    private lateinit var bindingProfile: FragmentMyProfileBinding
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mDonateViewModal: DonateViewModal
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
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mDonateViewModal = ViewModelProvider(this)[DonateViewModal::class.java]
        return bindingProfile.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        (activity as AppCompatActivity).supportActionBar?.hide()
        val rview = bindingProfile.profileStats
        @Suppress("DEPRECATION")
        val name : User = activity?.intent?.getParcelableExtra("User")!!
        var user = mUserViewModel.getUser(name.loginID)
////        var donateAmount : Double = mUserViewModel.getUserDonation(name.loginID)
//        var donateAmount : Double = viewModel.donateAmounts.value?:0.0
//        mUserViewModel.getUserDonation(name.loginID).observe(viewLifecycleOwner, Observer { amount ->
//
//            viewModel.donateAmounts.value = amount
//        })

//        Toast.makeText(requireContext(), "$donateAmount", Toast.LENGTH_SHORT).show()

        bindingProfile.profileName.text = name.loginID
        val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
        rview.layoutManager = LinearLayoutManager(requireContext())
        rview.setHasFixedSize(true)
        var day : String= viewModel.day.value.toString()

        var year : String= viewModel.year.value.toString()
        var month: String = months[viewModel.month.value!!]
        var dateJoined = "$day $month $year"



        rview.adapter = ProfileAdapter(setDataList(), dateJoined,viewModel.donateAmounts.value?:0.0)



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