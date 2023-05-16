package com.example.foodhub.user.adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.LatestNews
import com.example.foodhub.databinding.ListCardBulletinBinding
import com.example.foodhub.user.fragments.list.LatestNewsFragmentDirections

class LatestNewsListAdapter: RecyclerView.Adapter<LatestNewsListAdapter.LatestNewsListViewHolder>() {

    private var newsList = emptyList<LatestNews>()

    class LatestNewsListViewHolder(val binding: ListCardBulletinBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestNewsListViewHolder {
        return LatestNewsListViewHolder(
            ListCardBulletinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: LatestNewsListViewHolder, position: Int) {
        val currentItem = newsList[position]

        //holder.binding.bulletinCardImage.setImageURI(Uri.parse(currentItem.lnImage))

        val imageFileName = currentItem.lnImage.substring(currentItem.lnImage.lastIndexOf("/")+1)
        val imageBitmap = ImageStorageManager.getImageFromInternalStorage(holder.binding.bulletinCardImage.context, imageFileName)

        if (imageBitmap != null) {
            holder.binding.bulletinCardImage.setImageBitmap(imageBitmap)
        }

        holder.binding.bulletinCardTitle.text = currentItem.lnTitle
        holder.binding.bulletinCardDate.text = currentItem.lnDate

        holder.binding.bulletinCardContainer.setOnClickListener { view: View ->
            val action = LatestNewsFragmentDirections.actionLatestNewsFragmentToLatestNewsDetailsFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(latestNews: List<LatestNews>) {
        this.newsList = latestNews
        notifyDataSetChanged()
    }
}