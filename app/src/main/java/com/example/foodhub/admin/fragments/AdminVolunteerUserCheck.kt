package com.example.foodhub.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.AdminCheckVolunteerAdapter
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentAdminSettingsBinding
import com.example.foodhub.databinding.FragmentAdminUserCheckBinding
import com.example.foodhub.login.User
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.DonateViewModal
import com.example.foodhub.user.viewmodels.UserVolunteeredWorkViewModel

class AdminVolunteerUserCheck : Fragment() {

    private lateinit var bindingAdminUserCheck: FragmentAdminUserCheckBinding
    private lateinit var mUserVolunteeredWork: UserVolunteeredWorkViewModel
    private lateinit var mUserViewModel: UserViewModel
    private val viewModel: DonateViewModal by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_admin_settings, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserVolunteeredWork = ViewModelProvider(this)[UserVolunteeredWorkViewModel::class.java]
        bindingAdminUserCheck = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_user_check, container, false)
        return bindingAdminUserCheck.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingAdminUserCheck.ucName.text = viewModel.checkId.value
        var user = emptyList<User>()
        mUserViewModel.getAllUser().observe(viewLifecycleOwner, Observer { volunteer ->
            user = volunteer

        })
        var phoneNumber:String = ""
        for (i in user){
            if(i.loginID == viewModel.checkId.value){
                phoneNumber = i.phNum
            }else{
                phoneNumber = "0123456789"
            }
        }

        bindingAdminUserCheck.ucPhone.text = phoneNumber


        bindingAdminUserCheck.btnPresent.setOnClickListener{
            mUserVolunteeredWork.updateVolunteeredWorkStatus(viewModel.checkUvid.value?:-1,"Completed")
            findNavController().navigate(R.id.action_adminVolunteerUserCheck_to_adminCheckVolunteerFragment)
        }
        bindingAdminUserCheck.btnAbsent.setOnClickListener{
            mUserVolunteeredWork.updateVolunteeredWorkStatus(viewModel.checkUvid.value?:-1,"Absent")
            findNavController().navigate(R.id.action_adminVolunteerUserCheck_to_adminCheckVolunteerFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "User Attendance"
    }
}