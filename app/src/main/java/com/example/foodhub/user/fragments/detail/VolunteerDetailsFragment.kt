package com.example.foodhub.user.fragments.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodhub.R
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.UserVolunteeredWork
import com.example.foodhub.databinding.FragmentVolunteerDetailBinding
import com.example.foodhub.user.DonateViewModal
import com.example.foodhub.user.viewmodels.UserVolunteeredWorkViewModel
import java.util.*

class VolunteerDetailsFragment : Fragment() {

    private lateinit var bindingVolunteerDetails: FragmentVolunteerDetailBinding
    private val args by navArgs<VolunteerDetailsFragmentArgs>()
    private lateinit var mUserVolunteeredWork: UserVolunteeredWorkViewModel
    private val viewModel: DonateViewModal by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingVolunteerDetails = DataBindingUtil.inflate(inflater,
            R.layout.fragment_volunteer_detail, container, false)
        mUserVolunteeredWork = ViewModelProvider(this)[UserVolunteeredWorkViewModel::class.java]
        return bindingVolunteerDetails.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageFileName = args.currentWork.vImage.substring(args.currentWork.vImage.lastIndexOf("/")+1)
        Log.d("VolunteerDetailFragment", "Image for ${args.currentWork.vTitle}: $imageFileName")
        val imageBitmap = ImageStorageManager.getImageFromInternalStorage(requireContext(), imageFileName)
        bindingVolunteerDetails.imageView3.setImageBitmap(imageBitmap)

        bindingVolunteerDetails.eventTitle.text = args.currentWork.vTitle
        bindingVolunteerDetails.aboutUsContent.text = args.currentWork.vDesc
        val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        var day = args.currentWork.day.toString()
        var month = months[args.currentWork.month+1]
        bindingVolunteerDetails.calendarContent.text = "$day $month $currentYear"
        val address = args.currentWork.vStreet + ", " + args.currentWork.vCity + ", " + args.currentWork.vPostcode + ", " + args.currentWork.vState + ", " + args.currentWork.vCountry
        bindingVolunteerDetails.locationContent.text = address
        bindingVolunteerDetails.webpageContent2.text = args.currentWork.vWebsite

        bindingVolunteerDetails.webpageContent2.setOnClickListener {
            var webUrl = args.currentWork.vWebsite
            if (!webUrl.startsWith("http://") && !webUrl.startsWith("https://")) {
                webUrl = "http://$webUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            startActivity(browserIntent)
        }

        bindingVolunteerDetails.phoneContent.text = args.currentWork.vPhone

        bindingVolunteerDetails.registerButton.setOnClickListener {

            var check = mUserVolunteeredWork.checkVolunteered(viewModel.name.value?:"",args.currentWork.vId).value
            if (check == 0){
                mUserVolunteeredWork.addVolunteeredWork(UserVolunteeredWork(0,args.currentWork.vId,viewModel.name.value?:"","Pending",args.currentWork.vImage,args.currentWork.vTitle,args.currentWork.vDesc,args.currentWork.vStreet,args.currentWork.vCity,args.currentWork.vPostcode,args.currentWork.vState,args.currentWork.vCountry,args.currentWork.vWebsite,args.currentWork.vPhone,args.currentWork.vRegLink,args.currentWork.vMaps,args.currentWork.vWaze,args.currentWork.day,args.currentWork.month))
                Toast.makeText(requireContext(), "Registered", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Already Registered", Toast.LENGTH_SHORT).show()
            }


            view.findNavController().navigate(R.id.homeFragment)
//            var regUrl = args.currentWork.vRegLink
//            if (!regUrl.startsWith("http://") && !regUrl.startsWith("https://")) {
//                regUrl = "http://$regUrl"
//            }
//
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(regUrl))
//            startActivity(browserIntent)
        }

        bindingVolunteerDetails.GoNowButton.setOnClickListener {
            var mapsUrl = args.currentWork.vMaps
            if (!mapsUrl.startsWith("http://") && !mapsUrl.startsWith("https://")) {
                mapsUrl = "http://$mapsUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
            startActivity(browserIntent)
        }

        bindingVolunteerDetails.WazeButton.setOnClickListener {
            var wazeUrl = args.currentWork.vWaze
            if (!wazeUrl.startsWith("http://") && !wazeUrl.startsWith("https://")) {
                wazeUrl = "http://$wazeUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wazeUrl))
            startActivity(browserIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle_salmon)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Volunteer"
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).setTextColor(ContextCompat.getColor(requireContext(),
            R.color.salmon
        ))
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_toolbar).setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.white
        ))
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).setTextColor(ContextCompat.getColor(requireContext(),
            R.color.white
        ))
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_toolbar).setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.salmon
        ))
    }
}