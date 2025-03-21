package com.example.foodhub.user.fragments.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.foodhub.R
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.databinding.FragmentLatestNewsDetailsBinding

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
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = getString(R.string.latest_news)
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