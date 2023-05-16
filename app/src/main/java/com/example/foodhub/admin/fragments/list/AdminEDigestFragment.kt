package com.example.foodhub.admin.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.adapters.AdminEDigestAdapter
import com.example.foodhub.admin.viewmodels.EDigestViewModel
import com.example.foodhub.databinding.FragmentAdminEDigestBinding

class AdminEDigestFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var bindingAdminEDigest: FragmentAdminEDigestBinding
    private lateinit var eDigestViewModel: EDigestViewModel
    private lateinit var adapter: AdminEDigestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAdminEDigest = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_e_digest, container, false)
        eDigestViewModel = ViewModelProvider(this).get(EDigestViewModel::class.java)
        return bindingAdminEDigest.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingAdminEDigest.addEDigest.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.adminAddDigestFragment)
        }

        adapter = AdminEDigestAdapter()
        bindingAdminEDigest.eDigestRecycler.adapter = adapter
        bindingAdminEDigest.eDigestRecycler.layoutManager = LinearLayoutManager(requireContext())

        eDigestViewModel.getAllDigest.observe(viewLifecycleOwner, Observer { eDigest ->
            adapter.setData(eDigest)
        })

        bindingAdminEDigest.adminEDigestSearchview.isSubmitButtonEnabled = true
        bindingAdminEDigest.adminEDigestSearchview.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDigests(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchDigests(newText)
        }
        return true
    }

    private fun searchDigests(searchQuery: String) {
        var searchQuery = searchQuery
        searchQuery = "%$searchQuery%"

        eDigestViewModel.searchDigests(searchQuery = searchQuery).observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setData(list)
            }
        })
    }
}