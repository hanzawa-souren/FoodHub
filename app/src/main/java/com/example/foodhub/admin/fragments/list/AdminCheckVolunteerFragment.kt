package com.example.foodhub.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.AdminCheckVolunteerAdapter
import com.example.foodhub.DonationHistoryAdapter
import com.example.foodhub.EventsVolunteeredAdapter
import com.example.foodhub.R
import com.example.foodhub.database.tables.UserVolunteeredWork
import com.example.foodhub.databinding.FragmentAdminCheckVolunteersBinding
import com.example.foodhub.databinding.FragmentDonationSuccessBinding
import com.example.foodhub.databinding.FragmentEventsVolounteeredBinding
import com.example.foodhub.login.User
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.DonateViewModal
import com.example.foodhub.user.viewmodels.UserVolunteeredWorkViewModel


class AdminCheckVolunteerFragment: Fragment() {

    private lateinit var bindingCheckVolunteer: FragmentAdminCheckVolunteersBinding
    private lateinit var mUserVolunteeredWork: UserVolunteeredWorkViewModel
    private lateinit var mUserViewModel: UserViewModel
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

        bindingCheckVolunteer = DataBindingUtil.inflate(inflater,
            R.layout.fragment_admin_check_volunteers, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserVolunteeredWork = ViewModelProvider(this)[UserVolunteeredWorkViewModel::class.java]
        return bindingCheckVolunteer.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        val rview = bindingCheckVolunteer.checkVolunteerRV
        rview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        var userVW = emptyList<UserVolunteeredWork>()
        var user = emptyList<User>()
        var adapter1 = AdminCheckVolunteerAdapter(userVW, user)
        mUserViewModel.getAllUser().observe(viewLifecycleOwner, Observer { volunteer ->
           user = volunteer
            adapter1.updateData2(volunteer)
        })
        mUserVolunteeredWork.getParticipatedUser(viewModel.adminVid.value?:0).observe(viewLifecycleOwner, Observer { volunteers ->
            adapter1.updateData(volunteers.reversed())

        })


        rview.adapter = adapter1
        adapter1.onItemClick={
            UserVolunteeredWork->
            viewModel.checkId.value = UserVolunteeredWork.uId
            viewModel.checkUvid.value = UserVolunteeredWork.uvwId
            findNavController().navigate(R.id.action_adminCheckVolunteerFragment_to_adminVolunteerUserCheck)
        }
        bindingCheckVolunteer.allPresent.setOnClickListener{
            mUserVolunteeredWork.updateAll(viewModel.adminVid.value?:0,"Completed")
            Toast.makeText(requireContext(), "Marked all present", Toast.LENGTH_SHORT).show()
        }
        bindingCheckVolunteer.allPending.setOnClickListener{
            mUserVolunteeredWork.updateAll(viewModel.adminVid.value?:0,"Pending")
            Toast.makeText(requireContext(), "Marked all pending", Toast.LENGTH_SHORT).show()
        }




    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Volunteer Checklist"

    }

}
