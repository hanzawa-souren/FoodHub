package com.example.foodhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class VolunteerAdapter(private val mList: List<VolunteerModel>) : RecyclerView.Adapter<VolunteerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        //Call TextView and Image
        val image: ImageView = itemView.findViewById(R.id.volunteer_card_image)
        val title: TextView = itemView.findViewById(R.id.volunteer_card_title)
        val location: TextView = itemView.findViewById(R.id.volunteer_card_location)
        val card : CardView = itemView.findViewById(R.id.volunteer_card_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerAdapter.ViewHolder {
        //Create view and send it into ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_card_volunteer, parent, false)
        return ViewHolder(view)
    }
    var onItemClick : ((VolunteerModel)-> Unit)? = null
    override fun onBindViewHolder(holder: VolunteerAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        //Sets image to ImageView from itemHolder class
        holder.image.setImageResource(ItemsViewModel.image)
        holder.title.text = ItemsViewModel.title
        holder.location.text = ItemsViewModel.location
        holder.card.setOnClickListener {
            onItemClick?.invoke(ItemsViewModel)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}