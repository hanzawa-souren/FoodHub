package com.example.foodhub.user.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.AdminMainActivity
import com.example.foodhub.admin.viewmodels.EDigestViewModel
import com.example.foodhub.admin.viewmodels.FacilityViewModel
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
import com.example.foodhub.databinding.FragmentHomeBinding
import com.example.foodhub.login.User
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.*
import com.example.foodhub.user.adapters.EDigestHomeAdapter
import com.example.foodhub.user.adapters.LatestNewsHomeAdapter
import com.example.foodhub.user.adapters.NearMeHomeAdapter
import com.example.foodhub.user.adapters.ProfileAdapter
import com.example.foodhub.user.viewmodels.VoluntaryWorkHomeAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private val viewModel: DonateViewModal by activityViewModels()
    private lateinit var fullName: String
//    private var firstName = fullName.substring(0, fullName.indexOf(" "))
//    private var phNum = "+6012-3456789"
    private lateinit var userInfo: UserInfo
    private val userViewModel : UserViewModel by activityViewModels()
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var voluntaryWorkViewModel: VoluntaryWorkViewModel
    private lateinit var volunteerPreviewAdapter: VoluntaryWorkHomeAdapter
    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var nearMePreviewAdapter: NearMeHomeAdapter
    private lateinit var latestNewsViewModel: LatestNewsViewModel
    private lateinit var latestNewsPreviewAdapter: LatestNewsHomeAdapter
    private lateinit var eDigestViewModel: EDigestViewModel
    private lateinit var eDigestPreviewAdapter: EDigestHomeAdapter

    private lateinit var aContext: MainActivity
    private var doubleBackToExitPressedOnce = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        bindingHome = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        voluntaryWorkViewModel = ViewModelProvider(this).get(VoluntaryWorkViewModel::class.java)
        facilityViewModel = ViewModelProvider(this).get(FacilityViewModel::class.java)
        latestNewsViewModel = ViewModelProvider(this).get(LatestNewsViewModel::class.java)
        eDigestViewModel = ViewModelProvider(this).get(EDigestViewModel::class.java)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        @Suppress("DEPRECATION")
        val user : User = activity?.intent?.getParcelableExtra("User")!!
        if (userViewModel.changedHome) {
            userInfo = UserInfo(user.loginID, user.loginID, user.phNum)
        }
        else {
            userInfo = UserInfo(userViewModel.changedUsername.value.toString(), userViewModel.changedUsername.value.toString(), user.phNum )
        }

        return bindingHome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingHome.userInfo = userInfo

        bindingHome.categoryVolunteerCard.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.volunteerFragment)
        }
        bindingHome.categoryLatestNewsCard.setOnClickListener {
            view.findNavController().navigate(R.id.latestNewsFragment)
        }
        bindingHome.categoryEdigestCard.setOnClickListener {
            view.findNavController().navigate(R.id.edigestFragment)
        }
        bindingHome.categorySettingsCard.setOnClickListener {
            view.findNavController().navigate(R.id.settingsFragment)
        }

        setVolunteerPreviewScroll()
        setNearMePreviewScroll()
        setLatestNewsPreviewScroll()
        setEDigestPreviewScroll()

        bindingHome.volunteerPreviewTitle.setOnClickListener { view: View ->
            findNavController().navigate(R.id.volunteerFragment)
        }
        bindingHome.nearMePreviewTitle.setOnClickListener { view: View ->
            //findNavController().navigate(R.id.nearMeFragment)
            (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottom_nav_view).selectedItemId = R.id.nearMeFragment
        }
        bindingHome.latestNewsPreviewTitle.setOnClickListener { view: View ->
            findNavController().navigate(R.id.latestNewsFragment)
        }
        bindingHome.edigestPreviewTitle.setOnClickListener { view: View ->
            findNavController().navigate(R.id.edigestFragment)
        }
        @Suppress("DEPRECATION")
        var name : User = activity?.intent?.getParcelableExtra("User")!!

//        var donateAmount : Double = mUserViewModel.getUserDonation(name.loginID)
        var donateAmount : Double = viewModel.donateAmounts.value?:0.0

        viewModel.name.value = name.loginID

        mUserViewModel.getUserDonation(name.loginID).observe(viewLifecycleOwner, Observer { amount ->

            viewModel.donateAmounts.value = amount
        })


        mUserViewModel.getUserDateDay(name.loginID).observe(viewLifecycleOwner, Observer { day ->

            viewModel.day.value = day
        })
        mUserViewModel.getUserDateMonth(name.loginID).observe(viewLifecycleOwner, Observer { month ->

            viewModel.month.value = month
        })
        mUserViewModel.getUserDateYear(name.loginID).observe(viewLifecycleOwner, Observer { year ->

            viewModel.year.value = year
        })




    }

    private fun setVolunteerPreviewScroll() {
        volunteerPreviewAdapter = VoluntaryWorkHomeAdapter()
        bindingHome.volunteerPreview.adapter = volunteerPreviewAdapter
        bindingHome.volunteerPreview.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        voluntaryWorkViewModel.getAllWork.observe(viewLifecycleOwner, Observer { voluntaryWork ->
            volunteerPreviewAdapter.setData(voluntaryWork)
        })
    }

    private fun setNearMePreviewScroll() {
        nearMePreviewAdapter = NearMeHomeAdapter()
        bindingHome.nearMePreview.adapter = nearMePreviewAdapter
        bindingHome.nearMePreview.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        facilityViewModel.getAllFacility.observe(viewLifecycleOwner, Observer { facility ->
            nearMePreviewAdapter.setData(facility)
        })
    }

    private fun setLatestNewsPreviewScroll() {
        latestNewsPreviewAdapter = LatestNewsHomeAdapter()
        bindingHome.latestNewsPreview.adapter = latestNewsPreviewAdapter
        bindingHome.latestNewsPreview.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        latestNewsViewModel.getAllNews.observe(viewLifecycleOwner, Observer { latestNews ->
            latestNewsPreviewAdapter.setData(latestNews)
        })
    }

    private fun setEDigestPreviewScroll() {
        eDigestPreviewAdapter = EDigestHomeAdapter()
        bindingHome.edigestPreview.adapter = eDigestPreviewAdapter
        bindingHome.edigestPreview.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        eDigestViewModel.getAllDigest.observe(viewLifecycleOwner, Observer { digest ->
            eDigestPreviewAdapter.setData(digest)
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.top_toolbar).visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.top_toolbar).visibility = View.VISIBLE
    }

    @Suppress("DEPRECATION")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        aContext = context as MainActivity

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    activity?.finishAffinity()
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