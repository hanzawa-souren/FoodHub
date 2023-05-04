package com.example.foodhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
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

        bindingHelplines = DataBindingUtil.inflate(inflater, R.layout.fragment_helplines, container, false)
        return bindingHelplines.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        val rview = bindingHelplines.recycleView
        rview.layoutManager = LinearLayoutManager(requireContext())
        rview.setHasFixedSize(true)
        rview.adapter = HelpLineAdapter(setDataList())
    }
    private fun setDataList() : ArrayList<HelpLineModel>{
        var arrayList : ArrayList<HelpLineModel> = ArrayList()
        arrayList.add(HelpLineModel("MIASA Crisis Helpline","MIASA Crisis Helpline provides 24/7, free and confidential support by phone. We are here for everyone in Malaysia who may be...","Mon - Sun, 00:00 - 23:59"))
        arrayList.add(HelpLineModel("MIASA Crisis Helpline","MIASA Crisis Helpline provides 24/7, free and confidential support by phone. We are here for everyone in Malaysia who may be...","Mon - Sun, 00:00 - 23:59"))
        arrayList.add(HelpLineModel("MIASA Crisis Helpline","MIASA Crisis Helpline provides 24/7, free and confidential support by phone. We are here for everyone in Malaysia who may be...","Mon - Sun, 00:00 - 23:59"))
        arrayList.add(HelpLineModel("MIASA Crisis Helpline","MIASA Crisis Helpline provides 24/7, free and confidential support by phone. We are here for everyone in Malaysia who may be...","Mon - Sun, 00:00 - 23:59"))
        arrayList.add(HelpLineModel("MIASA Crisis Helpline","MIASA Crisis Helpline provides 24/7, free and confidential support by phone. We are here for everyone in Malaysia who may be...","Mon - Sun, 00:00 - 23:59"))
        arrayList.add(HelpLineModel("MIASA Crisis Helpline","MIASA Crisis Helpline provides 24/7, free and confidential support by phone. We are here for everyone in Malaysia who may be...","Mon - Sun, 00:00 - 23:59"))


        return arrayList
    }
}