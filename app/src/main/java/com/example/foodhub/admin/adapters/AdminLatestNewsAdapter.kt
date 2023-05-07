package com.example.foodhub.admin.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.admin.fragments.list.AdminBulletinFragmentDirections
import com.example.foodhub.database.tables.LatestNews
import com.example.foodhub.databinding.AdminBulletinCardBinding

class AdminLatestNewsAdapter: RecyclerView.Adapter<AdminLatestNewsAdapter.LatestNewsViewHolder>() {

    private var newsList = emptyList<LatestNews>()

    class LatestNewsViewHolder(val binding: AdminBulletinCardBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestNewsViewHolder {
        return LatestNewsViewHolder(
            AdminBulletinCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: LatestNewsViewHolder, position: Int) {
        val currentItem = newsList[position]

        holder.binding.bulletinImage.setImageURI(Uri.parse(currentItem.lnImage))
        holder.binding.bulletinTitle.text = currentItem.lnTitle
        holder.binding.bulletinDate.text = currentItem.lnDate
        holder.binding.bulletinContent.text = currentItem.lnContent

        holder.binding.bulletinCardView.setOnClickListener { view: View ->
            val action = AdminBulletinFragmentDirections.actionAdminBulletinFragmentToAdminUpdateNewsFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(latestNews: List<LatestNews>) {
        this.newsList = latestNews
        notifyDataSetChanged()
    }

}