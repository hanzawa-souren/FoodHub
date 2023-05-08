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
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.databinding.FragmentLatestNewsBinding
import com.example.foodhub.user.adapters.LatestNewsListAdapter

class LatestNewsFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var bindingNews: FragmentLatestNewsBinding
    private lateinit var latestNewsViewModel: LatestNewsViewModel
    private lateinit var adapter: LatestNewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingNews = DataBindingUtil.inflate(inflater,
            R.layout.fragment_latest_news, container, false)
        latestNewsViewModel = ViewModelProvider(this).get(LatestNewsViewModel::class.java)
        return bindingNews.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LatestNewsListAdapter()
        bindingNews.latestNewsList.adapter = adapter
        bindingNews.latestNewsList.layoutManager = LinearLayoutManager(requireContext())

        latestNewsViewModel.getAllNews.observe(viewLifecycleOwner, Observer { latestNews ->
            adapter.setData(latestNews)
        })

        bindingNews.userLatestNewsSearchview.isSubmitButtonEnabled = true
        bindingNews.userLatestNewsSearchview.setOnQueryTextListener(this)
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Latest News"
    }
}