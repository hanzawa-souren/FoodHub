package com.example.foodhub.user.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.databinding.FragmentLatestNewsDetailsBinding
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.ProfileModel

import com.example.foodhub.user.adapters.ProfileAdapter


class LatestNewsDetailsFragment : Fragment() {

    private lateinit var bindingNewsDetails: FragmentLatestNewsDetailsBinding
    private val args by navArgs<LatestNewsDetailsFragmentArgs>()

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

        val imageFileName = args.currentNews.lnImage.substring(args.currentNews.lnImage.lastIndexOf("/")+1)
        Log.d("VolunteerDetailFragment", "Image for ${args.currentNews.lnTitle}: $imageFileName")
        val imageBitmap = ImageStorageManager.getImageFromInternalStorage(requireContext(), imageFileName)
        bindingNewsDetails.ndImage.setImageBitmap(imageBitmap)

        bindingNewsDetails.ndDate.text = args.currentNews.lnDate
        bindingNewsDetails.ndTitle.text = args.currentNews.lnTitle
        bindingNewsDetails.ndAuthor.text = args.currentNews.lnAuthor
        bindingNewsDetails.ndContent.text = args.currentNews.lnContent
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle_salmon)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Latest News"
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