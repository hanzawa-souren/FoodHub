package com.example.foodhub.admin.fragments.add

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.database.tables.LatestNews
import com.example.foodhub.databinding.FragmentAdminAddNewsBinding
import java.text.SimpleDateFormat
import java.util.*

class AdminAddNewsFragment : Fragment() {

    private lateinit var bindingAddNews: FragmentAdminAddNewsBinding
    private lateinit var latestNewsViewModel: LatestNewsViewModel
    private lateinit var uploadedImage: Uri
    private var imageUriNull = true

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")

            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            requireContext().contentResolver.takePersistableUriPermission(uri, flag)

            imageUriNull = false
            uploadedImage = uri
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_admin_add_news, container, false)
        bindingAddNews = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_news, container, false)

        latestNewsViewModel = ViewModelProvider(this).get(LatestNewsViewModel::class.java)

        return bindingAddNews.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingAddNews.addLnImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        bindingAddNews.lnPublishButton.setOnClickListener { insertItem() }
    }

    private fun insertItem() {

        val lnImage: String
        if (!imageUriNull) {
            lnImage = uploadedImage.toString()
        }
        else {
            lnImage = ""
        }

        val lnAuthor = bindingAddNews.editLnAuthor.text.toString()
        val lnTitle = bindingAddNews.editLnTitle.text.toString()

        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val currentDate = formatter.format(currentTime)
        val lnDate = currentDate.toString()

        val lnContent = bindingAddNews.editLnContent.text.toString()

        if (inputCheck(lnImage, lnAuthor, lnTitle, lnContent)) {
            val latestNews = LatestNews(0, lnImage, lnAuthor, lnTitle, lnDate, lnContent)
            latestNewsViewModel.insertNews(latestNews)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminAddNewsFragment_to_adminBulletinFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(lnImage: String, lnAuthor: String, lnTitle: String, lnContent: String): Boolean {
        return !(TextUtils.isEmpty(lnImage) || TextUtils.isEmpty(lnAuthor) || TextUtils.isEmpty(lnTitle) || TextUtils.isEmpty(lnContent))
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "New Article"
        if (!imageUriNull) {
            bindingAddNews.addLnImage.setImageURI(uploadedImage)
        }
    }
}