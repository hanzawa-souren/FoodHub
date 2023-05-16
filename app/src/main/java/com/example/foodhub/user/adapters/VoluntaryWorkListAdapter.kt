package com.example.foodhub.user.adapters

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.VoluntaryWork
import com.example.foodhub.databinding.ListCardVolunteerBinding
import com.example.foodhub.user.fragments.list.VolunteerFragmentDirections

class VoluntaryWorkListAdapter: RecyclerView.Adapter<VoluntaryWorkListAdapter.VoluntaryWorkListViewHolder>() {

    private var vWorkList = emptyList<VoluntaryWork>()

    class VoluntaryWorkListViewHolder(val binding: ListCardVolunteerBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoluntaryWorkListViewHolder {
        return VoluntaryWorkListViewHolder(
            ListCardVolunteerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return vWorkList.size
    }

    override fun onBindViewHolder(holder: VoluntaryWorkListViewHolder, position: Int) {
        val currentItem = vWorkList[position]

        //holder.binding.volunteerCardImage.setImageURI(Uri.parse(currentItem.vImage))

        val imageFileName = currentItem.vImage.substring(currentItem.vImage.lastIndexOf("/")+1)
        val imageBitmap: Bitmap? = ImageStorageManager.getImageFromInternalStorage(holder.binding.volunteerCardImage.context, imageFileName)

        if (imageBitmap != null) {
            holder.binding.volunteerCardImage.setImageBitmap(imageBitmap)
        }

        holder.binding.volunteerCardTitle.text = currentItem.vTitle
        holder.binding.volunteerCardLocation.text = currentItem.vCity

        holder.binding.volunteerCardContainer.setOnClickListener { view: View ->
            val action = VolunteerFragmentDirections.actionVolunteerFragmentToVolunteerDetailsFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(voluntaryWork: List<VoluntaryWork>) {
        this.vWorkList = voluntaryWork
        notifyDataSetChanged()
    }
}