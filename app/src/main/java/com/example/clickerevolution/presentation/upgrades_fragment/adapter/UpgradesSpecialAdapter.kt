package com.example.clickerevolution.presentation.upgrades_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.R
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.databinding.ItemUpgradeBinding
import com.example.clickerevolution.databinding.ItemUpgradeSpecialBinding
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.utils.StringUtil.addCommaEveryThreeDigits
import com.example.clickerevolution.utils.UpgradesDiffUtil

class UpgradesSpecialAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<UpgradesSpecialAdapter.ViewHolder>() {

    private var upgradesList: List<Upgrade> = emptyList()

    inner class ViewHolder(
        private val binding: ItemUpgradeSpecialBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Upgrade) {
            binding.upgradeSpecialTitle.text = item.title
//            binding.upgradeImage.setImageResource(item.imageId)
            binding.upgradeSpecialLevel.text = "Уровень: ${item.level}"

            binding.upgradeSpecialCardView.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUpgradeSpecialBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = upgradesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return upgradesList.size
    }

    fun updateList(newDataList: List<Upgrade>) {
        val diffUtil = UpgradesDiffUtil(upgradesList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        upgradesList = newDataList
        diffResult.dispatchUpdatesTo(this)
    }
}