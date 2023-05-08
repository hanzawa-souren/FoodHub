package com.example.foodhub.user

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.foodhub.R
import com.example.foodhub.databinding.ActivityMainBinding
import com.example.foodhub.user.settings.UsernameSetting

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    private lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        bindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(bindingMain.root)

        bindingMain.bottomNavView.background = null
        bindingMain.bottomNavView.menu.getItem(2).isEnabled = false

        bindingMain.fabDonate.setColorFilter(Color.rgb(255, 255, 255))

        navController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.nearMeFragment,
            R.id.donateFragment,
            R.id.helplinesFragment,
            R.id.myProfileFragment
        ))



        setSupportActionBar(bindingMain.topToolbar)

        /*bindingMain.fabDonate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                Navigation.findNavController(this@MainActivity, R.id.myNavHostFragment)
                    .navigate(R.id.donateFragment)
            }
        })*/

        bindingMain.fabDonate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (navController.currentDestination?.id != R.id.donateFragment) {
                    Navigation.findNavController(this@MainActivity, R.id.myNavHostFragment)
                        .navigate(R.id.donateFragment)
                }
            }
        })

        setupActionBarWithNavController(navController, appBarConfiguration)

        /*bindingMain.bottomNavView.setupWithNavController(navController)*/

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

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }


    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_toolbar_menu, menu)
        return true
    }*/
}


