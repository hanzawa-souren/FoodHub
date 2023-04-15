package com.example.foodhub

import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.foodhub.databinding.ActivityMainBinding
import com.example.foodhub.databinding.FragmentHomeBinding
import com.google.android.material.bottomappbar.BottomAppBar

class HomeFragment : Fragment() {

    private var fullName = "Ali bin Abu"
    private var firstName = fullName.substring(0, fullName.indexOf(" "))
    private var phNum = "+6012-3456789"
    private val userInfo: UserInfo = UserInfo(fullName, firstName, phNum)

    private lateinit var bindingHome: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHome = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_home)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)

        bindingHome.userInfo = userInfo

        bindingHome.categoryVolunteerCard.setOnClickListener {
            Toast.makeText(this.requireActivity(), "Volunteer Category clicked", Toast.LENGTH_SHORT).show()
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

        return bindingHome.root
    }
}