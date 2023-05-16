package com.example.foodhub.admin.fragments.update

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.FacilityViewModel
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.Facility
import com.example.foodhub.databinding.FragmentAdminUpdateNearMeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdminUpdateNearMeFragment : Fragment(), MenuProvider, AdapterView.OnItemSelectedListener {

    private lateinit var bindingUpdateNearMe: FragmentAdminUpdateNearMeBinding
    private val args by navArgs<AdminUpdateNearMeFragmentArgs>()
    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var uploadedImage: Uri
    private var imageBitmap: Bitmap? = null
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

        bindingUpdateNearMe = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_update_near_me, container, false)
        facilityViewModel = ViewModelProvider(this).get(FacilityViewModel::class.java)
        return bindingUpdateNearMe.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingUpdateNearMe.updateNImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Spinner
        bindingUpdateNearMe.spinnerUpdateNFacility.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.facility_types,
            R.layout.custom_spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
            bindingUpdateNearMe.spinnerUpdateNFacility.adapter = adapter

            bindingUpdateNearMe.spinnerUpdateNFacility.setSelection(adapter.getPosition(args.currentFacility.nFacility))
        }

        val imageFileName = args.currentFacility.nImage.substring(args.currentFacility.nImage.lastIndexOf("/")+1)
        Log.d("UpdateNearMeFragment", imageFileName)
        imageBitmap = ImageStorageManager.getImageFromInternalStorage(requireContext(), imageFileName)
        bindingUpdateNearMe.updateNImage.setImageBitmap(imageBitmap)

        bindingUpdateNearMe.updateNName.setText(args.currentFacility.nName)
        bindingUpdateNearMe.updateNDesc.setText(args.currentFacility.nDesc)
        bindingUpdateNearMe.updateNStreet.setText(args.currentFacility.nStreet)
        bindingUpdateNearMe.updateNCity.setText(args.currentFacility.nCity)
        bindingUpdateNearMe.updateNPostcode.setText(args.currentFacility.nPostcode)
        bindingUpdateNearMe.updateNState.setText(args.currentFacility.nState)
        bindingUpdateNearMe.updateNCountry.setText(args.currentFacility.nCountry)
        bindingUpdateNearMe.updateNPhone.setText(args.currentFacility.nPhone)
        bindingUpdateNearMe.updateNWebsite.setText(args.currentFacility.nWebsite)
        bindingUpdateNearMe.updateNPhone.setText(args.currentFacility.nPhone)
        bindingUpdateNearMe.updateNMaps.setText(args.currentFacility.nMaps)
        bindingUpdateNearMe.updateNWaze.setText(args.currentFacility.nWaze)

        bindingUpdateNearMe.nUpdateButton.setOnClickListener { updateItem() }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun updateItem() {

        val nName = bindingUpdateNearMe.updateNName.text.toString()
        val nFacility = facilityType
        val nDesc = bindingUpdateNearMe.updateNDesc.text.toString()
        val nStreet = bindingUpdateNearMe.updateNStreet.text.toString()
        val nCity = bindingUpdateNearMe.updateNCity.text.toString()
        val nPostcode = bindingUpdateNearMe.updateNPostcode.text.toString()
        val nState = bindingUpdateNearMe.updateNState.text.toString()
        val nCountry = bindingUpdateNearMe.updateNCountry.text.toString()
        val nPhone = bindingUpdateNearMe.updateNPhone.text.toString()
        val nWebsite = bindingUpdateNearMe.updateNWebsite.text.toString()
        val nMaps = bindingUpdateNearMe.updateNMaps.text.toString()
        val nWaze = bindingUpdateNearMe.updateNWaze.text.toString()

        if (inputCheck(nName, nFacility, nDesc, nStreet, nCity, nPostcode, nState, nCountry, nPhone, nWebsite, nMaps, nWaze)) {

            val currentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyyMMdd_HH_mm_ss")
            val timestamp = formatter.format(currentTime).toString()
            val imageFileName = "facility_img_$timestamp"

            var nImage = ""
            var success: Boolean = false
            if (!imageUriNull) {
                val imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, uploadedImage))

                // Save the new image file into internal storage
                viewLifecycleOwner.lifecycleScope.launch {
                    nImage = ImageStorageManager.saveToInternalStorage(requireContext(), imageBitmap, imageFileName)
                }

                // Remove the old image file from internal storage
                viewLifecycleOwner.lifecycleScope.launch {
                    success = ImageStorageManager.deleteImageFromInternalStorage(requireContext(), args.currentFacility.nImage.substring(args.currentFacility.nImage.lastIndexOf("/")+1))
                }
            }
            else {
                nImage = args.currentFacility.nImage
            }
            Log.d("Update new pic", "File absolute path: $nImage")
            Log.d("Delete old pic", "Successful?: $success")

            val facility = Facility(args.currentFacility.nId, nImage, nName, nFacility, nDesc, nStreet, nCity, nPostcode, nState, nCountry, nPhone, nWebsite, nMaps, nWaze)
            facilityViewModel.updateFacility(facility)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminUpdateNearMeFragment_to_adminNearMeFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            deleteFacility()
        }
        return false
    }

    private fun deleteFacility() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            Log.d("Photo to-be-deleted", "File path: ${args.currentFacility.nImage}")
            var success: Boolean = false
            viewLifecycleOwner.lifecycleScope.launch {
                success = ImageStorageManager.deleteImageFromInternalStorage(requireContext(), args.currentFacility.nImage.substring(args.currentFacility.nImage.lastIndexOf("/")+1))
            }
            Log.d("Deletion successful?", "File path: $success")

            facilityViewModel.deleteFacility(args.currentFacility)
            Toast.makeText(
                requireContext(),
                "Successfully removed ${args.currentFacility.nName}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_adminUpdateNearMeFragment_to_adminNearMeFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentFacility.nName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentFacility.nName}?")
        builder.create().show()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        facilityType = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        facilityType = args.currentFacility.nFacility
    }

    private fun inputCheck(nName: String, nFacility: String, nDesc: String, nStreet: String, nCity:String, nPostcode: String, nState: String, nCountry: String, nPhone: String, nWebsite: String, nMaps: String, nWaze: String): Boolean {
        return !(TextUtils.isEmpty(nName) || TextUtils.isEmpty(nFacility) || TextUtils.isEmpty(nDesc) || TextUtils.isEmpty(nStreet) || TextUtils.isEmpty(nCity) || TextUtils.isEmpty(nPostcode) || TextUtils.isEmpty(nState) || TextUtils.isEmpty(nCountry) || TextUtils.isEmpty(nPhone) || TextUtils.isEmpty(nWebsite) || TextUtils.isEmpty(nMaps) || TextUtils.isEmpty(nWaze))
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle_salmon)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = getString(R.string.edit_facility)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).setTextColor(
            ContextCompat.getColor(requireContext(),
                R.color.salmon)
        )
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.admin_toolbar).setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.white)
        )
        if (!imageUriNull) {
            bindingUpdateNearMe.updateNImage.setImageURI(uploadedImage)
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).setTextColor(
            ContextCompat.getColor(requireContext(),
                R.color.white)
        )
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.admin_toolbar).setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.salmon)
        )
    }
}