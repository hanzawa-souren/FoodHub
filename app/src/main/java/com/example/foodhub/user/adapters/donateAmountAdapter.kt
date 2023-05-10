package com.example.foodhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.user.models.donateAmountModel

class donateAmountAdapter(private val mList: List<donateAmountModel>) : RecyclerView.Adapter<donateAmountAdapter.ViewHolder>() {


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val amount  = itemView.findViewById<TextView>(R.id.amount_number)
        val card = itemView.findViewById<CardView>(R.id.amount_card)



    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): donateAmountAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.donate_amount, parent, false)
        return ViewHolder(view)
    }
    var onItemClick : ((donateAmountModel)-> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.amount.text = ItemsViewModel.amount


        holder.card.setOnClickListener {
            onItemClick?.invoke(ItemsViewModel)
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }

}