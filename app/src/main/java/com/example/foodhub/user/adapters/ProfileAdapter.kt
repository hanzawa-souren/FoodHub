package com.example.foodhub.user.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R
import com.example.foodhub.database.tables.Donation

import com.example.foodhub.user.models.ProfileModel

class ProfileAdapter(private val mList: List<ProfileModel>, var dateJoined : String,var donateAmount:Double, var volunteered : Int) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.donateAmountTitle)
        val content: TextView = itemView.findViewById(R.id.transferMethod)
        val image: ImageView = itemView.findViewById(R.id.profile_row_img)
        val card : CardView = itemView.findViewById(R.id.profile_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_row, parent, false)
        return ViewHolder(view)
    }
    var onItemClick : ((ProfileModel)-> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]




        if(position == 0){
            holder.title.text = ItemsViewModel.title
            holder.content.text = dateJoined
            holder.image.setImageResource(ItemsViewModel.image)
        }else if(position == 2){
            holder.title.text = ItemsViewModel.title
            holder.content.text = "RM "+donateAmount.toString()
            holder.image.setImageResource(ItemsViewModel.image)
        }

        else{
            holder.title.text = ItemsViewModel.title
            holder.content.text = volunteered.toString()
            holder.image.setImageResource(ItemsViewModel.image)
        }
        holder.card.setOnClickListener {
            onItemClick?.invoke(ItemsViewModel)
        }
    }



    override fun getItemCount(): Int {
        return mList.size
    }

}