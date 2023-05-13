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
import androidx.navigation.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentDateJoinedBinding
import com.example.foodhub.databinding.FragmentDonationReceiptBinding

import com.example.foodhub.user.DonateViewModal


class DateJoinedFragment : Fragment() {

    private lateinit var bindingDonateJoined: FragmentDateJoinedBinding
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

        bindingDonateJoined = DataBindingUtil.inflate(inflater,
            R.layout.fragment_date_joined, container, false)
        return bindingDonateJoined.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Date Joined"
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        bindingDonateJoined.djDay.text = viewModel.day.value.toString()
        val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
        bindingDonateJoined.djMonth.text = months[viewModel.month.value?:0]








    }



}
