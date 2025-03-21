package com.example.foodhub.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentDonationSuccessBinding
import com.example.foodhub.user.DonateViewModal


class DonateSuccessFragment : Fragment() {

    private lateinit var bindingDonateSuccess: FragmentDonationSuccessBinding
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
            R.layout.fragment_donation_success, container, false)
        return bindingDonateSuccess.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""

        (activity as AppCompatActivity).supportActionBar?.hide()
        viewModel.tnc.value = false
        var amount : String = viewModel.donateAmount.value?:"0"
        bindingDonateSuccess.successSubtext.text = "Your donation of RM $amount is successful"

        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack(R.id.homeFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        bindingDonateSuccess.buttonOk.setOnClickListener{

            view.findNavController().navigate(R.id.homeFragment)

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().navigate(R.id.homeFragment)
        }





    }



}
