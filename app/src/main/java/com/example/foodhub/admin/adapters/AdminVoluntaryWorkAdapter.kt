package com.example.foodhub.admin.adapters

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.admin.fragments.list.AdminVolunteerFragmentDirections
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.VoluntaryWork
import com.example.foodhub.databinding.AdminVolunteerCardBinding

class AdminVoluntaryWorkAdapter: RecyclerView.Adapter<AdminVoluntaryWorkAdapter.VoluntaryWorkViewHolder>() {

    private var vWorkList = emptyList<VoluntaryWork>()

    class VoluntaryWorkViewHolder(val binding: AdminVolunteerCardBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoluntaryWorkViewHolder {
        return VoluntaryWorkViewHolder(
            AdminVolunteerCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return vWorkList.size
    }

    override fun onBindViewHolder(holder: VoluntaryWorkViewHolder, position: Int) {
        val currentItem = vWorkList[position]

        val imageFileName = currentItem.vImage.substring(currentItem.vImage.lastIndexOf("/")+1)
        Log.d("VoluntaryWorkAdapter", imageFileName)
        val imageBitmap: Bitmap? = ImageStorageManager.getImageFromInternalStorage(holder.binding.volunteerImage.context, imageFileName)

        if (imageBitmap != null) {
            holder.binding.volunteerImage.setImageBitmap(imageBitmap)
        }

        holder.binding.volunteerTitle.text = currentItem.vTitle
        holder.binding.volunteerLocation.text = currentItem.vCity
        holder.binding.volunteerDesc.text = currentItem.vDesc

        holder.binding.volunteerCardView.setOnClickListener { view: View ->
            val action = AdminVolunteerFragmentDirections.actionAdminVolunteerFragmentToAdminUpdateVolunteerFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(voluntaryWork: List<VoluntaryWork>) {
        this.vWorkList = voluntaryWork
        notifyDataSetChanged()
    }

}