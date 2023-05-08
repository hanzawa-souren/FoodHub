package com.example.foodhub.user.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.admin.fragments.list.AdminHelplinesFragmentDirections
import com.example.foodhub.database.tables.Helpline
import com.example.foodhub.databinding.PreviewCardHelplinesBinding

class HelplineListAdapter: RecyclerView.Adapter<HelplineListAdapter.HelplineListViewHolder>() {

    private var helplineList = emptyList<Helpline>()

    class HelplineListViewHolder(val binding: PreviewCardHelplinesBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelplineListViewHolder {
        return HelplineListViewHolder(
            PreviewCardHelplinesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return helplineList.size
    }

    override fun onBindViewHolder(holder: HelplineListViewHolder, position: Int) {
        val currentItem = helplineList[position]

        holder.binding.helplineCardTitle.text = currentItem.hTitle
        holder.binding.helplineCardDescription.text = currentItem.hDesc

        holder.binding.helplineCardContainer.setOnClickListener { view: View ->
            TODO("Redirect to helpline details page.")
        }
    }

    fun setData(helpline: List<Helpline>) {
        this.helplineList = helpline
        notifyDataSetChanged()
    }
}