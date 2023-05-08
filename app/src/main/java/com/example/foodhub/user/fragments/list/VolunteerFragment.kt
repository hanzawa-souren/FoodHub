package com.example.foodhub.user.fragments.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
import com.example.foodhub.databinding.FragmentVolunteerBinding
import com.example.foodhub.user.adapters.VoluntaryWorkListAdapter

class VolunteerFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var bindingVolunteer: FragmentVolunteerBinding
    private lateinit var voluntaryWorkViewModel: VoluntaryWorkViewModel
    private lateinit var adapter: VoluntaryWorkListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingVolunteer = DataBindingUtil.inflate(inflater,
            R.layout.fragment_volunteer, container, false)

        voluntaryWorkViewModel = ViewModelProvider(this).get(VoluntaryWorkViewModel::class.java)

        return bindingVolunteer.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val grid = bindingVolunteer.gridView
        grid.adapter = CustomAdapterVol(requireContext(), setDataList())*/

        adapter = VoluntaryWorkListAdapter()
        bindingVolunteer.volunteerList.adapter = adapter
        bindingVolunteer.volunteerList.layoutManager = GridLayoutManager(requireContext(), 2)
        /*bindingVolunteer.volunteerList.layoutManager = LinearLayoutManager(requireContext())*/

        voluntaryWorkViewModel.getAllWork.observe(viewLifecycleOwner, Observer { voluntaryWork ->
            adapter.setData(voluntaryWork)
        })

        bindingVolunteer.userVolunteerSearchview.isSubmitButtonEnabled = true
        bindingVolunteer.userVolunteerSearchview.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchWorks(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchWorks(newText)
        }
        return true
    }

    private fun searchWorks(searchQuery: String) {
        var searchQuery = searchQuery
        searchQuery = "%$searchQuery%"

        voluntaryWorkViewModel.searchWorks(searchQuery = searchQuery).observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setData(list)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Volunteer"
    }

    /*private class CustomAdapterVol(context: Context, var arraylist : ArrayList<VolunteerModel>) : BaseAdapter(){
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
    }*/
}
