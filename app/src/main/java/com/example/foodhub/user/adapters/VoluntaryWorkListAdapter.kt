package com.example.foodhub.user.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.tables.VoluntaryWork
import com.example.foodhub.databinding.PreviewCardVolunteerBinding

class VoluntaryWorkListAdapter: RecyclerView.Adapter<VoluntaryWorkListAdapter.VoluntaryWorkListViewHolder>() {

    private var vWorkList = emptyList<VoluntaryWork>()

    class VoluntaryWorkListViewHolder(val binding: PreviewCardVolunteerBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoluntaryWorkListViewHolder {
        return VoluntaryWorkListViewHolder(
            PreviewCardVolunteerBinding.inflate(
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

        holder.binding.volunteerCardImage.setImageURI(Uri.parse(currentItem.vImage))
        holder.binding.volunteerCardTitle.text = currentItem.vTitle
        holder.binding.volunteerCardLocation.text = currentItem.vCity

        holder.binding.volunteerCardContainer.setOnClickListener { view: View ->
            TODO("Redirect to volunteer details page.")
        }
    }

    fun setData(voluntaryWork: List<VoluntaryWork>) {
        this.vWorkList = voluntaryWork
        notifyDataSetChanged()
    }
}