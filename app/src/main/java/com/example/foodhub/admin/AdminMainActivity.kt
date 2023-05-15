package com.example.foodhub.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.foodhub.R
import com.example.foodhub.database.CheckConnection
import com.example.foodhub.database.DBSyncManager
import com.example.foodhub.databinding.ActivityAdminMainBinding

class AdminMainActivity : AppCompatActivity() {

    private lateinit var bindingAdminMain: ActivityAdminMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration
    //private val checkConnection by lazy { CheckConnection(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingAdminMain = DataBindingUtil.setContentView(this, R.layout.activity_admin_main)

        navController = findNavController(R.id.adminNavHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.adminHomeFragment, R.id.adminVolunteerFragment, R.id.adminNearMeFragment, R.id.adminHelplinesFragment, R.id.adminBulletinFragment))

        setSupportActionBar(bindingAdminMain.adminToolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        bindingAdminMain.adminBottomNavView.apply {
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

        /*bindingAdminMain.apply {
            checkConnection.observe(this@AdminMainActivity) {
                if (it) {

                    DBSyncManager.syncWorkFromRemote(this@AdminMainActivity, this@AdminMainActivity)
                }
                else {

                    Toast.makeText(this@AdminMainActivity, "No Internet connection", Toast.LENGTH_SHORT).show()
                }
            }
        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}