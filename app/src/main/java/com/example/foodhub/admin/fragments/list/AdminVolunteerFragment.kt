package com.example.foodhub.admin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.adapters.AdminVoluntaryWorkAdapter
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.VoluntaryWork
import com.example.foodhub.databinding.FragmentAdminVolunteerBinding
import kotlinx.coroutines.launch

class AdminVolunteerFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener  {

    private lateinit var bindingAdminVolunteer: FragmentAdminVolunteerBinding
    private lateinit var voluntaryWorkViewModel: VoluntaryWorkViewModel
    private lateinit var adapter: AdminVoluntaryWorkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingAdminVolunteer = DataBindingUtil.inflate(inflater,
            R.layout.fragment_admin_volunteer, container, false)
        voluntaryWorkViewModel = ViewModelProvider(this).get(VoluntaryWorkViewModel::class.java)
        return bindingAdminVolunteer.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingAdminVolunteer.addVolunteer.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.adminAddVolunteerFragment)
        }

        adapter = AdminVoluntaryWorkAdapter()
        bindingAdminVolunteer.volunteerRecycler.adapter = adapter
        bindingAdminVolunteer.volunteerRecycler.layoutManager = LinearLayoutManager(requireContext())

        voluntaryWorkViewModel.getAllWork.observe(viewLifecycleOwner, Observer { voluntaryWork ->
            adapter.setData(voluntaryWork)
        })

        // Search from adapter
        bindingAdminVolunteer.adminVolunteerSearchview.isSubmitButtonEnabled = true
        bindingAdminVolunteer.adminVolunteerSearchview.setOnQueryTextListener(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_all_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete_all) {
            deleteAllWork()
        }
        return false
    }

    private fun deleteAllWork() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            // Delete image files from internal storage upon entry removal from ROOM
            var vWorkList = emptyList<VoluntaryWork>()
            val imageFileNames: MutableList<String> = mutableListOf()
            voluntaryWorkViewModel.getAllWork.observe(viewLifecycleOwner, Observer { list ->
                vWorkList = list
            })
            for (item in vWorkList) {
                imageFileNames.add(item.vImage.substring(item.vImage.lastIndexOf("/")+1))
            }
            viewLifecycleOwner.lifecycleScope.launch {
                ImageStorageManager.deleteAllImagesFromInternalStorage(requireContext(), imageFileNames)
            }

            voluntaryWorkViewModel.deleteAllWork()
            Toast.makeText(
                requireContext(),
                "Successfully removed all work entries",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all work entries?")
        builder.setMessage("Are you sure you want to delete all work entries?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchWorks(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchWorks(newText)
        }
        return true
    }

    private fun searchWorks(searchQuery: String) {
        var searchQuery = searchQuery
        searchQuery = "%$searchQuery%"

        voluntaryWorkViewModel.searchWorks(searchQuery = searchQuery).observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setData(list)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Voluntary Works"
    }

}