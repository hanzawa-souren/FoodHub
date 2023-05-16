/*
package com.example.foodhub.admin.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentAdminBulletinBinding
import com.google.android.material.tabs.TabLayoutMediator

class AdminBulletinFragment : Fragment() {

    private lateinit var bindingAdminBulletin: FragmentAdminBulletinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAdminBulletin = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_bulletin, container, false)
        return bindingAdminBulletin.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupTabLayout()

        bindingAdminBulletin.adminBulletinTabLayout.getTabAt(0)?.text = "Latest News"
        bindingAdminBulletin.adminBulletinTabLayout.getTabAt(1)?.text = "E-Digest"
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            bindingAdminBulletin.adminBulletinTabLayout, bindingAdminBulletin.adminBulletinViewpager
        ) { tab, position -> tab.text = (position + 1).toString() }.attach()
    }

    private fun setupViewPager() {
        val adapter = BulletinViewPagerAdapter(requireActivity(), 2)
        bindingAdminBulletin.adminBulletinViewpager.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Bulletin"
    }
}*/

package com.example.foodhub.admin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.EDigestViewModel
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.EDigest
import com.example.foodhub.database.tables.LatestNews
import com.example.foodhub.databinding.FragmentAdminBulletinBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminBulletinFragment : Fragment(), MenuProvider {

    private lateinit var bindingAdminBulletin: FragmentAdminBulletinBinding
    private lateinit var latestNewsViewModel: LatestNewsViewModel
    private lateinit var eDigestViewModel: EDigestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAdminBulletin = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_bulletin, container, false)
        latestNewsViewModel = ViewModelProvider(this).get(LatestNewsViewModel::class.java)
        eDigestViewModel = ViewModelProvider(this).get(EDigestViewModel::class.java)
        return bindingAdminBulletin.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupViewPager()
        setupTabLayout()

        bindingAdminBulletin.adminBulletinTabLayout.getTabAt(0)?.text = "Latest News"
        bindingAdminBulletin.adminBulletinTabLayout.getTabAt(1)?.text = "E-Digest"
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            bindingAdminBulletin.adminBulletinTabLayout, bindingAdminBulletin.adminBulletinViewpager
        ) { tab, position -> tab.text = (position + 1).toString() }.attach()
    }

    private fun setupViewPager() {
        val adapter = BulletinViewPagerAdapter(requireActivity(), 2)
        bindingAdminBulletin.adminBulletinViewpager.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Bulletin"
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_delete_all_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete_all) {
            when (bindingAdminBulletin.adminBulletinTabLayout.selectedTabPosition) {
                0 -> deleteAllNews()
                1 -> deleteAllDigest()
            }
        }
        return false
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

    private fun deleteAllDigest() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            // Delete image files from internal storage upon entry removal from ROOM
            var digestList = emptyList<EDigest>()
            val imageFileNames: MutableList<String> = mutableListOf()
            eDigestViewModel.getAllDigest.observe(viewLifecycleOwner, Observer { list ->
                digestList = list
            })
            for (item in digestList) {
                Log.d("Hey", "Hey")
                imageFileNames.add(item.eImage.substring(item.eImage.lastIndexOf("/")+1))
                Log.d("Haha", item.eImage.substring(item.eImage.lastIndexOf("/")+1))
            }
            viewLifecycleOwner.lifecycleScope.launch {
                ImageStorageManager.deleteAllImagesFromInternalStorage(requireContext(), imageFileNames)
            }

            eDigestViewModel.deleteAllDigest()
            Toast.makeText(
                requireContext(),
                "Successfully removed all digest entries",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all digest entries?")
        builder.setMessage("Are you sure you want to delete all digest entries?")
        builder.create().show()
    }
}