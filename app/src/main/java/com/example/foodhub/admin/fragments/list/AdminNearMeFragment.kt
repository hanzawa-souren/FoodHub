package com.example.foodhub.admin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.adapters.AdminFacilityAdapter
import com.example.foodhub.admin.viewmodels.FacilityViewModel
import com.example.foodhub.databinding.FragmentAdminNearMeBinding

class AdminNearMeFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {

    private lateinit var bindingAdminNearMe: FragmentAdminNearMeBinding
    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var adapter: AdminFacilityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_admin_near_me, container, false)
        bindingAdminNearMe = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_near_me, container, false)

        facilityViewModel = ViewModelProvider(this).get(FacilityViewModel::class.java)

        return bindingAdminNearMe.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingAdminNearMe.addNearMe.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.adminAddNearMeFragment)
        }

        adapter = AdminFacilityAdapter()
        bindingAdminNearMe.nearMeRecycler.adapter = adapter
        bindingAdminNearMe.nearMeRecycler.layoutManager = LinearLayoutManager(requireContext())

        facilityViewModel.getAllFacility.observe(viewLifecycleOwner, Observer { facility ->
            adapter.setData(facility)
        })

        bindingAdminNearMe.adminNearMeSearchview.isSubmitButtonEnabled = true
        bindingAdminNearMe.adminNearMeSearchview.setOnQueryTextListener(this)

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Facilities"
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_all_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete_all) {
            deleteAllFacility()
        }
        return false
    }

    private fun deleteAllFacility() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            facilityViewModel.deleteAllFacility()
            Toast.makeText(
                requireContext(),
                "Successfully removed all facility entries",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all facility entries?")
        builder.setMessage("Are you sure you want to delete all facility entries?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchFacilities(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchFacilities(newText)
        }
        return true
    }

    private fun searchFacilities(searchQuery: String) {
        var searchQuery = searchQuery
        searchQuery = "%$searchQuery%"

        facilityViewModel.searchFacilities(searchQuery = searchQuery).observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setData(list)
            }
        })
    }
}