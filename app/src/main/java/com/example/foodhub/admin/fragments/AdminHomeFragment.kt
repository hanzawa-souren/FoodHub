package com.example.foodhub.admin.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.SyncStateContract
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.foodhub.R
import com.example.foodhub.admin.AdminMainActivity
import com.example.foodhub.admin.viewmodels.*
import com.example.foodhub.databinding.FragmentAdminHomeBinding
import com.example.foodhub.login.LoginActivity
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.MainActivity

class AdminHomeFragment : Fragment(), MenuProvider {

    private lateinit var bindingAdminHome: FragmentAdminHomeBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var donationViewModel: DonationViewModel
    private lateinit var voluntaryWorkViewModel: VoluntaryWorkViewModel
    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var helplineViewModel: HelplineViewModel
    private lateinit var newsViewModel: LatestNewsViewModel
    private lateinit var eDigestViewModel: EDigestViewModel

    private lateinit var aContext: AdminMainActivity
    private var doubleBackToExitPressedOnce = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingAdminHome = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        donationViewModel = ViewModelProvider(this).get(DonationViewModel::class.java)
        voluntaryWorkViewModel = ViewModelProvider(this).get(VoluntaryWorkViewModel::class.java)
        facilityViewModel = ViewModelProvider(this).get(FacilityViewModel::class.java)
        helplineViewModel = ViewModelProvider(this).get(HelplineViewModel::class.java)
        newsViewModel = ViewModelProvider(this).get(LatestNewsViewModel::class.java)
        eDigestViewModel = ViewModelProvider(this).get(EDigestViewModel::class.java)

        return bindingAdminHome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        bindingAdminHome.donationsCount.isSelected = true

        userViewModel.getUserCount().observe(viewLifecycleOwner, Observer { totalUser ->
            if (totalUser != null) {
                var totalUser = totalUser
                //--totalUser        // Remove admin from total registered users
                bindingAdminHome.usersCount.text = totalUser.toString()
            }
        })

        donationViewModel.getTotalDonationAmount().observe(viewLifecycleOwner, Observer { totalAmount ->
            if (totalAmount != null) {
                bindingAdminHome.donationsCount.text = String.format("%.2f", totalAmount)
            }

        })

        voluntaryWorkViewModel.getWorkCount().observe(viewLifecycleOwner, Observer { totalWork ->
            if (totalWork != null) {
                bindingAdminHome.workCount.text = totalWork.toString()
            }
        })

        facilityViewModel.getFacilityCount().observe(viewLifecycleOwner, Observer { totalFacility ->
            if (totalFacility != null) {
                bindingAdminHome.facilityCount.text = totalFacility.toString()
            }
        })

        helplineViewModel.getHelplineCount().observe(viewLifecycleOwner, Observer { totalHelpline ->
            if (totalHelpline != null) {
                bindingAdminHome.helplineCount.text = totalHelpline.toString()
            }
        })

        newsViewModel.getNewsCount().observe(viewLifecycleOwner, Observer { totalNews ->
            if (totalNews != null) {
                bindingAdminHome.newsCount.text = totalNews.toString()
            }
        })

        eDigestViewModel.getDigestCount().observe(viewLifecycleOwner, Observer { totalDigest ->
            if (totalDigest != null) {
                bindingAdminHome.digestCount.text = totalDigest.toString()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.admin_toolbar_title).text = "Dashboard"
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.admin_home_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_logout) {
            requireActivity().run {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    @Suppress("DEPRECATION")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        aContext = context as AdminMainActivity

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    activity?.finish()
                }
                else {
                    Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                }
                doubleBackToExitPressedOnce = true
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}