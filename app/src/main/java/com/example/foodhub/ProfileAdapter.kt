package com.example.foodhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter(private val mList: List<ProfileModel>) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.profile_row_title)
        val content: TextView = itemView.findViewById(R.id.profile_row_content)
        val image: ImageView = itemView.findViewById(R.id.profile_row_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]


        holder.title.text = ItemsViewModel.title
        holder.content.text = ItemsViewModel.content
        holder.image.setImageResource(ItemsViewModel.image)
    }



    override fun getItemCount(): Int {
        return mList.size
    }

}