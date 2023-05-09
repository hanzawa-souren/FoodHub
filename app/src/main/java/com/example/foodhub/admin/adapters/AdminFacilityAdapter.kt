package com.example.foodhub.admin.adapters

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.admin.fragments.list.AdminNearMeFragmentDirections
import com.example.foodhub.database.ImageStorageManager
import com.example.foodhub.database.tables.Facility
import com.example.foodhub.databinding.AdminNearMeCardBinding

class AdminFacilityAdapter: RecyclerView.Adapter<AdminFacilityAdapter.FacilityViewHolder>() {

    private var facilityList = emptyList<Facility>()

    class FacilityViewHolder(val binding: AdminNearMeCardBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        return FacilityViewHolder(
            AdminNearMeCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return facilityList.size
    }

    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        val currentItem = facilityList[position]

        val imageFileName = currentItem.nImage.substring(currentItem.nImage.lastIndexOf("/")+1)
        Log.d("FacilityAdapter", imageFileName)
        val imageBitmap: Bitmap? = ImageStorageManager.getImageFromInternalStorage(holder.binding.nearMeImage.context, imageFileName)

        if (imageBitmap != null) {
            holder.binding.nearMeImage.setImageBitmap(imageBitmap)
        }

        holder.binding.nearMeName.text = currentItem.nName
        holder.binding.nearMeLocation.text = currentItem.nCity
        holder.binding.nearMeFacility.text = currentItem.nFacility
        holder.binding.nearMeDesc.text = currentItem.nDesc

        holder.binding.nearMeCardView.setOnClickListener { view: View ->
            val action = AdminNearMeFragmentDirections.actionAdminNearMeFragmentToAdminUpdateNearMeFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(facility: List<Facility>) {
        this.facilityList = facility
        notifyDataSetChanged()
    }
}