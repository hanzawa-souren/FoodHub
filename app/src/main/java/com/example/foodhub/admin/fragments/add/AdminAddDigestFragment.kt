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
import com.example.foodhub.admin.viewmodels.EDigestViewModel
import com.example.foodhub.database.tables.EDigest
import com.example.foodhub.databinding.FragmentAdminAddDigestBinding
import java.text.SimpleDateFormat
import java.util.*

class AdminAddDigestFragment : Fragment() {

    private lateinit var bindingAddDigest: FragmentAdminAddDigestBinding
    private lateinit var eDigestViewModel: EDigestViewModel
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
        //return inflater.inflate(R.layout.fragment_admin_add_digest, container, false)
        bindingAddDigest = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_digest, container, false)

        eDigestViewModel = ViewModelProvider(this).get(EDigestViewModel::class.java)

        return bindingAddDigest.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingAddDigest.addEImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        bindingAddDigest.ePublishButton.setOnClickListener { insertItem() }
    }

    private fun insertItem() {

        val eImage: String
        if (!imageUriNull) {
            eImage = uploadedImage.toString()
        }
        else {
            eImage = ""
        }

        val eAuthor = bindingAddDigest.editEAuthor.text.toString()
        val eTitle = bindingAddDigest.editETitle.text.toString()

        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val currentDate = formatter.format(currentTime)
        val eDate = currentDate.toString()

        val eContent = bindingAddDigest.editEContent.text.toString()

        if (inputCheck(eImage, eAuthor, eTitle, eContent)) {
            val digest = EDigest(0, eImage, eAuthor, eTitle, eDate, eContent)
            eDigestViewModel.insertDigest(digest)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminAddDigestFragment_to_adminBulletinFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(eImage: String, eAuthor: String, eTitle: String, eContent: String): Boolean {
        return !(TextUtils.isEmpty(eImage) || TextUtils.isEmpty(eAuthor) || TextUtils.isEmpty(eTitle) || TextUtils.isEmpty(eContent))
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "New Digest"
        if (!imageUriNull) {
            bindingAddDigest.addEImage.setImageURI(uploadedImage)
        }
    }
}