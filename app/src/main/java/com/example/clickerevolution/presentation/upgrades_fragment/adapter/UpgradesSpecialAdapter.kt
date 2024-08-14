package com.example.clickerevolution.presentation.upgrades_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.databinding.ItemUpgradeSpecialBinding
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.utils.AnimationUtils.setTouchAnimation
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
            binding.upgradeSpecialImage.setImageResource(item.imageId)
            binding.upgradeSpecialLevel.text = when (item.level) {
                MAX_LEVEL -> "Макс. уровень"
                else -> item.level.toString()
            }

            binding.upgradeSpecialCardView.apply {
                setTouchAnimation(0.95f)
                setOnClickListener {
                    onClick(item.id)
                }
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

private const val MAX_LEVEL = 10