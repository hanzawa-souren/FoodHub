package com.example.foodhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class DonateCardAdapter(private val mList: List<DonateCardModel>) : RecyclerView.Adapter<DonateCardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.paymentImg)
        val number: TextView = itemView.findViewById(R.id.card_number)
        val card : CardView = itemView.findViewById(R.id.payment_card_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonateCardAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.donate_quick, parent, false)
        return ViewHolder(view)
    }
    var onItemClick : ((DonateCardModel)-> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.image.setImageResource(ItemsViewModel.image)
        holder.number.text = ItemsViewModel.number
        holder.card.alpha = ItemsViewModel.status


        holder.card.setOnClickListener {
            onItemClick?.invoke(ItemsViewModel)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}