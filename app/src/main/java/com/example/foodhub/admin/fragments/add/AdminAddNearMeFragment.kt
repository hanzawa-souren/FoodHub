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
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.example.foodhub.admin.viewmodels.FacilityViewModel
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.Facility
import com.example.foodhub.databinding.FragmentAdminAddNearMeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdminAddNearMeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var bindingAddNearMe: FragmentAdminAddNearMeBinding
    private lateinit var facilityViewModel: FacilityViewModel
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

    private var facilityType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAddNearMe = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_near_me, container, false)
        facilityViewModel = ViewModelProvider(this).get(FacilityViewModel::class.java)
        return bindingAddNearMe.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingAddNearMe.addNImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Spinner
        bindingAddNearMe.spinnerNFacility.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.facility_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bindingAddNearMe.spinnerNFacility.adapter = adapter
        }

        bindingAddNearMe.nPublishButton.setOnClickListener { insertItem() }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun insertItem() {

        val nName = bindingAddNearMe.editNName.text.toString()
        val nFacility = facilityType
        val nDesc = bindingAddNearMe.editNDesc.text.toString()
        val nStreet = bindingAddNearMe.editNStreet.text.toString()
        val nCity = bindingAddNearMe.editNCity.text.toString()
        val nPostcode = bindingAddNearMe.editNPostcode.text.toString()
        val nState = bindingAddNearMe.editNState.text.toString()
        val nCountry = bindingAddNearMe.editNCountry.text.toString()
        val nPhone = bindingAddNearMe.editNPhone.text.toString()
        val nWebsite = bindingAddNearMe.editNWebsite.text.toString()
        val nMaps = bindingAddNearMe.editNMaps.text.toString()
        val nWaze = bindingAddNearMe.editNWaze.text.toString()

        if (inputCheck(nName, nFacility, nDesc, nStreet, nCity, nPostcode, nState, nCountry, nPhone, nWebsite, nMaps, nWaze)) {

            val currentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyyMMdd_HH_mm_ss")
            val timestamp = formatter.format(currentTime).toString()
            val imageFileName = "facility_img_$timestamp"

            var nImage = ""
            val imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, uploadedImage))

            // Create a bitmap copy of the admin-selected image and save it into the application storage
            // The image file absolute path is stored inside ROOM
            viewLifecycleOwner.lifecycleScope.launch {
                nImage = ImageStorageManager.saveToInternalStorage(requireContext(), imageBitmap, imageFileName)
            }
            Log.d("AddNearMeFragment", "Image file name: $imageFileName")
            Log.d("AddNearMeFragment", "File absolute path: $nImage")

            val facility = Facility(0, nImage, nName, nFacility, nDesc, nStreet, nCity, nPostcode, nState, nCountry, nPhone, nWebsite, nMaps, nWaze)
            facilityViewModel.insertFacility(facility)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminAddNearMeFragment_to_adminNearMeFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        facilityType = parent?.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        facilityType = ""
    }

    private fun inputCheck(nName: String, nFacility: String, nDesc: String, nStreet: String, nCity:String, nPostcode: String, nState: String, nCountry: String, nPhone: String, nWebsite: String, nMaps: String, nWaze: String): Boolean {
        return !(imageUriNull || TextUtils.isEmpty(nName) || TextUtils.isEmpty(nFacility) || TextUtils.isEmpty(nDesc) || TextUtils.isEmpty(nStreet) || TextUtils.isEmpty(nCity) || TextUtils.isEmpty(nPostcode) || TextUtils.isEmpty(nState) || TextUtils.isEmpty(nCountry) || TextUtils.isEmpty(nPhone) || TextUtils.isEmpty(nWebsite) || TextUtils.isEmpty(nMaps) || TextUtils.isEmpty(nWaze))
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "New Facility"
        if (!imageUriNull) {
            bindingAddNearMe.addNImage.setImageURI(uploadedImage)
        }
    }
}