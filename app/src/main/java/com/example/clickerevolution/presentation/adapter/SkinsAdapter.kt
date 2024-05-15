package com.example.clickerevolution.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.clickerevolution.databinding.ItemSkinBinding
import com.example.clickerevolution.presentation.model.Skin
import com.example.clickerevolution.utils.SkinsDiffUtil

class SkinsAdapter(
//    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<SkinsAdapter.ViewHolder>() {

    private var skinsList: List<Skin> = listOf()

    inner class ViewHolder(
        private val binding: ItemSkinBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Skin) {
            binding.itemRecyclerTitle.text = item.title
            binding.itemRecyclerImage.load(item.imageId)
            binding.itemRecyclerDescription.text = item.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSkinBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = skinsList[position]
        holder.bind(item)
//        holder.itemView.setOnClickListener {
//            itemClickListener.onItemClick(item.id)
//        }
    }

    override fun getItemCount(): Int {
        return skinsList.size
    }

    fun updateList(newDataList: List<Skin>) {
        val diffUtil = SkinsDiffUtil(skinsList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        skinsList = newDataList
        diffResult.dispatchUpdatesTo(this)
    }
}
