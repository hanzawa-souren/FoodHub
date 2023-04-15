package com.example.foodhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foodhub.databinding.FragmentVolunteerBinding

class VolunteerFragment : Fragment() {

    private lateinit var bindingVolunteer: FragmentVolunteerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingVolunteer = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_volunteer)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_volunteer, container, false)
        val binding = DataBindingUtil.inflate<FragmentVolunteerBinding>(inflater, R.layout.fragment_volunteer, container, false)
        return binding.root
    }
}