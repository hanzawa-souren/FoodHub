package com.example.foodhub.user

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.foodhub.R
import com.example.foodhub.databinding.ActivityMainBinding
import com.example.foodhub.user.viewmodels.DonateViewModal

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        bindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(bindingMain.root)

        bindingMain.bottomNavView.background = null
        bindingMain.bottomNavView.menu.getItem(2).isEnabled = false

        bindingMain.fabDonate.setColorFilter(Color.rgb(255, 255, 255))

        navController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.nearMeFragment,
            R.id.donateFragment2,
            R.id.helplinesFragment,
            R.id.myProfileFragment
        ))

        setSupportActionBar(bindingMain.topToolbar)

        bindingMain.fabDonate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (navController.currentDestination?.id != R.id.donateFragment2) {
                    Navigation.findNavController(this@MainActivity, R.id.myNavHostFragment)
                        .navigate(R.id.donateFragment2)
                }
                else {
                    navController.navigateUp()
                }
            }
        })

        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        bindingMain.bottomNavView.apply {
            navController.let { navController ->
                NavigationUI.setupWithNavController(this, navController)
            }
            setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, navController)
                true
            }
            setOnItemReselectedListener {
                navController.popBackStack(destinationId = it.itemId, inclusive = false)
            }
        }
        val viewModel = ViewModelProvider(this)[DonateViewModal::class.java]
        var userID = intent.getStringExtra("userID")
        viewModel.setString(userID?:"")
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}

