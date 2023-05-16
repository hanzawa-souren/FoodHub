package com.example.foodhub.admin.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.HelplineViewModel
import com.example.foodhub.database.tables.Helpline
import com.example.foodhub.databinding.FragmentAdminAddHelplineBinding

class AdminAddHelplineFragment : Fragment() {

    private lateinit var bindingAddHelpline: FragmentAdminAddHelplineBinding
    private lateinit var helplineViewModel: HelplineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAddHelpline = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_helpline, container, false)
        helplineViewModel = ViewModelProvider(this).get(HelplineViewModel::class.java)
        return bindingAddHelpline.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingAddHelpline.hPublishButton.setOnClickListener { insertItem() }
    }

    private fun insertItem() {

        val hTitle = bindingAddHelpline.editHTitle.text.toString()
        val hPhone = bindingAddHelpline.editHPhone.text.toString()
        val hWebsite = bindingAddHelpline.editHWebsite.text.toString()
        val hDesc = bindingAddHelpline.editHDesc.text.toString()

        if (inputCheck(hTitle, hPhone, hWebsite, hDesc)) {
            val helpline = Helpline(0, hTitle, hPhone, hWebsite, hDesc)
            helplineViewModel.insertHelpline(helpline)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminAddHelplineFragment_to_adminHelplinesFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(hTitle: String, hPhone: String, hWebsite: String, hDesc: String): Boolean {
        return !(TextUtils.isEmpty(hTitle) || TextUtils.isEmpty(hPhone) || TextUtils.isEmpty(hWebsite) || TextUtils.isEmpty(hDesc))
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle_salmon)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = getString(R.string.new_helpline)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).setTextColor(
            ContextCompat.getColor(requireContext(),
                R.color.salmon)
        )
        (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.admin_toolbar).setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.white)
        )
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