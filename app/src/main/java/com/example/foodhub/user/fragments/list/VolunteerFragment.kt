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
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
import com.example.foodhub.databinding.FragmentVolunteerBinding
import com.example.foodhub.user.adapters.VoluntaryWorkListAdapter

class VolunteerFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var bindingVolunteer: FragmentVolunteerBinding
    private lateinit var voluntaryWorkViewModel: VoluntaryWorkViewModel
    private lateinit var adapter: VoluntaryWorkListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingVolunteer = DataBindingUtil.inflate(inflater,
            R.layout.fragment_volunteer, container, false)
        voluntaryWorkViewModel = ViewModelProvider(this).get(VoluntaryWorkViewModel::class.java)
        return bindingVolunteer.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VoluntaryWorkListAdapter()
        bindingVolunteer.volunteerList.adapter = adapter
        bindingVolunteer.volunteerList.layoutManager = GridLayoutManager(requireContext(), 2)

        voluntaryWorkViewModel.getAllWork.observe(viewLifecycleOwner, Observer { voluntaryWork ->
            adapter.setData(voluntaryWork)
        })

        bindingVolunteer.userVolunteerSearchview.isSubmitButtonEnabled = true
        bindingVolunteer.userVolunteerSearchview.setOnQueryTextListener(this)
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
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = getString(R.string.volunteer)
    }
}
