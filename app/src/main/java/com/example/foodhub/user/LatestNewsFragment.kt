package com.example.foodhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.databinding.FragmentLatestNewsBinding
import com.example.foodhub.user.LatestNewsAdapter
import com.example.foodhub.user.LatestNewsModel

class LatestNewsFragment : Fragment() {

    private lateinit var bindingNews: FragmentLatestNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_latest_news, container, false)

        bindingNews = DataBindingUtil.inflate(inflater, R.layout.fragment_latest_news, container, false)
        return bindingNews.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
        val rview = bindingNews.recycleView
        rview.layoutManager = LinearLayoutManager(requireContext())
        rview.setHasFixedSize(true)
        rview.adapter = LatestNewsAdapter(setDataList())
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)
    }
    private fun setDataList() : ArrayList<LatestNewsModel>{
        var arrayList : ArrayList<LatestNewsModel> = ArrayList()
        arrayList.add(LatestNewsModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2003"))
        arrayList.add(LatestNewsModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2004"))
        arrayList.add(LatestNewsModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2005"))
        arrayList.add(LatestNewsModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2006"))
        arrayList.add(LatestNewsModel(R.drawable.arrow_forward_ios,"testing 1","5th July 2007"))

        return arrayList
    }
}