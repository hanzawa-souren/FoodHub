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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.foodhub.R
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.databinding.FragmentNearMeDetailBinding

class NearMeDetailFragment : Fragment() {

    private lateinit var bindingNearMeDetails: FragmentNearMeDetailBinding
    private val args by navArgs<NearMeDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingNearMeDetails = DataBindingUtil.inflate(inflater, R.layout.fragment_near_me_detail, container, false)
        return bindingNearMeDetails.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageFileName = args.currentFacility.nImage.substring(args.currentFacility.nImage.lastIndexOf("/")+1)
        Log.d("NearMeDetailFragment", "Image for ${args.currentFacility.nName}: $imageFileName")
        val imageBitmap = ImageStorageManager.getImageFromInternalStorage(requireContext(), imageFileName)
        bindingNearMeDetails.imageView3.setImageBitmap(imageBitmap)

        bindingNearMeDetails.eventTitle.text = args.currentFacility.nName
        bindingNearMeDetails.aboutUsContent.text = args.currentFacility.nDesc

        val address = args.currentFacility.nStreet + ", " + args.currentFacility.nCity + ", " + args.currentFacility.nPostcode + ", " + args.currentFacility.nState + ", " + args.currentFacility.nCountry
        bindingNearMeDetails.locationContent.text = address
        bindingNearMeDetails.webpageContent2.text = args.currentFacility.nWebsite

        bindingNearMeDetails.webpageContent2.setOnClickListener {
            var webUrl = args.currentFacility.nWebsite
            if (!webUrl.startsWith("http://") && !webUrl.startsWith("https://")) {
                webUrl = "http://$webUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            startActivity(browserIntent)
        }

        bindingNearMeDetails.phoneContent.text = args.currentFacility.nPhone

        bindingNearMeDetails.GoNowButton.setOnClickListener {
            var mapsUrl = args.currentFacility.nMaps
            if (!mapsUrl.startsWith("http://") && !mapsUrl.startsWith("https://")) {
                mapsUrl = "http://$mapsUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
            startActivity(browserIntent)
        }

        bindingNearMeDetails.WazeButton.setOnClickListener {
            var wazeUrl = args.currentFacility.nWaze
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
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = getString(R.string.near_me)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).setTextColor(
            ContextCompat.getColor(requireContext(),
                R.color.salmon
            ))
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_toolbar).setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.white
            ))
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).setTextColor(
            ContextCompat.getColor(requireContext(),
                R.color.white
            ))
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_toolbar).setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.salmon
            ))
    }
}