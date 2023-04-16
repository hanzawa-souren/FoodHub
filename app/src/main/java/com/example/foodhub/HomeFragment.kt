package com.example.foodhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
            /*Toast.makeText(this.requireActivity(), "Volunteer Category clicked", Toast.LENGTH_SHORT).show()*/
        }
        bindingHome.categoryDonateCard.setOnClickListener {
            Toast.makeText(this.requireActivity(), "Donate Category clicked", Toast.LENGTH_SHORT).show()
        }
        bindingHome.categoryLatestNewsCard.setOnClickListener {
            Toast.makeText(this.requireActivity(), "Latest News Category clicked", Toast.LENGTH_SHORT).show()
        }
        bindingHome.categoryShowMoreCard.setOnClickListener {
            Toast.makeText(this.requireActivity(), "Show More Category clicked", Toast.LENGTH_SHORT).show()
        }



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