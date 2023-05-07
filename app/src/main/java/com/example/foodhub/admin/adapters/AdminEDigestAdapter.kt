package com.example.foodhub.admin.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.admin.fragments.list.AdminBulletinFragmentDirections
import com.example.foodhub.database.tables.EDigest
import com.example.foodhub.databinding.AdminBulletinCardBinding

class AdminEDigestAdapter: RecyclerView.Adapter<AdminEDigestAdapter.EDigestViewHolder>() {

    private var digestList = emptyList<EDigest>()

    class EDigestViewHolder(val binding: AdminBulletinCardBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EDigestViewHolder {
        return EDigestViewHolder(
            AdminBulletinCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return digestList.size
    }

    override fun onBindViewHolder(holder: EDigestViewHolder, position: Int) {
        val currentItem = digestList[position]

        holder.binding.bulletinImage.setImageURI(Uri.parse(currentItem.eImage))
        holder.binding.bulletinTitle.text = currentItem.eTitle
        holder.binding.bulletinDate.text = currentItem.eDate
        holder.binding.bulletinContent.text = currentItem.eContent

        holder.binding.bulletinCardView.setOnClickListener { view: View ->
            val action = AdminBulletinFragmentDirections.actionAdminBulletinFragmentToAdminUpdateDigestFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    fun setData(eDigest: List<EDigest>) {
        this.digestList = eDigest
        notifyDataSetChanged()
    }

}