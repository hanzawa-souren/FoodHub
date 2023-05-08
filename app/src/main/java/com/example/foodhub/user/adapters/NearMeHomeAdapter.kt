package com.example.foodhub.user.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.tables.Facility
import com.example.foodhub.databinding.PreviewCardNearMeBinding

class NearMeHomeAdapter: RecyclerView.Adapter<NearMeHomeAdapter.NearMePreviewViewHolder>() {

    private var facilityList = emptyList<Facility>()
    private val limit = 4

    class NearMePreviewViewHolder(val binding: PreviewCardNearMeBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearMePreviewViewHolder {
        return NearMePreviewViewHolder(
            PreviewCardNearMeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        if (facilityList.size > limit) {
            return limit
        }
        else {
            return facilityList.size
        }
    }

    override fun onBindViewHolder(holder: NearMePreviewViewHolder, position: Int) {
        val currentItem = facilityList[position]

        holder.binding.nearMeCardImage.setImageURI(Uri.parse(currentItem.nImage))
        holder.binding.nearMeCardTitle.text = currentItem.nName
        holder.binding.nearMeCardFacility.text = currentItem.nFacility
        holder.binding.nearMeCardLocation.text = currentItem.nCity

        holder.binding.nearMeCardContainer.setOnClickListener { view: View ->
            TODO("Redirect to near me details page.")
        }
    }

    fun setData(facility: List<Facility>) {
        this.facilityList = facility
        notifyDataSetChanged()
    }
}