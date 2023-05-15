package com.example.foodhub

import android.content.ClipData.Item
import android.graphics.Color
import android.net.Uri
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
import com.example.foodhub.database.tables.UserVolunteeredWork
import com.example.foodhub.user.models.DonateCardModel

class EventsVolunteeredAdapter(private var mList: List<UserVolunteeredWork>) : RecyclerView.Adapter<EventsVolunteeredAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val mainImg:ImageView = itemView.findViewById(R.id.vw_main_img)
        val month: TextView = itemView.findViewById(R.id.vw_month)
        val country : TextView = itemView.findViewById(R.id.vw_location_country)
        val day : TextView = itemView.findViewById(R.id.vw_day)
        val statusImg:ImageView = itemView.findViewById(R.id.vw_status_img)

        val statusText : TextView = itemView.findViewById(R.id.vw_status_text)

        val card : CardView = itemView.findViewById(R.id.vw_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsVolunteeredAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.events_volunteered_profile_row, parent, false)
        return ViewHolder(view)
    }
    var onItemClick : ((UserVolunteeredWork)-> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
        // pending(peach) , complete(green) , Absent(red)
        val colors = arrayOf("#F1E8B8","#49BD36","#FF0000")
        val statusTexts = arrayOf("Pending","Completed","Absent")
        val statusImages = arrayOf(R.drawable.do_not_disturb_on,R.drawable.check_circle,R.drawable.cancel)
        holder.mainImg.setImageURI(Uri.parse(ItemsViewModel.vImage))
        holder.month.text = months[ItemsViewModel.month]
        holder.day.text = ItemsViewModel.day.toString()
        holder.country.text = ItemsViewModel.vCountry.toString()
       if(ItemsViewModel.status == "Pending"){
           holder.statusImg.setImageResource(statusImages[0])
           holder.statusText.text = "Pending"
           holder.statusText.setTextColor(Color.parseColor("#F1E8B8"))
       }else if(ItemsViewModel.status == "Completed"){
           holder.statusImg.setImageResource(statusImages[1])
           holder.statusText.text = "Completed"
           holder.statusText.setTextColor(Color.parseColor("#49BD36"))
       }else{
           holder.statusImg.setImageResource(statusImages[2])
           holder.statusText.text = "Absent"
           holder.statusText.setTextColor(Color.parseColor("#FF0000"))
       }



        holder.card.setOnClickListener {
            onItemClick?.invoke(ItemsViewModel)
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }
    fun updateData(newList: List<UserVolunteeredWork>) {
        mList = newList
        notifyDataSetChanged()
    }

}