package com.example.clickerevolution.presentation.shop_fragment.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.databinding.ItemSkinBinding
import com.example.clickerevolution.presentation.model.Skin
import com.example.clickerevolution.utils.SkinsDiffUtil

class SkinsAdapter(
//    var gold: Int,
    private val onAction: (Skin, Action) -> Unit
//    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<SkinsAdapter.ViewHolder>() {

    private var skinsList: List<Skin> = emptyList()

    inner class ViewHolder(
        private val binding: ItemSkinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Skin) {
            binding.skinTitle.text = item.title
            binding.skinImage.setImageResource(item.imageId)
            binding.skinPrice.text = item.price.toString()

            when {
                !item.isPurchased -> {
                    binding.skinActionText.text = "Купить"
                    binding.skinButtonAction.setOnClickListener {
                        onAction(item, Action.PURCHASE)
                    }
                }

                item.isPurchased && !item.isEquipped -> {
                    binding.skinActionLinear.visibility = GONE
                    binding.skinActionText.text = "Одеть"
                    binding.skinButtonAction.setOnClickListener {
                        onAction(item, Action.EQUIP)
                    }
                }

                item.isPurchased && item.isEquipped -> {
                    binding.skinActionLinear.visibility = GONE
                    binding.skinActionText.text = "Снять"
                    binding.skinButtonAction.setOnClickListener {
                        onAction(item, Action.UNEQUIP)
                    }
                }
            }
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

    enum class Action {
        PURCHASE, EQUIP, UNEQUIP
    }
}