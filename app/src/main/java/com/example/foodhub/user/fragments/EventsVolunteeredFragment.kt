package com.example.foodhub.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.DonationHistoryAdapter
import com.example.foodhub.EventsVolunteeredAdapter
import com.example.foodhub.R
import com.example.foodhub.database.tables.UserVolunteeredWork
import com.example.foodhub.databinding.FragmentDonationSuccessBinding
import com.example.foodhub.databinding.FragmentEventsVolounteeredBinding
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.DonateViewModal
import com.example.foodhub.user.viewmodels.UserVolunteeredWorkViewModel


class EventsVolunteeredFragment: Fragment() {

    private lateinit var bindingDonateSuccess: FragmentEventsVolounteeredBinding
    private lateinit var mUserVolunteeredWork: UserVolunteeredWorkViewModel
    private val viewModel: DonateViewModal by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_donate, container, false)

        bindingDonateSuccess = DataBindingUtil.inflate(inflater,
            R.layout.fragment_events_volounteered, container, false)
        mUserVolunteeredWork = ViewModelProvider(this)[UserVolunteeredWorkViewModel::class.java]
        return bindingDonateSuccess.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        var userVW = emptyList<UserVolunteeredWork>()
        var rview = bindingDonateSuccess.eventsVolunteeredRV
        rview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        var adapter1 = EventsVolunteeredAdapter(userVW)

        rview.adapter = adapter1

        mUserVolunteeredWork.getEventsVolunteeredUser(viewModel.name.value?:"").observe(viewLifecycleOwner, Observer { volunteers ->
            adapter1.updateData(volunteers.reversed())
            viewModel.numEventsVolunteered.value = userVW.size
        })
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Events Volunteered"
        adapter1.onItemClick={
            userVolunteeredWork ->
            viewModel.evVid.value = userVolunteeredWork.vId
            viewModel.evAboutUs.value = userVolunteeredWork.vDesc
            viewModel.evLocation.value = userVolunteeredWork.vStreet + userVolunteeredWork.vCity + userVolunteeredWork.vState + userVolunteeredWork.vPostcode + userVolunteeredWork.vCountry
            viewModel.evPhone.value = userVolunteeredWork.vPhone
            viewModel.evWebPage.value = userVolunteeredWork.vWebsite
            viewModel.evStatus.value = userVolunteeredWork.status
            viewModel.evWaze.value = userVolunteeredWork.vWaze
            viewModel.evGmap.value = userVolunteeredWork.vMaps
            viewModel.evTitle.value = userVolunteeredWork.vTitle
            val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
            var month = months[userVolunteeredWork.month+1]
            var date = "${userVolunteeredWork.day} $month 2023"
            viewModel.evDate.value = date
            viewModel.evImage.value = userVolunteeredWork.vImage
            view.findNavController().navigate(R.id.eventsVolunteeredDetailFragment)
        }





    }



}
