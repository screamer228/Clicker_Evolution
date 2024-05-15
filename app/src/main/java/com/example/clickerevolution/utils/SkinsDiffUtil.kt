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
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {

            oldList[oldItemPosition].title != newList[newItemPosition].title -> {
                false
            }

            oldList[oldItemPosition].imageId != newList[newItemPosition].imageId -> {
                false
            }

            oldList[oldItemPosition].price != newList[newItemPosition].price -> {
                false
            }

            else -> true
        }
    }
}