package com.example.clickerevolution.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.clickerevolution.presentation.model.Skin

class SkinsDiffUtil(
    private val oldList: List<Skin>,
    private val newList: List<Skin>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.title == newItem.title &&
                oldItem.imageId == newItem.imageId &&
                oldItem.price == newItem.price &&
                oldItem.isPurchased == newItem.isPurchased &&
                oldItem.isEquipped == newItem.isEquipped
    }
}