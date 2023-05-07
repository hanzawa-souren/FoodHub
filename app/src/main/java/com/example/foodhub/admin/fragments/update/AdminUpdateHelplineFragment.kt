package com.example.foodhub.admin.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.HelplineViewModel
import com.example.foodhub.database.tables.Helpline
import com.example.foodhub.databinding.FragmentAdminUpdateHelplineBinding

class AdminUpdateHelplineFragment : Fragment(), MenuProvider {

    private lateinit var bindingUpdateHelpline: FragmentAdminUpdateHelplineBinding
    private val args by navArgs<AdminUpdateHelplineFragmentArgs>()
    private lateinit var helplineViewModel: HelplineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_admin_update_helpline, container, false)
        bindingUpdateHelpline = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_update_helpline, container, false)

        helplineViewModel = ViewModelProvider(this).get(HelplineViewModel::class.java)

        return bindingUpdateHelpline.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingUpdateHelpline.updateHTitle.setText(args.currentHelpline.hTitle)
        bindingUpdateHelpline.updateHPhone.setText(args.currentHelpline.hPhone)
        bindingUpdateHelpline.updateHWebsite.setText(args.currentHelpline.hWebsite)
        bindingUpdateHelpline.updateHDesc.setText(args.currentHelpline.hDesc)

        bindingUpdateHelpline.hUpdateButton.setOnClickListener { updateItem() }
    }

    private fun updateItem() {

        val hTitle = bindingUpdateHelpline.updateHTitle.text.toString()
        val hPhone = bindingUpdateHelpline.updateHPhone.text.toString()
        val hWebsite = bindingUpdateHelpline.updateHWebsite.text.toString()
        val hDesc = bindingUpdateHelpline.updateHDesc.text.toString()

        if (inputCheck(hTitle, hPhone, hWebsite, hDesc)) {
            val helpline = Helpline(args.currentHelpline.hId, hTitle, hPhone, hWebsite, hDesc)
            helplineViewModel.updateHelpline(helpline)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adminUpdateHelplineFragment_to_adminHelplinesFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(hTitle: String, hPhone: String, hWebsite: String, hDesc: String): Boolean {
        return !(TextUtils.isEmpty(hTitle) || TextUtils.isEmpty(hPhone) || TextUtils.isEmpty(hWebsite) || TextUtils.isEmpty(hDesc))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            deleteHelpline()
        }
        return false
    }

    private fun deleteHelpline() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            helplineViewModel.deleteHelpline(args.currentHelpline)
            Toast.makeText(
                requireContext(),
                "Successfully removed ${args.currentHelpline.hTitle}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_adminUpdateHelplineFragment_to_adminHelplinesFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentHelpline.hTitle}?")
        builder.setMessage("Are you sure you want to delete ${args.currentHelpline.hTitle}?")
        builder.create().show()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Edit Helpline"
    }
}