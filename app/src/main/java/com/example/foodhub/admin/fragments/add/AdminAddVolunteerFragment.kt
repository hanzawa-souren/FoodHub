package com.example.foodhub.admin.fragments.add

import android.content.Intent
import android.graphics.ImageDecoder
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.VoluntaryWork
import com.example.foodhub.databinding.FragmentAdminAddVolunteerBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdminAddVolunteerFragment : Fragment() {

    private lateinit var bindingAddVolunteer: FragmentAdminAddVolunteerBinding
    private lateinit var voluntaryWorkViewModel: VoluntaryWorkViewModel
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

        bindingAddVolunteer = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_volunteer, container, false)
        voluntaryWorkViewModel = ViewModelProvider(this).get(VoluntaryWorkViewModel::class.java)
        return bindingAddVolunteer.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingAddVolunteer.addVImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        bindingAddVolunteer.vPublishButton.setOnClickListener { insertItem() }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun insertItem() {

        val vTitle = bindingAddVolunteer.editVTitle.text.toString()
        val vDesc = bindingAddVolunteer.editVDesc.text.toString()
        val vStreet = bindingAddVolunteer.editVStreet.text.toString()
        val vCity = bindingAddVolunteer.editVCity.text.toString()
        val vPostcode = bindingAddVolunteer.editVPostcode.text.toString()
        val vState = bindingAddVolunteer.editVState.text.toString()
        val vCountry = bindingAddVolunteer.editVCountry.text.toString()
        val vPhone = bindingAddVolunteer.editVPhone.text.toString()
        val vWebsite = bindingAddVolunteer.editVWebsite.text.toString()
        val vReglink = bindingAddVolunteer.editVReglink.text.toString()
        val vMaps = bindingAddVolunteer.editVMaps.text.toString()
        val vWaze = bindingAddVolunteer.editVWaze.text.toString()
        val vDay = bindingAddVolunteer.updateDay.text.toString()
        val vMonth = bindingAddVolunteer.updateMonth.text.toString()

        if (inputCheck(vTitle, vDesc, vStreet, vCity, vPostcode, vState, vCountry, vPhone, vWebsite, vReglink, vMaps, vWaze,vDay,vMonth)) {

            val currentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyyMMdd_HH_mm_ss")
            val timestamp = formatter.format(currentTime).toString()
            val imageFileName = "v_work_img_$timestamp"

            var vImage = ""
            val imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, uploadedImage))

            // Create a bitmap copy of the admin-selected image and save it into the application storage
            // The image file absolute path is stored inside ROOM
            viewLifecycleOwner.lifecycleScope.launch {
                vImage = ImageStorageManager.saveToInternalStorage(requireContext(), imageBitmap, imageFileName)
            }
            Log.d("AddVolunteerFragment", "Image file name: $imageFileName")
            Log.d("AddVolunteerFragment", "File absolute path: $vImage")

            val voluntaryWork = VoluntaryWork(0, vImage, vTitle, vDesc, vStreet, vCity, vPostcode, vState, vCountry, vWebsite, vPhone, vReglink, vMaps, vWaze,vDay.toInt(),vMonth.toInt()-1)
            voluntaryWorkViewModel.insertWork(voluntaryWork)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminAddVolunteerFragment_to_adminVolunteerFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(vTitle: String, vDesc: String, vStreet: String, vCity: String, vPostcode: String, vState: String, vCountry: String, vPhone: String, vWebsite: String, vReglink: String, vMaps: String, vWaze: String,vDay:String,vMonth:String): Boolean {
        return !(imageUriNull || TextUtils.isEmpty(vTitle) || TextUtils.isEmpty(vDesc) || TextUtils.isEmpty(vStreet) || TextUtils.isEmpty(vCity) || TextUtils.isEmpty(vPostcode) || TextUtils.isEmpty(vState) || TextUtils.isEmpty(vCountry) || TextUtils.isEmpty(vPhone) || TextUtils.isEmpty(vWebsite) || TextUtils.isEmpty(vReglink) || TextUtils.isEmpty(vMaps) || TextUtils.isEmpty(vWaze)|| TextUtils.isEmpty(vDay)|| TextUtils.isEmpty(vMonth))
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "New Work"
        if (!imageUriNull) {
            bindingAddVolunteer.addVImage.setImageURI(uploadedImage)
        }
    }
}