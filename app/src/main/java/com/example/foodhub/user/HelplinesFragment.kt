package com.example.foodhub.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentHelplinesBinding

class HelplinesFragment : Fragment() {

    private lateinit var bindingHelplines: FragmentHelplinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_helplines, container, false)

        bindingHelplines = DataBindingUtil.inflate(inflater,
            R.layout.fragment_helplines, container, false)
        return bindingHelplines.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}