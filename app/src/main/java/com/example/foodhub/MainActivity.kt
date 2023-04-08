package com.example.foodhub

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.example.foodhub.databinding.ActivityMainBinding
import com.google.android.material.bottomappbar.BottomAppBar


class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private var fullName = "Ali bin Abu"
    private var firstName = fullName.substring(0, fullName.indexOf(" "))
    private var phNum = "+6012-3456789"
    private val userInfo: UserInfo = UserInfo(fullName, firstName, phNum)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        bindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindingMain.userInfo = userInfo

        bindingMain.bottomNavView.background = null
        bindingMain.bottomNavView.menu.getItem(2).isEnabled = false

        bindingMain.fabDonate.setColorFilter(Color.rgb(255, 255, 255))

    }

}

