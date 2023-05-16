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
import com.example.foodhub.databinding.FragmentVolunteerDetailBinding

class VolunteerDetailsFragment : Fragment() {

    private lateinit var bindingVolunteerDetails: FragmentVolunteerDetailBinding
    private val args by navArgs<VolunteerDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingVolunteerDetails = DataBindingUtil.inflate(inflater,
            R.layout.fragment_volunteer_detail, container, false)
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
            var regUrl = args.currentWork.vRegLink
            if (!regUrl.startsWith("http://") && !regUrl.startsWith("https://")) {
                regUrl = "http://$regUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(regUrl))
            startActivity(browserIntent)
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
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = getString(R.string.volunteer)
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