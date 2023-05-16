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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.tables.Donation
import com.example.foodhub.database.tables.UserVolunteeredWork
import com.example.foodhub.login.User
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.models.DonateCardModel

class AdminCheckVolunteerAdapter(private var mList: List<UserVolunteeredWork>,private var uList: List<User>) : RecyclerView.Adapter< AdminCheckVolunteerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.cv_name)
        val phone: TextView = itemView.findViewById(R.id.cv_phone)

        val statusImg:ImageView = itemView.findViewById(R.id.cv_status_img)

        val statusText : TextView = itemView.findViewById(R.id.cv_status_text)

        val card : CardView = itemView.findViewById(R.id.cv_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  AdminCheckVolunteerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.check_volunteer_row, parent, false)
        return ViewHolder(view)
    }
    var onItemClick : ((UserVolunteeredWork)-> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.name.text = ItemsViewModel.uId
        var phoneNumber = ""
        for(i in uList){
            if(ItemsViewModel.uId == i.loginID){
                phoneNumber = i.phNum
            }
        }
        holder.phone.text = phoneNumber

        // pending(peach) , complete(green) , Absent(red)
        val colors = arrayOf("#F1E8B8","#49BD36","#FF0000")
        val statusTexts = arrayOf("Pending","Completed","Absent")
        val statusImages = arrayOf(R.drawable.do_not_disturb_on,R.drawable.check_circle,R.drawable.cancel)

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
    fun updateData2(newList: List<User>) {
        uList = newList
        notifyDataSetChanged()
    }

}