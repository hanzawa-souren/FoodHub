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
import com.example.foodhub.databinding.FragmentVolunteerBinding
import com.example.foodhub.user.models.VolunteerModel

class VolunteerFragment : Fragment() {

    private lateinit var bindingVolunteer: FragmentVolunteerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_volunteer, container, false)

        bindingVolunteer = DataBindingUtil.inflate(inflater,
            R.layout.fragment_volunteer, container, false)
        return bindingVolunteer.root
    }
    private class CustomAdapterVol(context: Context, var arraylist : ArrayList<VolunteerModel>) : BaseAdapter(){
        private val myContext: Context

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
            val rowMain = layoutInflater.inflate(R.layout.preview_card_volunteer,viewGroup,false)
            var item : VolunteerModel = arraylist[position]
            var images = rowMain.findViewById<ImageView>(R.id.volunteer_card_image)
            var name = rowMain.findViewById<TextView>(R.id.volunteer_card_title)
            var location = rowMain.findViewById<TextView>(R.id.volunteer_card_location)


            images.setImageResource(item.image)
            name.text = item.title
            location.text = item.location


            return rowMain
        }
    }

    private fun setDataList() : ArrayList<VolunteerModel>{
        var arrayList : ArrayList<VolunteerModel> = ArrayList()
        arrayList.add(VolunteerModel(R.drawable.arrow_forward_ios,"testing 1","Kuala Lumpur"))
        arrayList.add(VolunteerModel(R.drawable.ic_salmon_about_us,"testing 2","Johor Bahru"))
        arrayList.add(VolunteerModel(R.drawable.deactivate,"testing 3","Selangor"))
        arrayList.add(VolunteerModel(R.drawable.arrow_forward_ios,"testing 1","Kuala Lumpur"))
        arrayList.add(VolunteerModel(R.drawable.ic_salmon_about_us,"testing 2","Johor Bahru"))
        arrayList.add(VolunteerModel(R.drawable.deactivate,"testing 3","Selangor"))
        arrayList.add(VolunteerModel(R.drawable.arrow_forward_ios,"testing 1","Kuala Lumpur"))
        arrayList.add(VolunteerModel(R.drawable.ic_salmon_about_us,"testing 2","Johor Bahru"))
        arrayList.add(VolunteerModel(R.drawable.deactivate,"testing 3","Selangor"))
        arrayList.add(VolunteerModel(R.drawable.arrow_forward_ios,"testing 1","Kuala Lumpur"))
        arrayList.add(VolunteerModel(R.drawable.ic_salmon_about_us,"testing 2","Johor Bahru"))
        arrayList.add(VolunteerModel(R.drawable.deactivate,"testing 3","Selangor"))
        return arrayList
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)

        val grid = bindingVolunteer.gridView
        grid.adapter = CustomAdapterVol(requireContext(), setDataList())
        
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
    }

}
