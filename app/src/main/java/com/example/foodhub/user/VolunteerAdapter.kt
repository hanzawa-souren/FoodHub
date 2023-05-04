package com.example.foodhub.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R

class VolunteerAdapter(private val mList: List<VolunteerModel>) : RecyclerView.Adapter<VolunteerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        //Call TextView and Image
        val image: ImageView = itemView.findViewById(R.id.volunteer_card_image)
        val title: TextView = itemView.findViewById(R.id.volunteer_card_title)
        val location: TextView = itemView.findViewById(R.id.volunteer_card_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Create view and send it into ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_card_volunteer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        //Sets image to ImageView from itemHolder class
        holder.image.setImageResource(ItemsViewModel.image)
        //Sets text to TextView from itemHolder class
        holder.title.text = ItemsViewModel.text1
        holder.location.text = ItemsViewModel.text2
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}