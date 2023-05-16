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
import com.example.foodhub.database.tables.Facility
import com.example.foodhub.databinding.ListCardNearMeBinding
import com.example.foodhub.user.fragments.list.NearMeFragmentDirections

class NearMeListAdapter: RecyclerView.Adapter<NearMeListAdapter.NearMeListViewHolder>() {

    private var facilityList = emptyList<Facility>()

    class NearMeListViewHolder(val binding: ListCardNearMeBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearMeListViewHolder {
        return NearMeListViewHolder(
            ListCardNearMeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return facilityList.size
    }

    override fun onBindViewHolder(holder: NearMeListViewHolder, position: Int) {
        val currentItem = facilityList[position]

        //holder.binding.nearMeCardImage.setImageURI(Uri.parse(currentItem.nImage))

        val imageFileName = currentItem.nImage.substring(currentItem.nImage.lastIndexOf("/")+1)
        val imageBitmap: Bitmap? = ImageStorageManager.getImageFromInternalStorage(holder.binding.nearMeCardImage.context, imageFileName)

        if (imageBitmap != null) {
            holder.binding.nearMeCardImage.setImageBitmap(imageBitmap)
        }

        holder.binding.nearMeCardTitle.text = currentItem.nName
        holder.binding.nearMeCardFacility.text = currentItem.nFacility
        holder.binding.nearMeCardLocation.text = currentItem.nCity

        holder.binding.nearMeCardContainer.setOnClickListener { view: View ->
            val action = NearMeFragmentDirections.actionNearMeFragmentToNearMeDetailFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(facility: List<Facility>) {
        this.facilityList = facility
        notifyDataSetChanged()
    }
}