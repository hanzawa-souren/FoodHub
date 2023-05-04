package com.example.foodhub.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R

class LatestNewsAdapter(private val mList: List<LatestNewsModel>) : RecyclerView.Adapter<LatestNewsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.bulletin_card_image)
        val title: TextView = itemView.findViewById(R.id.bulletin_card_title)
        val date: TextView = itemView.findViewById(R.id.bulletin_card_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_card_bulletin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.image.setImageResource(ItemsViewModel.image)
        holder.title.text = ItemsViewModel.text1
        holder.date.text = ItemsViewModel.text2
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}