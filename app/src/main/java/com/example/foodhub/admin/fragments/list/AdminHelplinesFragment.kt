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
import com.example.foodhub.admin.adapters.AdminHelplineAdapter
import com.example.foodhub.admin.viewmodels.HelplineViewModel
import com.example.foodhub.databinding.FragmentAdminHelplinesBinding

class AdminHelplinesFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {

    private lateinit var bindingAdminHelplines: FragmentAdminHelplinesBinding
    private lateinit var helplineViewModel: HelplineViewModel
    private lateinit var adapter: AdminHelplineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAdminHelplines = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_helplines, container, false)
        helplineViewModel = ViewModelProvider(this).get(HelplineViewModel::class.java)
        return bindingAdminHelplines.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingAdminHelplines.addHelpline.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.adminAddHelplineFragment)
        }

        adapter = AdminHelplineAdapter()
        bindingAdminHelplines.helplinesRecycler.adapter = adapter
        bindingAdminHelplines.helplinesRecycler.layoutManager = LinearLayoutManager(requireContext())

        helplineViewModel.getAllHelpline.observe(viewLifecycleOwner, Observer { helpline ->
            adapter.setData(helpline)
        })

        bindingAdminHelplines.adminHelplinesSearchview.isSubmitButtonEnabled = true
        bindingAdminHelplines.adminHelplinesSearchview.setOnQueryTextListener(this)

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Helplines"
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_all_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete_all) {
            deleteAllHelpline()
        }
        return false
    }

    private fun deleteAllHelpline() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            helplineViewModel.deleteAllHelpline()
            Toast.makeText(
                requireContext(),
                "Successfully removed all helpline entries",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all helpline entries?")
        builder.setMessage("Are you sure you want to delete all helpline entries?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchHelplines(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchHelplines(newText)
        }
        return true
    }

    private fun searchHelplines(searchQuery: String) {
        var searchQuery = searchQuery
        searchQuery = "%$searchQuery%"

        helplineViewModel.searchHelplines(searchQuery = searchQuery).observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setData(list)
            }
        })
    }
}