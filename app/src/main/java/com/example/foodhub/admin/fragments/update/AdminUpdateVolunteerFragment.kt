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
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
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

        bindingUpdateVolunteer.vUpdateButton.setOnClickListener { updateItem() }
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
            deleteWork()
        }
        return false
    }

    private fun deleteWork() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            Log.d("Photo to-be-deleted", "File path: ${args.currentWork.vImage}")
            var success: Boolean = false
            viewLifecycleOwner.lifecycleScope.launch {
                success = ImageStorageManager.deleteImageFromInternalStorage(requireContext(), args.currentWork.vImage.substring(args.currentWork.vImage.lastIndexOf("/")+1))
            }
            Log.d("Deletion successful?", "File path: $success")

            voluntaryWorkViewModel.deleteWork(args.currentWork)
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle_salmon)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = getString(R.string.edit_work)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).setTextColor(
            ContextCompat.getColor(requireContext(),
                R.color.salmon)
        )
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.admin_toolbar).setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.white)
        )
        if (!imageUriNull) {
            bindingUpdateVolunteer.updateVImage.setImageURI(uploadedImage)
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