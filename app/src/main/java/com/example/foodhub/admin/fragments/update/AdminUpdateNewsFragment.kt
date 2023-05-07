package com.example.foodhub.admin.fragments.update

import android.app.AlertDialog
import android.content.Intent
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
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.database.tables.LatestNews
import com.example.foodhub.databinding.FragmentAdminUpdateNewsBinding
import java.text.SimpleDateFormat
import java.util.*

class AdminUpdateNewsFragment : Fragment(), MenuProvider {

    private lateinit var bindingUpdateNews: FragmentAdminUpdateNewsBinding
    private val args by navArgs<AdminUpdateNewsFragmentArgs>()
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
        //return inflater.inflate(R.layout.fragment_admin_update_news, container, false)
        bindingUpdateNews = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_update_news, container, false)

        latestNewsViewModel = ViewModelProvider(this).get(LatestNewsViewModel::class.java)

        return bindingUpdateNews.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingUpdateNews.updateLnImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        bindingUpdateNews.updateLnImage.setImageURI(Uri.parse(args.currentNews.lnImage))
        bindingUpdateNews.updateLnAuthor.setText(args.currentNews.lnAuthor)
        bindingUpdateNews.updateLnTitle.setText(args.currentNews.lnTitle)
        bindingUpdateNews.updateLnContent.setText(args.currentNews.lnContent)

        bindingUpdateNews.lnUpdateButton.setOnClickListener { updateItem() }
    }

    private fun updateItem() {

        val lnImage: String
        if (!imageUriNull) {
            lnImage = uploadedImage.toString()
        }
        else {
            lnImage = args.currentNews.lnImage
        }

        val lnAuthor = bindingUpdateNews.updateLnAuthor.text.toString()
        val lnTitle = bindingUpdateNews.updateLnTitle.text.toString()
        val lnContent = bindingUpdateNews.updateLnContent.text.toString()

        if (inputCheck(lnImage, lnAuthor, lnTitle, lnContent)) {

            val lnDate: String
            if (verifyChanges(lnImage, lnAuthor, lnTitle, lnContent)) {
                val currentTime = Calendar.getInstance().time
                val formatter = SimpleDateFormat("dd MMM yyyy")
                val currentDate = formatter.format(currentTime)
                lnDate = currentDate.toString()
            }
            else {
                lnDate = args.currentNews.lnDate
            }

            val latestNews = LatestNews(args.currentNews.lnId, lnImage, lnAuthor, lnTitle, lnDate, lnContent)
            latestNewsViewModel.updateNews(latestNews)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminUpdateNewsFragment_to_adminBulletinFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(lnImage: String, lnAuthor: String, lnTitle: String, lnContent: String): Boolean {
        return !(TextUtils.isEmpty(lnImage) || TextUtils.isEmpty(lnAuthor) || TextUtils.isEmpty(lnTitle) || TextUtils.isEmpty(lnContent))
    }

    private fun verifyChanges(lnImage: String, lnAuthor: String, lnTitle: String, lnContent: String): Boolean {
        return !((lnImage == args.currentNews.lnImage) && (lnAuthor == args.currentNews.lnAuthor) && (lnTitle == args.currentNews.lnTitle) && (lnContent == args.currentNews.lnContent))
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Edit Article"
        if (!imageUriNull) {
            bindingUpdateNews.updateLnImage.setImageURI(uploadedImage)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            deleteNews()
        }
        return false
    }

    private fun deleteNews() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            latestNewsViewModel.deleteNews(args.currentNews)
            Toast.makeText(
                requireContext(),
                "Successfully removed article",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_adminUpdateNewsFragment_to_adminBulletinFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete article?")
        builder.setMessage("Are you sure you want to delete the article?")
        builder.create().show()
    }
}