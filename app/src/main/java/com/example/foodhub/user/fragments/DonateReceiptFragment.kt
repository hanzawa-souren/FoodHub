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
import com.example.foodhub.databinding.FragmentDonationReceiptBinding

import com.example.foodhub.user.DonateViewModal


class DonateReceiptFragment : Fragment() {

    private lateinit var bindingDonateReceipt: FragmentDonationReceiptBinding
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

        bindingDonateReceipt = DataBindingUtil.inflate(inflater,
            R.layout.fragment_donation_receipt, container, false)
        return bindingDonateReceipt.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Donation Receipt"
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)

        bindingDonateReceipt.drAmount.text = "RM "+viewModel.donateHistoryAmount.value
        bindingDonateReceipt.drPaymentMethodContent.text = viewModel.donateHistoryPaymentMethod.value
        bindingDonateReceipt.drDateContent.text = viewModel.donateHistoryDate.value
        bindingDonateReceipt.drRefContent.text = viewModel.donateHistoryRef.value
        bindingDonateReceipt.drCardNumberContent.text = viewModel.donateHistoryCardNumber.value
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack(R.id.homeFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)







    }



}
