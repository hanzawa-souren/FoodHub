package com.example.foodhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var fullName = "Ali bin Abu"
    private var firstName = fullName.substring(0, fullName.indexOf(" "))
    private var phNum = "+6012-3456789"
    private val userInfo: UserInfo = UserInfo(fullName, firstName, phNum)

    private lateinit var bindingHome: FragmentHomeBinding

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

        /*Volunteer Preview RecyclerView starts here*/
        //Call RecyclerView, LayoutManager, Adapter
        bindingHome.volunteerPreview.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        //ArrayList of class VolunteerModel
        val volunteerData = ArrayList<VolunteerModel>()

        //The loop creates 4 views
        for (i in 1..4) {
            volunteerData.add(VolunteerModel(R.drawable.ic_fhlogo_background, "Volunteer $i", "Location $i"))
        }

        //Pass ArrayList into Adapter
        val volunteerAdapter = VolunteerAdapter(volunteerData)

        //Set Adapter with RecyclerView
        bindingHome.volunteerPreview.adapter = volunteerAdapter
        /*Volunteer Preview RecyclerView ends here*/

        /*Near Me Preview RecyclerView starts here*/
        bindingHome.nearMePreview.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val nearMeData = ArrayList<NearMeModel>()

        for (i in 1..4) {
            nearMeData.add(NearMeModel(R.drawable.ic_fhlogo_background, "Near Me $i", "Facility $i", "Location $i"))
        }

        val nearMeAdapter = NearMeAdapter(nearMeData)

        bindingHome.nearMePreview.adapter = nearMeAdapter
        /*Near Me Preview RecyclerView ends here*/

        /*Latest News Preview RecyclerView starts here*/
        bindingHome.latestNewsPreview.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val latestNewsData = ArrayList<LatestNewsModel>()

        for (i in 1..4) {
            latestNewsData.add(LatestNewsModel(R.drawable.ic_fhlogo_background, "News $i", "Date $i"))
        }

        val latestNewsAdapter = LatestNewsAdapter(latestNewsData)

        bindingHome.latestNewsPreview.adapter = latestNewsAdapter
        /*Latest News Preview RecyclerView ends here*/

        /*Edigest Preview RecyclerView starts here*/
        bindingHome.edigestPreview.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val edigestData = ArrayList<EdigestModel>()

        for (i in 1..4) {
            edigestData.add(EdigestModel(R.drawable.ic_fhlogo_background, "Digest $i", "Date $i"))
        }

        val edigestAdapter = EdigestAdapter(edigestData)

        bindingHome.edigestPreview.adapter = edigestAdapter
        /*Edigest Preview RecyclerView ends here*/

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