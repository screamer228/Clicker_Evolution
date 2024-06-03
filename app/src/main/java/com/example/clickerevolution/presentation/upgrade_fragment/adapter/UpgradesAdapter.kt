package com.example.clickerevolution.presentation.upgrade_fragment.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.R
import com.example.clickerevolution.databinding.ItemUpgradeBinding
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.utils.UpgradesDiffUtil

class UpgradesAdapter(
    private val onClick: (Upgrade) -> Unit
//    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<UpgradesAdapter.ViewHolder>() {

    private var upgradesList: List<Upgrade> = emptyList()

    inner class ViewHolder(
        private val binding: ItemUpgradeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Upgrade, isEnabled: Boolean) {
            binding.upgradeTitle.text = item.title
//            binding.upgradeImage.setImageResource(item.imageId)
            binding.upgradeLevel.text = "Уровень: ${item.level}"
            binding.upgradePower.text = "Сила: +${item.power}"
            binding.upgradePrice.text = item.price.toString()



            if (isEnabled) {
                binding.upgradeButtonUpgrade.setCardBackgroundColor(
                    getColor(
                        binding.root.context,
                        R.color.green
                    )
                )
                binding.upgradeTitle.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                binding.upgradePower.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.yellow
                    )
                )
                binding.upgradeLevel.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.yellow
                    )
                )
                binding.upgradePrice.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                binding.upgradeActionText.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                binding.upgradeButtonUpgrade.isClickable = true
                binding.upgradeButtonUpgrade.setOnClickListener {
                    onClick(item)
                }
            } else {
                binding.upgradeButtonUpgrade.setCardBackgroundColor(
                    getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.upgradeTitle.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.upgradePower.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.upgradeLevel.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.upgradePrice.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.upgradeActionText.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.upgradeButtonUpgrade.isClickable = false
            }

//            binding.overlayView.visibility = if (isEnabled) View.GONE else View.VISIBLE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUpgradeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = upgradesList[position]
        val isEnabled = position == 0 || upgradesList[position - 1].level >= 1
        holder.bind(item, isEnabled)
//        holder.itemView.setOnClickListener {
//            itemClickListener.onItemClick(item.id)
//        }
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
