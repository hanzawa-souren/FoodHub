package com.example.foodhub.user.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.database.tables.EDigest
import com.example.foodhub.databinding.PreviewCardBulletinBinding

class EDigestListAdapter: RecyclerView.Adapter<EDigestListAdapter.EDigestListViewHolder>() {

    private var digestList = emptyList<EDigest>()

    class EDigestListViewHolder(val binding: PreviewCardBulletinBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EDigestListViewHolder {
        return EDigestListViewHolder(
            PreviewCardBulletinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return digestList.size
    }

    override fun onBindViewHolder(holder: EDigestListViewHolder, position: Int) {
        val currentItem = digestList[position]

        holder.binding.bulletinCardImage.setImageURI(Uri.parse(currentItem.eImage))
        holder.binding.bulletinCardTitle.text = currentItem.eTitle
        holder.binding.bulletinCardDate.text = currentItem.eDate

        holder.binding.bulletinCardContainer.setOnClickListener { view: View ->
            TODO("Redirect to e-digest details page.")
        }
    }

    fun setData(digest: List<EDigest>) {
        this.digestList = digest
        notifyDataSetChanged()
    }
}