package com.example.foodhub.user.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.tables.LatestNews
import com.example.foodhub.databinding.PreviewCardBulletinBinding
import com.example.foodhub.user.fragments.HomeFragmentDirections
import com.example.foodhub.user.fragments.list.LatestNewsFragmentDirections

class LatestNewsHomeAdapter: RecyclerView.Adapter<LatestNewsHomeAdapter.LatestNewsPreviewViewHolder>() {

    private var newsList = emptyList<LatestNews>()
    private val limit = 4

    class LatestNewsPreviewViewHolder(val binding: PreviewCardBulletinBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestNewsPreviewViewHolder {
        return LatestNewsPreviewViewHolder(
            PreviewCardBulletinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        if (newsList.size > limit) {
            return limit
        }
        else {
            return newsList.size
        }
    }

    override fun onBindViewHolder(holder: LatestNewsPreviewViewHolder, position: Int) {
        val currentItem = newsList[position]

        holder.binding.bulletinCardImage.setImageURI(Uri.parse(currentItem.lnImage))
        holder.binding.bulletinCardTitle.text = currentItem.lnTitle
        holder.binding.bulletinCardDate.text = currentItem.lnDate

        holder.binding.bulletinCardContainer.setOnClickListener { view: View ->
            val action = HomeFragmentDirections.actionHomeFragmentToLatestNewsDetailsFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(latestNews: List<LatestNews>) {
        this.newsList = latestNews
        notifyDataSetChanged()
    }
}