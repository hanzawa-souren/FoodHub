package com.example.foodhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EdigestAdapter(private val mList: List<EdigestModel>) : RecyclerView.Adapter<EdigestAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.bulletin_card_image)
        val title: TextView = itemView.findViewById(R.id.bulletin_card_title)
        val date: TextView = itemView.findViewById(R.id.bulletin_card_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EdigestAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_card_bulletin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.image.setImageResource(ItemsViewModel.image)
        holder.title.text = ItemsViewModel.title
        holder.date.text = ItemsViewModel.date
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}