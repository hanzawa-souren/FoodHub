package com.example.foodhub.user.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.EDigestViewModel
import com.example.foodhub.databinding.FragmentEdigestBinding
import com.example.foodhub.user.adapters.EDigestListAdapter

class EdigestFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var bindingEdigest: FragmentEdigestBinding
    private lateinit var eDigestViewModel: EDigestViewModel
    private lateinit var adapter: EDigestListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingEdigest = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edigest, container, false)
        eDigestViewModel = ViewModelProvider(this).get(EDigestViewModel::class.java)
        return bindingEdigest.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EDigestListAdapter()
        bindingEdigest.eDigestList.adapter = adapter
        bindingEdigest.eDigestList.layoutManager = LinearLayoutManager(requireContext())

        eDigestViewModel.getAllDigest.observe(viewLifecycleOwner, Observer { eDigest ->
            adapter.setData(eDigest)
        })

        bindingEdigest.userEDigestSearchview.isSubmitButtonEnabled = true
        bindingEdigest.userEDigestSearchview.setOnQueryTextListener(this)
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "E-Digests"
    }
}