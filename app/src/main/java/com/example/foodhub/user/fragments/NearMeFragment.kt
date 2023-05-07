package com.example.foodhub.user.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentNearMeBinding
import com.example.foodhub.user.viewmodels.NearMeModel

class NearMeFragment : Fragment() {

    private lateinit var bindingNearMe: FragmentNearMeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_near_me, container, false)
        bindingNearMe = DataBindingUtil.inflate(inflater,
            R.layout.fragment_near_me, container, false)
        return bindingNearMe.root



    }
    private class CustomAdapter(context: Context ,var arraylist : ArrayList<NearMeModel>) : BaseAdapter(){
        private val myContext:Context

        init {
            myContext = context
        }
        override fun getCount(): Int {
            return arraylist.size
        }

        override fun getItem(p0: Int): Any {
            return arraylist[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(position: Int, p1: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(myContext)
            val rowMain = layoutInflater.inflate(R.layout.preview_card_near_me,viewGroup,false)
            var item : NearMeModel = arraylist[position]
            var images = rowMain.findViewById<ImageView>(R.id.near_me_card_image)
            var name = rowMain.findViewById<TextView>(R.id.near_me_card_title)
            var location = rowMain.findViewById<TextView>(R.id.near_me_card_location)
            var facility = rowMain.findViewById<TextView>(R.id.near_me_card_facility)

            images.setImageResource(item.image)
            name.text = item.title
            location.text = item.location
            facility.text = item.facility

            return rowMain
        }
    }
    private fun setDataList() : ArrayList<NearMeModel>{
        var arrayList : ArrayList<NearMeModel> = ArrayList()
        arrayList.add(NearMeModel(R.drawable.arrow_forward_ios,"testing 1","Farmer's market","Kuala Lumpur"))
        arrayList.add(NearMeModel(R.drawable.ic_salmon_about_us,"testing 2","Farmer's ","Johor Bahru"))
        arrayList.add(NearMeModel(R.drawable.deactivate,"testing 3","market","Selangor"))
        arrayList.add(NearMeModel(R.drawable.arrow_forward_ios,"testing 1","Farmer's market","Kuala Lumpur"))
        arrayList.add(NearMeModel(R.drawable.ic_salmon_about_us,"testing 2","Farmer's ","Johor Bahru"))
        arrayList.add(NearMeModel(R.drawable.deactivate,"testing 3","market","Selangor"))
        arrayList.add(NearMeModel(R.drawable.arrow_forward_ios,"testing 1","Farmer's market","Kuala Lumpur"))
        arrayList.add(NearMeModel(R.drawable.ic_salmon_about_us,"testing 2","Farmer's ","Johor Bahru"))
        arrayList.add(NearMeModel(R.drawable.deactivate,"testing 3","market","Selangor"))
        return arrayList
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.hide()
        val grid = bindingNearMe.gridView

        grid.adapter = CustomAdapter(requireContext(),setDataList())


    }


    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
    }



}