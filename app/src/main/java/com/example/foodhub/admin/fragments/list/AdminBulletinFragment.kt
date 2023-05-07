package com.example.foodhub.admin.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentAdminBulletinBinding
import com.google.android.material.tabs.TabLayoutMediator

class AdminBulletinFragment : Fragment() {

    private lateinit var bindingAdminBulletin: FragmentAdminBulletinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_admin_bulletin, container, false)
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
}