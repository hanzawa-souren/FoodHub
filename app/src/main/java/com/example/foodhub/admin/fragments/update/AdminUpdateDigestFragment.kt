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
import com.example.foodhub.admin.viewmodels.EDigestViewModel
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.EDigest
import com.example.foodhub.databinding.FragmentAdminUpdateDigestBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdminUpdateDigestFragment : Fragment(), MenuProvider {

    private lateinit var bindingUpdateDigest: FragmentAdminUpdateDigestBinding
    private val args by navArgs<AdminUpdateDigestFragmentArgs>()
    private lateinit var eDigestViewModel: EDigestViewModel
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

        bindingUpdateDigest = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_update_digest, container, false)
        eDigestViewModel = ViewModelProvider(this).get(EDigestViewModel::class.java)
        return bindingUpdateDigest.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingUpdateDigest.updateEImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val imageFileName = args.currentDigest.eImage.substring(args.currentDigest.eImage.lastIndexOf("/")+1)
        Log.d("UpdateDigestFragment", imageFileName)
        imageBitmap = ImageStorageManager.getImageFromInternalStorage(requireContext(), imageFileName)
        bindingUpdateDigest.updateEImage.setImageBitmap(imageBitmap)

        bindingUpdateDigest.updateEAuthor.setText(args.currentDigest.eAuthor)
        bindingUpdateDigest.updateETitle.setText(args.currentDigest.eTitle)
        bindingUpdateDigest.updateEContent.setText(args.currentDigest.eContent)

        bindingUpdateDigest.eUpdateButton.setOnClickListener { updateItem() }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun updateItem() {

        val eAuthor = bindingUpdateDigest.updateEAuthor.text.toString()
        val eTitle = bindingUpdateDigest.updateETitle.text.toString()
        val eContent = bindingUpdateDigest.updateEContent.text.toString()

        if (inputCheck(eAuthor, eTitle, eContent)) {

            val currentTime2 = Calendar.getInstance().time
            val formatter2 = SimpleDateFormat("yyyyMMdd_HH_mm_ss")
            val timestamp = formatter2.format(currentTime2).toString()
            val imageFileName = "e_digest_img_$timestamp"

            var eImage = ""
            var success: Boolean = false
            if (!imageUriNull) {
                val imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, uploadedImage))

                // Save the new image file into internal storage
                viewLifecycleOwner.lifecycleScope.launch {
                    eImage = ImageStorageManager.saveToInternalStorage(requireContext(), imageBitmap, imageFileName)
                }

                // Remove the old image file from internal storage
                viewLifecycleOwner.lifecycleScope.launch {
                    success = ImageStorageManager.deleteImageFromInternalStorage(requireContext(), args.currentDigest.eImage.substring(args.currentDigest.eImage.lastIndexOf("/")+1))
                }
            }
            else {
                eImage = args.currentDigest.eImage
            }
            Log.d("Update new pic", "File absolute path: $eImage")
            Log.d("Delete old pic", "Successful?: $success")

            val eDate: String
            if (verifyChanges(eImage, eAuthor, eTitle, eContent)) {
                val currentTime = Calendar.getInstance().time
                val formatter = SimpleDateFormat("dd MMM yyyy")
                val currentDate = formatter.format(currentTime)
                eDate = currentDate.toString()
            }
            else {
                eDate = args.currentDigest.eDate
            }

            val eDigest = EDigest(args.currentDigest.eId, eImage, eAuthor, eTitle, eDate, eContent)
            eDigestViewModel.updateDigest(eDigest)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminUpdateDigestFragment_to_adminBulletinFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyChanges(eImage: String, eAuthor: String, eTitle: String, eContent: String): Boolean {
        return !((eImage == args.currentDigest.eImage) && (eAuthor == args.currentDigest.eAuthor) && (eTitle == args.currentDigest.eTitle) && (eContent == args.currentDigest.eContent))
    }

    private fun inputCheck(eAuthor: String, eTitle: String, eContent: String): Boolean {
        return !(imageUriNull || TextUtils.isEmpty(eAuthor) || TextUtils.isEmpty(eTitle) || TextUtils.isEmpty(eContent))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            deleteDigest()
        }
        return false
    }

    private fun deleteDigest() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            Log.d("Photo to-be-deleted", "File path: ${args.currentDigest.eImage}")
            var success: Boolean = false
            viewLifecycleOwner.lifecycleScope.launch {
                success = ImageStorageManager.deleteImageFromInternalStorage(requireContext(), args.currentDigest.eImage.substring(args.currentDigest.eImage.lastIndexOf("/")+1))
            }
            Log.d("Deletion successful?", "File path: $success")

            eDigestViewModel.deleteDigest(args.currentDigest)
            Toast.makeText(
                requireContext(),
                "Successfully removed digest",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_adminUpdateDigestFragment_to_adminBulletinFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete digest?")
        builder.setMessage("Are you sure you want to delete the digest?")
        builder.create().show()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle_salmon)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = getString(R.string.edit_digest)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).setTextColor(
            ContextCompat.getColor(requireContext(),
                R.color.salmon)
        )
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.admin_toolbar).setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.white)
        )
        if (!imageUriNull) {
            bindingUpdateDigest.updateEImage.setImageURI(uploadedImage)
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