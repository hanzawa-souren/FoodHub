package com.example.foodhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.tables.Donation
import com.example.foodhub.user.models.DonateCardModel

class DonationHistoryAdapter(private var mList: List<Donation>) : RecyclerView.Adapter<DonationHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val transferAmount: TextView = itemView.findViewById(R.id.transferAmount)

        val transferMethod : TextView = itemView.findViewById(R.id.transferMethod)

        val transferDate : TextView = itemView.findViewById(R.id.donateDate)

        val card : CardView = itemView.findViewById(R.id.historyCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.donation_history_row, parent, false)
        return ViewHolder(view)
    }
    var onItemClick : ((Donation)-> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.transferAmount.text = "RM "+ItemsViewModel.dAmount.toString()
        holder.transferMethod.text = ItemsViewModel.dMethod
        val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
        var day : String
        var month : String
        var year : String

        day = ItemsViewModel.day.toString()
        month = months[ItemsViewModel.month]
        year = ItemsViewModel.year.toString()

        var date = "$day $month $year"
        holder.transferDate.text = date

        holder.card.setOnClickListener {
            onItemClick?.invoke(ItemsViewModel)
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }
    fun updateData(newList: List<Donation>) {
        mList = newList
        notifyDataSetChanged()
    }

}