package com.example.foodhub.user.fragments.detail

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
import com.example.foodhub.databinding.FragmentEDigestDetailsBinding
import com.example.foodhub.databinding.FragmentLatestNewsDetailsBinding
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.models.ProfileModel
import com.example.foodhub.user.fragments.detail.EdigestDetailsFragmentArgs
import com.example.foodhub.user.adapters.ProfileAdapter


class EdigestDetailsFragment : Fragment() {

    private lateinit var bindingEdigestDetails: FragmentEDigestDetailsBinding
    private val args by navArgs<EdigestDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingEdigestDetails = DataBindingUtil.inflate(inflater,
            R.layout.fragment_e_digest_details, container, false)
        return bindingEdigestDetails.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageFileName = args.currentDigest.eImage.substring(args.currentDigest.eImage.lastIndexOf("/")+1)
        Log.d("VolunteerDetailFragment", "Image for ${args.currentDigest.eTitle}: $imageFileName")
        val imageBitmap = ImageStorageManager.getImageFromInternalStorage(requireContext(), imageFileName)
        bindingEdigestDetails.eddImage.setImageBitmap(imageBitmap)

        bindingEdigestDetails.eddDate.text = args.currentDigest.eDate
        bindingEdigestDetails.eddTitle.text = args.currentDigest.eTitle
        bindingEdigestDetails.eddAuthor.text = args.currentDigest.eAuthor
        bindingEdigestDetails.eddContent.text = args.currentDigest.eContent
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle_salmon)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = getString(R.string.edigests)
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