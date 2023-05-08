package com.example.foodhub.user.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.EDigestViewModel
import com.example.foodhub.admin.viewmodels.FacilityViewModel
import com.example.foodhub.admin.viewmodels.LatestNewsViewModel
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
import com.example.foodhub.databinding.FragmentHomeBinding
import com.example.foodhub.user.*
import com.example.foodhub.user.adapters.EDigestHomeAdapter
import com.example.foodhub.user.adapters.LatestNewsHomeAdapter
import com.example.foodhub.user.adapters.NearMeHomeAdapter
import com.example.foodhub.user.viewmodels.VoluntaryWorkHomeAdapter

class HomeFragment : Fragment() {

    private var fullName = "Ali bin Abu"
    private var firstName = fullName.substring(0, fullName.indexOf(" "))
    private var phNum = "+6012-3456789"
    private val userInfo: UserInfo = UserInfo(fullName, firstName, phNum)

    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var voluntaryWorkViewModel: VoluntaryWorkViewModel
    private lateinit var volunteerPreviewAdapter: VoluntaryWorkHomeAdapter
    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var nearMePreviewAdapter: NearMeHomeAdapter
    private lateinit var latestNewsViewModel: LatestNewsViewModel
    private lateinit var latestNewsPreviewAdapter: LatestNewsHomeAdapter
    private lateinit var eDigestViewModel: EDigestViewModel
    private lateinit var eDigestPreviewAdapter: EDigestHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)

        bindingHome = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        voluntaryWorkViewModel = ViewModelProvider(this).get(VoluntaryWorkViewModel::class.java)
        facilityViewModel = ViewModelProvider(this).get(FacilityViewModel::class.java)
        latestNewsViewModel = ViewModelProvider(this).get(LatestNewsViewModel::class.java)
        eDigestViewModel = ViewModelProvider(this).get(EDigestViewModel::class.java)

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


}