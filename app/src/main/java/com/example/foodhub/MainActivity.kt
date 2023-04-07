package com.example.foodhub

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.foodhub.databinding.ActivityMainBinding

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

