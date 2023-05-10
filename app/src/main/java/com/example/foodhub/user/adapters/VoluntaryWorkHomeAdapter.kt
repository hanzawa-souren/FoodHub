package com.example.foodhub.user.viewmodels

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.tables.VoluntaryWork
import com.example.foodhub.databinding.PreviewCardVolunteerBinding
import com.example.foodhub.user.fragments.HomeFragmentDirections

class VoluntaryWorkHomeAdapter: RecyclerView.Adapter<VoluntaryWorkHomeAdapter.VoluntaryWorkPreviewViewHolder>() {

    private var vWorkList = emptyList<VoluntaryWork>()
    private val limit = 4

    class VoluntaryWorkPreviewViewHolder(val binding: PreviewCardVolunteerBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoluntaryWorkPreviewViewHolder {
        return VoluntaryWorkPreviewViewHolder(
            PreviewCardVolunteerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        if (vWorkList.size > limit) {
            return limit
        }
        else {
            return vWorkList.size
        }
    }

    override fun onBindViewHolder(holder: VoluntaryWorkPreviewViewHolder, position: Int) {
        val currentItem = vWorkList[position]

        holder.binding.volunteerCardImage.setImageURI(Uri.parse(currentItem.vImage))
        holder.binding.volunteerCardTitle.text = currentItem.vTitle
        holder.binding.volunteerCardLocation.text = currentItem.vCity

        holder.binding.volunteerCardContainer.setOnClickListener { view: View ->
            val action = HomeFragmentDirections.actionHomeFragmentToVolunteerDetailsFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(voluntaryWork: List<VoluntaryWork>) {
        this.vWorkList = voluntaryWork
        notifyDataSetChanged()
    }

}