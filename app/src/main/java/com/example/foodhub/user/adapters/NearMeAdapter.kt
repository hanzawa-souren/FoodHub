package com.example.foodhub.user.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R
import com.example.foodhub.user.models.NearMeModel

class NearMeAdapter(private val mList: List<NearMeModel>) : RecyclerView.Adapter<NearMeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.near_me_card_image)
        val title: TextView = itemView.findViewById(R.id.near_me_card_title)
        val facility: TextView = itemView.findViewById(R.id.near_me_card_facility)
        val location: TextView = itemView.findViewById(R.id.near_me_card_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_card_near_me, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val ItemsViewModel = mList[position]
//
//        holder.image.setImageResource(ItemsViewModel.image)
//        holder.title.text = ItemsViewModel.text1
//        holder.facility.text = ItemsViewModel.text2
//        holder.location.text = ItemsViewModel.text3
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}