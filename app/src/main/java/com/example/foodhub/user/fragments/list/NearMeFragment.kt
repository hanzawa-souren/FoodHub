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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.FacilityViewModel
import com.example.foodhub.databinding.FragmentNearMeBinding
import com.example.foodhub.user.adapters.NearMeListAdapter

class NearMeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var bindingNearMe: FragmentNearMeBinding
    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var adapter: NearMeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingNearMe = DataBindingUtil.inflate(inflater,
            R.layout.fragment_near_me, container, false)
        facilityViewModel = ViewModelProvider(this).get(FacilityViewModel::class.java)
        return bindingNearMe.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NearMeListAdapter()
        bindingNearMe.nearMeList.adapter = adapter
        bindingNearMe.nearMeList.layoutManager = GridLayoutManager(requireContext(), 2)

        facilityViewModel.getAllFacility.observe(viewLifecycleOwner, Observer { facility ->
            adapter.setData(facility)
        })

        bindingNearMe.userNearMeSearchview.isSubmitButtonEnabled = true
        bindingNearMe.userNearMeSearchview.setOnQueryTextListener(this)
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

        facilityViewModel.searchFacilities(searchQuery = searchQuery).observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setData(list)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Near Me"
    }
}