package com.example.foodhub.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentFirstTimeScr1Binding
import com.example.foodhub.databinding.FragmentLoginBinding

class FirstTimeScr1 : Fragment() {

    private var _binding: FragmentFirstTimeScr1Binding? = null
    private val binding get() = _binding!!
    private lateinit var pref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstTimeScr1Binding.inflate(inflater, container, false)


        //Getting the sharedPreferences
        val appContext = requireContext().applicationContext
        var pref = appContext.getSharedPreferences("firstTime", Context.MODE_PRIVATE)
        val prefEditor = pref.edit()
        if (pref.getBoolean("firstTime", true)) { // use default of true
            prefEditor.putBoolean("firstTime", false)
            prefEditor.apply()
        } else {
            findNavController().navigate(R.id.action_firstTimeScr1_to_preLogin)
        }

        binding.firstNextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_firstTimeScr1_to_firstTimeScr2)
        }


        return binding.root
    }



}