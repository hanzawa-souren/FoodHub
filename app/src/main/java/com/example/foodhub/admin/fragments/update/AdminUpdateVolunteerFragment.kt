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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodhub.R
import com.example.foodhub.admin.AdminMainActivity
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
import com.example.foodhub.database.CheckConnection
import com.example.foodhub.database.DBSyncManager
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.VoluntaryWork
import com.example.foodhub.databinding.FragmentAdminUpdateVolunteerBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdminUpdateVolunteerFragment : Fragment(), MenuProvider {

    private lateinit var bindingUpdateVolunteer: FragmentAdminUpdateVolunteerBinding
    private val args by navArgs<AdminUpdateVolunteerFragmentArgs>()
    private lateinit var voluntaryWorkViewModel: VoluntaryWorkViewModel
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

    private val checkConnection by lazy { CheckConnection((activity as AppCompatActivity).application) }

    private var internetUp = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingUpdateVolunteer = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_update_volunteer, container, false)
        voluntaryWorkViewModel = ViewModelProvider(this).get(VoluntaryWorkViewModel::class.java)
        return bindingUpdateVolunteer.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingUpdateVolunteer.updateVImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val imageFileName = args.currentWork.vImage.substring(args.currentWork.vImage.lastIndexOf("/")+1)
        Log.d("UpdateVolunteerFragment", imageFileName)
        imageBitmap = ImageStorageManager.getImageFromInternalStorage(requireContext(), imageFileName)
        bindingUpdateVolunteer.updateVImage.setImageBitmap(imageBitmap)

        bindingUpdateVolunteer.updateVTitle.setText(args.currentWork.vTitle)
        bindingUpdateVolunteer.updateVDesc.setText(args.currentWork.vDesc)
        bindingUpdateVolunteer.updateVStreet.setText(args.currentWork.vStreet)
        bindingUpdateVolunteer.updateVCity.setText(args.currentWork.vCity)
        bindingUpdateVolunteer.updateVPostcode.setText(args.currentWork.vPostcode)
        bindingUpdateVolunteer.updateVState.setText(args.currentWork.vState)
        bindingUpdateVolunteer.updateVCountry.setText(args.currentWork.vCountry)
        bindingUpdateVolunteer.updateVPhone.setText(args.currentWork.vPhone)
        bindingUpdateVolunteer.updateVWebsite.setText(args.currentWork.vWebsite)
        bindingUpdateVolunteer.updateVReglink.setText(args.currentWork.vRegLink)
        bindingUpdateVolunteer.updateVMaps.setText(args.currentWork.vMaps)
        bindingUpdateVolunteer.updateVWaze.setText(args.currentWork.vWaze)

        bindingUpdateVolunteer.vUpdateButton.setOnClickListener {

            checkConnection.observe((activity as AppCompatActivity)) {
                if (it) {
                    internetUp = true
                }
            }

            if (internetUp) {
                updateItem()
            }
            else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("OK") { _, _ -> }
                builder.setTitle("No Internet Connection")
                builder.setMessage("Please try again later")
                builder.create().show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun updateItem() {

        val vTitle = bindingUpdateVolunteer.updateVTitle.text.toString()
        val vDesc = bindingUpdateVolunteer.updateVDesc.text.toString()
        val vStreet = bindingUpdateVolunteer.updateVStreet.text.toString()
        val vCity = bindingUpdateVolunteer.updateVCity.text.toString()
        val vPostcode = bindingUpdateVolunteer.updateVPostcode.text.toString()
        val vState = bindingUpdateVolunteer.updateVState.text.toString()
        val vCountry = bindingUpdateVolunteer.updateVCountry.text.toString()
        val vPhone = bindingUpdateVolunteer.updateVPhone.text.toString()
        val vWebsite = bindingUpdateVolunteer.updateVWebsite.text.toString()
        val vReglink = bindingUpdateVolunteer.updateVReglink.text.toString()
        val vMaps = bindingUpdateVolunteer.updateVMaps.text.toString()
        val vWaze = bindingUpdateVolunteer.updateVWaze.text.toString()

        if (inputCheck(vTitle, vDesc, vStreet, vCity, vPostcode, vState, vCountry, vPhone, vWebsite, vReglink, vMaps, vWaze)) {

            var duplicateWork: Boolean = false

            voluntaryWorkViewModel.searchWorks(vTitle).observe(viewLifecycleOwner, Observer { workFound ->
                if (workFound != null) {
                    duplicateWork = true
                }
            })

            if (!duplicateWork) {
                val currentTime = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyyMMdd_HH_mm_ss")
                val timestamp = formatter.format(currentTime).toString()
                val imageFileName = "v_work_img_$timestamp"

                var vImage = ""
                var success: Boolean = false
                if (!imageUriNull) {
                    val imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, uploadedImage))

                    // Save the new image file into internal storage
                    viewLifecycleOwner.lifecycleScope.launch {
                        vImage = ImageStorageManager.saveToInternalStorage(requireContext(), imageBitmap, imageFileName)
                    }

                    // Remove the old image file from internal storage
                    viewLifecycleOwner.lifecycleScope.launch {
                        success = ImageStorageManager.deleteImageFromInternalStorage(requireContext(), args.currentWork.vImage.substring(args.currentWork.vImage.lastIndexOf("/")+1))
                    }
                }
                else {
                    vImage = args.currentWork.vImage
                }
                Log.d("Update new pic", "File absolute path: $vImage")
                Log.d("Delete old pic", "Successful?: $success")

                val voluntaryWork = VoluntaryWork(args.currentWork.vId, vImage, vTitle, vDesc, vStreet, vCity, vPostcode, vState, vCountry, vWebsite, vPhone, vReglink, vMaps, vWaze)
                voluntaryWorkViewModel.updateWork(voluntaryWork)
                Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_adminUpdateVolunteerFragment_to_adminVolunteerFragment)
            }
            else {
                Toast.makeText(requireContext(), "Title already exists.", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(vTitle: String, vDesc: String, vStreet: String, vCity: String, vPostcode: String, vState: String, vCountry: String, vPhone: String, vWebsite: String, vReglink: String, vMaps: String, vWaze: String): Boolean {
        return !(TextUtils.isEmpty(vTitle) || TextUtils.isEmpty(vDesc) || TextUtils.isEmpty(vStreet) || TextUtils.isEmpty(vCity) || TextUtils.isEmpty(vPostcode) || TextUtils.isEmpty(vState) || TextUtils.isEmpty(vCountry) || TextUtils.isEmpty(vPhone) || TextUtils.isEmpty(vWebsite) || TextUtils.isEmpty(vReglink) || TextUtils.isEmpty(vMaps) || TextUtils.isEmpty(vWaze))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {

            checkConnection.observe((activity as AppCompatActivity)) {
                if (it) {
                    internetUp = true
                }
            }

            if (internetUp) {

                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    deleteWork()
                    Toast.makeText(
                        requireContext(),
                        "Successfully removed ${args.currentWork.vTitle}",
                        Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_adminUpdateVolunteerFragment_to_adminVolunteerFragment)
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.setTitle("Delete ${args.currentWork.vTitle}?")
                builder.setMessage("Are you sure you want to delete ${args.currentWork.vTitle}?")
                builder.create().show()
            }
            else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("OK") { _, _ -> }
                builder.setTitle("No Internet Connection")
                builder.setMessage("Please try again later")
                builder.create().show()
            }

        }
        return false
    }

    private fun deleteWork() {
        Log.d("Photo to-be-deleted", "File path: ${args.currentWork.vImage}")
        var success: Boolean = false
        viewLifecycleOwner.lifecycleScope.launch {
            success = ImageStorageManager.deleteImageFromInternalStorage(requireContext(), args.currentWork.vImage.substring(args.currentWork.vImage.lastIndexOf("/")+1))
        }
        Log.d("Deletion successful?", "File path: $success")

        voluntaryWorkViewModel.deleteWork(args.currentWork)
        // Sync to remote DB
        DBSyncManager.deleteWork(viewLifecycleOwner, requireContext(), args.currentWork)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Edit Work"
        if (!imageUriNull) {
            bindingUpdateVolunteer.updateVImage.setImageURI(uploadedImage)
        }

        bindingUpdateVolunteer.apply {
            checkConnection.observe(viewLifecycleOwner) {
                if (it) {
                    (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_no_internet_bar).visibility = View.GONE
                    //internetUp = true
                }
                else {
                    (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_no_internet_bar).visibility = View.VISIBLE
                    //internetUp = false
                }
            }
        }
    }
}