package com.example.foodhub

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.foodhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        bindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(bindingMain.root)

        bindingMain.bottomNavView.background = null
        bindingMain.bottomNavView.menu.getItem(2).isEnabled = false

        bindingMain.fabDonate.setColorFilter(Color.rgb(255, 255, 255))

        //setSupportActionBar(bindingMain.bottomAppBar)
        setSupportActionBar(bindingMain.topToolbar)

        bindingMain.fabDonate.setOnClickListener {
            Toast.makeText(this, "Donate Button clicked", Toast.LENGTH_SHORT).show()
        }

        navController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.nearMeFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        bindingMain.bottomNavView.setupWithNavController(navController)

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

