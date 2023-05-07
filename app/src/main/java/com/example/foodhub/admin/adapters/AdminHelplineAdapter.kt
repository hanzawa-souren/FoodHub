package com.example.foodhub.admin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.admin.fragments.list.AdminHelplinesFragmentDirections
import com.example.foodhub.database.tables.Helpline
import com.example.foodhub.databinding.AdminHelplineCardBinding

class AdminHelplineAdapter: RecyclerView.Adapter<AdminHelplineAdapter.HelplineViewHolder>() {

    private var helplineList = emptyList<Helpline>()

    class HelplineViewHolder(val binding: AdminHelplineCardBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelplineViewHolder {
        return HelplineViewHolder(
            AdminHelplineCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return helplineList.size
    }

    override fun onBindViewHolder(holder: HelplineViewHolder, position: Int) {
        val currentItem = helplineList[position]

        holder.binding.helplineTitle.text = currentItem.hTitle
        holder.binding.helplinePhone.text = currentItem.hPhone
        holder.binding.helplineDesc.text = currentItem.hDesc

        holder.binding.helplineCardView.setOnClickListener { view: View ->
            val action = AdminHelplinesFragmentDirections.actionAdminHelplinesFragmentToAdminUpdateHelplineFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(helpline: List<Helpline>) {
        this.helplineList = helpline
        notifyDataSetChanged()
    }

}