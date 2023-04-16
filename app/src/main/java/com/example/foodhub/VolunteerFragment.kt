package com.example.foodhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.example.foodhub.databinding.FragmentVolunteerBinding

class VolunteerFragment : Fragment() {

    private lateinit var bindingVolunteer: FragmentVolunteerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_volunteer, container, false)

        bindingVolunteer = DataBindingUtil.inflate(inflater, R.layout.fragment_volunteer, container, false)
        return bindingVolunteer.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })*/
        
    }
}