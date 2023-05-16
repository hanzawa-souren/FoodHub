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
import com.example.foodhub.admin.viewmodels.HelplineViewModel
import com.example.foodhub.databinding.FragmentHelplinesBinding
import com.example.foodhub.user.adapters.HelplineListAdapter

class HelplinesFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var bindingHelplines: FragmentHelplinesBinding
    private lateinit var helplineViewModel: HelplineViewModel
    private lateinit var adapter: HelplineListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingHelplines = DataBindingUtil.inflate(inflater,
            R.layout.fragment_helplines, container, false)
        helplineViewModel = ViewModelProvider(this).get(HelplineViewModel::class.java)
        return bindingHelplines.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HelplineListAdapter()
        bindingHelplines.helplinesList.adapter = adapter
        bindingHelplines.helplinesList.layoutManager = LinearLayoutManager(requireContext())

        helplineViewModel.getAllHelpline.observe(viewLifecycleOwner, Observer { helpline ->
            adapter.setData(helpline)
        })

        bindingHelplines.userHelplinesSearchview.isSubmitButtonEnabled = true
        bindingHelplines.userHelplinesSearchview.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchHelplines(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = getString(R.string.helplines)
    }
}