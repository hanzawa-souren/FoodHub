package com.example.foodhub.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentEdigestBinding
import com.example.foodhub.user.adapters.EdigestAdapter
import com.example.foodhub.user.viewmodels.EdigestModel

class EdigestFragment : Fragment() {

    private lateinit var bindingEdigest: FragmentEdigestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_edigest, container, false)

        bindingEdigest = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edigest, container, false)
        return bindingEdigest.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        val rview = bindingEdigest.recycleView
        rview.layoutManager = LinearLayoutManager(requireContext())
        rview.setHasFixedSize(true)
        rview.adapter = EdigestAdapter(setDataList())
    }
    private fun setDataList() : ArrayList<EdigestModel>{
        var arrayList : ArrayList<EdigestModel> = ArrayList()
        arrayList.add(EdigestModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2003"))
        arrayList.add(EdigestModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2004"))
        arrayList.add(EdigestModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2005"))
        arrayList.add(EdigestModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2006"))
        arrayList.add(EdigestModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2007"))

        return arrayList
    }
}