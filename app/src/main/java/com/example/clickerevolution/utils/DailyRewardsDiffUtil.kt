package com.example.clickerevolution.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.clickerevolution.presentation.model.DailyReward

class DailyRewardDiffUtil(
    private val oldList: List<DailyReward>,
    private val newList: List<DailyReward>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].day == newList[newItemPosition].day
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.day == newItem.day &&
                oldItem.reward == newItem.reward
    }
}