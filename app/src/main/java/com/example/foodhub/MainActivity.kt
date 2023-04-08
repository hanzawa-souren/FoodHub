package com.example.foodhub

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.example.foodhub.databinding.ActivityMainBinding
//import com.example.foodhub.databinding.BottomAppBarBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    //private lateinit var bindingBottomAppBar: BottomAppBarBinding
    private var fullName = "Ali bin Abu"
    private var firstName = fullName.substring(0, fullName.indexOf(" "))
    private var phNum = "+6012-3456789"
    private val userInfo: UserInfo = UserInfo(fullName, firstName, phNum)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        bindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //bindingBottomAppBar = DataBindingUtil.setContentView(this, R.layout.bottom_app_bar)
        bindingMain.userInfo = userInfo

        bindingMain.bottomNavView.background = null
        bindingMain.bottomNavView.menu.getItem(2).isEnabled = false

        bindingMain.fabDonate.setColorFilter(Color.rgb(255, 255, 255))

        /*setSupportActionBar(bindingBottomAppBar.bottomAppBar)*/

        bindingMain.fabDonate.setOnClickListener {
            Toast.makeText(this, "Donate Button clicked", Toast.LENGTH_SHORT).show()
        }

        bindingMain.bottomNavView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_near_me -> {
                    Toast.makeText(this, "Near Me selected", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_helplines -> {
                    Toast.makeText(this, "Helplines selected", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_my_profile -> {
                    Toast.makeText(this, "My Profile selected", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        bindingMain.categoryVolunteerCard.setOnClickListener {
            Toast.makeText(this, "Volunteer Category clicked", Toast.LENGTH_SHORT).show()
        }
        bindingMain.categoryDonateCard.setOnClickListener {
            Toast.makeText(this, "Donate Category clicked", Toast.LENGTH_SHORT).show()
        }
        bindingMain.categoryLatestNewsCard.setOnClickListener {
            Toast.makeText(this, "Latest News Category clicked", Toast.LENGTH_SHORT).show()
        }
        bindingMain.categoryShowMoreCard.setOnClickListener {
            Toast.makeText(this, "Show More Category clicked", Toast.LENGTH_SHORT).show()
        }
    }
}

