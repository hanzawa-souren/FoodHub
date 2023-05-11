package com.example.foodhub.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.DonationHistoryAdapter
import com.example.foodhub.R
import com.example.foodhub.database.tables.Donation
import com.example.foodhub.databinding.FragmentDonationHistoryBinding
import com.example.foodhub.databinding.FragmentDonationSuccessBinding
import com.example.foodhub.login.User
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.DonateViewModal


class DonateHistoryFragment: Fragment() {
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mDonateViewModal: DonateViewModal
    private lateinit var bindingDonateHistory: FragmentDonationHistoryBinding
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

        bindingDonateHistory = DataBindingUtil.inflate(inflater,
            R.layout.fragment_donation_history, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mDonateViewModal = ViewModelProvider(this)[DonateViewModal::class.java]
        return bindingDonateHistory.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""

        val rview = bindingDonateHistory.donateHistoryRV
        @Suppress("DEPRECATION")
        val name : User = activity?.intent?.getParcelableExtra("User")!!
        var donations  = emptyList<Donation>()
        rview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rview.setHasFixedSize(true)
        var adapter1 = DonationHistoryAdapter(donations)
        rview.adapter = adapter1
        mUserViewModel.getUserDonationObject(name.loginID).observe(viewLifecycleOwner, Observer { donation ->
            adapter1.updateData(donation.reversed())
        })
        adapter1.onItemClick = {
                donateHistoryRow ->
            viewModel.donateHistoryAmount.value = donateHistoryRow.dAmount.toString()
            viewModel.donateHistoryRef.value = donateHistoryRow.dId.toString()
            viewModel.donateHistoryPaymentMethod.value = donateHistoryRow.dMethod
            viewModel.donateHistoryCardNumber.value = donateHistoryRow.cardNumber
            val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
            var month = months[donateHistoryRow.month]
            var date = "${donateHistoryRow.day} $month ${donateHistoryRow.year}"
            viewModel.donateHistoryDate.value = date
            view.findNavController().navigate(R.id.donateReceiptFragment)
        }

        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Donation History"
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)




    }




}
