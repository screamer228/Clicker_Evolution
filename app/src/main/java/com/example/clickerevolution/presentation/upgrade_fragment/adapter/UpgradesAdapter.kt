package com.example.clickerevolution.presentation.upgrade_fragment.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.R
import com.example.clickerevolution.databinding.ItemUpgradeBinding
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.utils.UpgradesDiffUtil

class UpgradesAdapter(
//    var gold: Int,
    private val onClick: (Upgrade) -> Unit
//    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<UpgradesAdapter.ViewHolder>() {

    private var upgradesList: List<Upgrade> = emptyList()

    inner class ViewHolder(
        private val binding: ItemUpgradeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Upgrade) {
            binding.upgradeTitle.text = item.title
//            binding.upgradeImage.setImageResource(item.imageId)
            binding.upgradeLevel.text = "Уровень: ${item.level}"
            binding.upgradePrice.text = item.price.toString()
            binding.upgradeButtonUpgrade.setCardBackgroundColor(R.color.green)
            binding.upgradeButtonUpgrade.setOnClickListener {
                onClick(item)
            }

//            when {
//                !item.isPurchased -> {
//                    binding.skinActionText.text = "Купить"
//                    binding.skinButtonAction.setOnClickListener {
//                        onAction(item, Action.PURCHASE)
//                    }
//                }
//
//                item.isPurchased && !item.isEquipped -> {
//                    binding.skinActionLinear.visibility = GONE
//                    binding.skinActionText.text = "Одеть"
//                    binding.skinButtonAction.setOnClickListener {
//                        onAction(item, Action.EQUIP)
//                    }
//                }
//
//                item.isPurchased && item.isEquipped -> {
//                    binding.skinActionLinear.visibility = GONE
//                    binding.skinActionText.text = "Снять"
//                    binding.skinButtonAction.setOnClickListener {
//                        onAction(item, Action.UNEQUIP)
//                    }
//                }
//            }
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
        holder.bind(item)
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
