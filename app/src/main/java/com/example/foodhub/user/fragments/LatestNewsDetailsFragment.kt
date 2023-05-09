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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentLatestNewsDetailsBinding
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.ProfileModel

import com.example.foodhub.user.adapters.ProfileAdapter


class LatestNewsDetailsFragment : Fragment() {

    private lateinit var bindingNewsDetails: FragmentLatestNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_profile, container, false)

        bindingNewsDetails = DataBindingUtil.inflate(inflater,
            R.layout.fragment_latest_news_details, container, false)
        return bindingNewsDetails.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()




    }


}