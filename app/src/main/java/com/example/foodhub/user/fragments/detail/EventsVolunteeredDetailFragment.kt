package com.example.foodhub.user.fragments.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.databinding.FragmentEDigestDetailsBinding
import com.example.foodhub.databinding.FragmentEventsVolunteeredDetailsBinding
import com.example.foodhub.databinding.FragmentLatestNewsDetailsBinding
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.DonateViewModal
import com.example.foodhub.user.models.ProfileModel
import com.example.foodhub.user.fragments.detail.EdigestDetailsFragmentArgs
import com.example.foodhub.user.adapters.ProfileAdapter
import com.example.foodhub.user.viewmodels.UserVolunteeredWorkViewModel


class EventsVolunteeredDetailFragment : Fragment() {
    private val viewModel: DonateViewModal by activityViewModels()
    private lateinit var bindingEventsVolunteeredDetail: FragmentEventsVolunteeredDetailsBinding
    private lateinit var mUserVolunteeredWork: UserVolunteeredWorkViewModel
    private val args by navArgs<EdigestDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserVolunteeredWork = ViewModelProvider(this)[UserVolunteeredWorkViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_profile, container, false)

        bindingEventsVolunteeredDetail = DataBindingUtil.inflate(inflater,
            R.layout.fragment_events_volunteered_details, container, false)
        return bindingEventsVolunteeredDetail.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Events Volunteered Details"
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        bindingEventsVolunteeredDetail.aboutUsContent.text = viewModel.evAboutUs.value
        bindingEventsVolunteeredDetail.locationContent.text = viewModel.evLocation.value
        bindingEventsVolunteeredDetail.webpageContent2.text = viewModel.evWebPage.value
        bindingEventsVolunteeredDetail.phoneContent.text = viewModel.evPhone.value
        bindingEventsVolunteeredDetail.calendarContent.text = viewModel.evDate.value
        bindingEventsVolunteeredDetail.eventTitle.text = viewModel.evTitle.value
        bindingEventsVolunteeredDetail.imageView3.setImageURI(Uri.parse(viewModel.evImage.value))
        if(viewModel.evStatus.value == "Completed"){
            bindingEventsVolunteeredDetail.cancelBtn.visibility = View.INVISIBLE
            bindingEventsVolunteeredDetail.evdStatusImg.visibility = View.VISIBLE
            bindingEventsVolunteeredDetail.evdStatusText.visibility = View.VISIBLE
        }else{
            bindingEventsVolunteeredDetail.cancelBtn.visibility = View.VISIBLE
            bindingEventsVolunteeredDetail.evdStatusImg.visibility = View.INVISIBLE
            bindingEventsVolunteeredDetail.evdStatusText.visibility = View.INVISIBLE
        }
        bindingEventsVolunteeredDetail.cancelBtn.setOnClickListener{
            mUserVolunteeredWork.cancelEventsVolunteeredUser(viewModel.name.value?:"",viewModel.evVid.value?:-1)
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack(R.id.homeFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//            view.findNavController().navigate(R.id.eventsVolunteeredFragment)
            view.findNavController().navigate(R.id.myProfileFragment)
            Toast.makeText(requireContext(), "Registration Cancelled", Toast.LENGTH_SHORT).show()
        }
        bindingEventsVolunteeredDetail.GoNowButton.setOnClickListener {
            var mapsUrl = viewModel.evGmap.value?:""
            if (!mapsUrl.startsWith("http://") && !mapsUrl.startsWith("https://")) {
                mapsUrl = "http://$mapsUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
            startActivity(browserIntent)
        }

        bindingEventsVolunteeredDetail.WazeButton.setOnClickListener {
            var wazeUrl = viewModel.evWaze.value?:""
            if (!wazeUrl.startsWith("http://") && !wazeUrl.startsWith("https://")) {
                wazeUrl = "http://$wazeUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wazeUrl))
            startActivity(browserIntent)
        }


    }


}