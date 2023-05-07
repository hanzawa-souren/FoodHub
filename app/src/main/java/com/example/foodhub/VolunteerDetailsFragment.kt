package com.example.foodhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.databinding.FragmentDonateBinding
import com.example.foodhub.databinding.FragmentDonateConfirmBinding
import com.example.foodhub.databinding.FragmentVolunteerDetailBinding

class VolunteerDetailsFragment : Fragment() {

    private lateinit var bindingVolunteerDetails: FragmentVolunteerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        bindingVolunteerDetails = DataBindingUtil.inflate(inflater, R.layout.fragment_volunteer_detail, container, false)
        return bindingVolunteerDetails.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(R.drawable.setting_background)



    }


}

private fun ActionBar?.setBackgroundDrawable(signOutCircle: Int) {

}






