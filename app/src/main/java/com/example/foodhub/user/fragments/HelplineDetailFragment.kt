package com.example.foodhub.user.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.example.foodhub.databinding.FragmentHelplineDetailBinding

class HelplineDetailFragment : Fragment() {

    private lateinit var bindingHelplineDetails: FragmentHelplineDetailBinding
    private val args by navArgs<HelplineDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingHelplineDetails = DataBindingUtil.inflate(inflater, R.layout.fragment_helpline_detail, container, false)
        return bindingHelplineDetails.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingHelplineDetails.helplineDetailTitle.text = args.currentHelpline.hTitle
        bindingHelplineDetails.helplineNumber.text = args.currentHelpline.hPhone
        bindingHelplineDetails.websiteLink.text = args.currentHelpline.hWebsite
        bindingHelplineDetails.descriptionContent.text = args.currentHelpline.hDesc

        bindingHelplineDetails.helplineNumber.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + args.currentHelpline.hPhone))
            startActivity(dialIntent)
        }

        bindingHelplineDetails.websiteLink.setOnClickListener {
            var webUrl = args.currentHelpline.hWebsite
            if (!webUrl.startsWith("http://") && !webUrl.startsWith("https://")) {
                webUrl = "http://$webUrl"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            startActivity(browserIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle_salmon)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Helplines"
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