/*
package com.example.foodhub.admin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.adapters.AdminLatestNewsAdapter
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.LatestNews
import com.example.foodhub.databinding.FragmentAdminLatestNewsBinding
import kotlinx.coroutines.launch

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

    private fun deleteAllNews() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            // Delete image files from internal storage upon entry removal from ROOM
            var newsList = emptyList<LatestNews>()
            val imageFileNames: MutableList<String> = mutableListOf()
            latestNewsViewModel.getAllNews.observe(viewLifecycleOwner, Observer { list ->
                newsList = list
            })
            for (item in newsList) {
                imageFileNames.add(item.lnImage.substring(item.lnImage.lastIndexOf("/")+1))
            }
            viewLifecycleOwner.lifecycleScope.launch {
                ImageStorageManager.deleteAllImagesFromInternalStorage(requireContext(), imageFileNames)
            }

            latestNewsViewModel.deleteAllNews()
            Toast.makeText(
                requireContext(),
                "Successfully removed all news entries",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all news entries?")
        builder.setMessage("Are you sure you want to delete all news entries?")
        builder.create().show()
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
}*/

package com.example.foodhub.admin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.adapters.AdminLatestNewsAdapter
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.LatestNews
import com.example.foodhub.databinding.FragmentAdminLatestNewsBinding
import kotlinx.coroutines.launch

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

    /*private fun deleteAllNews() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            // Delete image files from internal storage upon entry removal from ROOM
            var newsList = emptyList<LatestNews>()
            val imageFileNames: MutableList<String> = mutableListOf()
            latestNewsViewModel.getAllNews.observe(viewLifecycleOwner, Observer { list ->
                newsList = list
            })
            for (item in newsList) {
                imageFileNames.add(item.lnImage.substring(item.lnImage.lastIndexOf("/")+1))
            }
            viewLifecycleOwner.lifecycleScope.launch {
                ImageStorageManager.deleteAllImagesFromInternalStorage(requireContext(), imageFileNames)
            }

            latestNewsViewModel.deleteAllNews()
            Toast.makeText(
                requireContext(),
                "Successfully removed all news entries",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all news entries?")
        builder.setMessage("Are you sure you want to delete all news entries?")
        builder.create().show()
    }*/

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