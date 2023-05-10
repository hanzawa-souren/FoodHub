package com.example.foodhub.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R
import com.example.foodhub.login.UserViewModel

import com.example.foodhub.user.models.ProfileModel
import com.example.foodhub.user.viewmodels.DonateViewModal

class ProfileAdapter(private val mList: List<ProfileModel>, var dateJoined : String,var donateAmount:Double) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.profile_row_title)
        val content: TextView = itemView.findViewById(R.id.profile_row_content)
        val image: ImageView = itemView.findViewById(R.id.profile_row_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]




        if(position == 0){
            holder.title.text = ItemsViewModel.title
            holder.content.text = dateJoined
            holder.image.setImageResource(ItemsViewModel.image)
        }else if(position == 2){
            holder.title.text = ItemsViewModel.title
            holder.content.text = donateAmount.toString()
            holder.image.setImageResource(ItemsViewModel.image)
        }

        else{
            holder.title.text = ItemsViewModel.title
            holder.content.text = ItemsViewModel.content
            holder.image.setImageResource(ItemsViewModel.image)
        }

    }



    override fun getItemCount(): Int {
        return mList.size
    }

}