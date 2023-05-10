package com.example.foodhub.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.foodhub.R

import com.example.foodhub.databinding.FragmentTermsAndConditionBinding
import com.example.foodhub.user.DonateViewModal

class TermsAndConditionFragment: Fragment() {
    private val viewModel: DonateViewModal by activityViewModels()
        private lateinit var bindingTerms: FragmentTermsAndConditionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingTerms = DataBindingUtil.inflate(inflater, R.layout.fragment_terms_and_condition, container, false)
//        bindingTerms = FragmentTermsAndConditionBinding.inflate(inflater, container, false)
        return bindingTerms.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)

        bindingTerms.buttonReturn.setOnClickListener{
            viewModel.updateTncValue(true)
            view.findNavController().popBackStack()

        }

    }

}