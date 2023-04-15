package com.example.foodhub

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import com.example.foodhub.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController


class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    //private lateinit var view: View
    /*private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration : AppBarConfiguration*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        bindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setContentView(bindingMain.root)

        bindingMain.bottomNavView.background = null
        bindingMain.bottomNavView.menu.getItem(2).isEnabled = false

        bindingMain.fabDonate.setColorFilter(Color.rgb(255, 255, 255))

        setSupportActionBar(bindingMain.bottomAppBar)

        bindingMain.fabDonate.setOnClickListener {
            Toast.makeText(this, "Donate Button clicked", Toast.LENGTH_SHORT).show()
        }

        bindingMain.bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
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
                else -> { false }
            }
        }
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.nav_near_me -> {
                Toast.makeText(this, "Near Me selected", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.nav_helplines -> {
                Toast.makeText(this, "Helplines selected", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.nav_my_profile -> {
                Toast.makeText(this, "My Profile selected", Toast.LENGTH_SHORT).show()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }*/
}

