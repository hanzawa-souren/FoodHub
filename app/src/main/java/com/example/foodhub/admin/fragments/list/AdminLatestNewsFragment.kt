package com.example.foodhub.admin.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.adapters.AdminLatestNewsAdapter
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.databinding.FragmentAdminLatestNewsBinding

class AdminLatestNewsFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var bindingAdminLatestNews: FragmentAdminLatestNewsBinding
    private lateinit var latestNewsViewModel: LatestNewsViewModel
    private lateinit var adapter: AdminLatestNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAdminLatestNews = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_latest_news, container, false)
        latestNewsViewModel = ViewModelProvider(this).get(LatestNewsViewModel::class.java)
        return bindingAdminLatestNews.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingAdminLatestNews.addLatestNews.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.adminAddNewsFragment)
        }

        adapter = AdminLatestNewsAdapter()
        bindingAdminLatestNews.latestNewsRecycler.adapter = adapter
        bindingAdminLatestNews.latestNewsRecycler.layoutManager = LinearLayoutManager(requireContext())

        latestNewsViewModel.getAllNews.observe(viewLifecycleOwner, Observer { latestNews ->
            adapter.setData(latestNews)
        })

        bindingAdminLatestNews.adminLatestNewsSearchview.isSubmitButtonEnabled = true
        bindingAdminLatestNews.adminLatestNewsSearchview.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNews(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNews(newText)
        }
        return true
    }

    private fun searchNews(searchQuery: String) {
        var searchQuery = searchQuery
        searchQuery = "%$searchQuery%"

        latestNewsViewModel.searchNews(searchQuery = searchQuery)
            .observe(viewLifecycleOwner, Observer { list ->
                list?.let {
                    adapter.setData(list)
                }
            })
    }
}