package com.example.foodhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.user.viewmodels.HelpLineModel

class HelpLineAdapter(private val mList: List<HelpLineModel>) : RecyclerView.Adapter<HelpLineAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.helpline_card_title)
        val description: TextView = itemView.findViewById(R.id.helpline_card_description)
        val schedule: TextView = itemView.findViewById(R.id.helpline_card_schedule)
        val card : CardView = itemView.findViewById(R.id.helpline_card_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpLineAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_card_helplines, parent, false)
        return ViewHolder(view)
    }
    var onItemClick : ((HelpLineModel)-> Unit)? = null
    override fun onBindViewHolder(holder: HelpLineAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]


        holder.title.text = ItemsViewModel.title
        holder.description.text = ItemsViewModel.description
        holder.schedule.text = ItemsViewModel.schedule

        holder.card.setOnClickListener {
            onItemClick?.invoke(ItemsViewModel)
        }
    }



    override fun getItemCount(): Int {
        return mList.size
    }

}