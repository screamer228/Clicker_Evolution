package com.example.clickerevolution.presentation.upgrades_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.R
import com.example.clickerevolution.common.ANIMATION_SCALE
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.databinding.ItemUpgradeBinding
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.utils.AnimationUtils.setOnTouchAnimation
import com.example.clickerevolution.utils.StringUtil.addCommaEveryThreeDigits
import com.example.clickerevolution.utils.UpgradesDiffUtil

class UpgradesAdapter(
    private val type: UpgradeType,
    private val onClick: (Upgrade) -> Unit
) : RecyclerView.Adapter<UpgradesAdapter.ViewHolder>() {

    private var upgradesList: List<Upgrade> = emptyList()

    inner class ViewHolder(
        private val binding: ItemUpgradeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Upgrade, isEnabled: Boolean) {
            binding.upgradeTitle.text = item.title
//            binding.upgradeImage.setImageResource(item.imageId)
            binding.upgradeLevel.text = "Уровень: ${item.level}"

            when (type) {
                UpgradeType.CLICK_TICK -> {
                    binding.upgradePower.text = "Сила: +${item.power}"
                }

                UpgradeType.TICK_PER_SEC -> {
                    binding.upgradePower.text = "Сила: +${item.power}/сек"
                }

                else -> {}
            }

            binding.upgradePrice.text = addCommaEveryThreeDigits(item.price.value)

            if (isEnabled) {
                binding.upgradeButtonUpgrade.apply {
                    setCardBackgroundColor(
                        getColor(
                            binding.root.context,
                            R.color.green
                        )
                    )
                    strokeColor = getColor(binding.root.context, R.color.black)
                }
                binding.upgradeTitle.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                binding.upgradeLevel.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.yellow
                    )
                )
                binding.upgradePower.setTextColor(
                    getColor(
                        binding.root.context,
                        R.color.light_green
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
                binding.upgradeButtonUpgrade.apply {
                    isClickable = true
                    setOnTouchAnimation(ANIMATION_SCALE)
                    setOnClickListener {
                        onClick(item)
                    }
                }
            } else {
                binding.upgradeButtonUpgrade.apply {
                    setCardBackgroundColor(
                        getColor(
                            binding.root.context,
                            R.color.gray
                        )
                    )
                    strokeColor = getColor(binding.root.context, R.color.gray)
                }
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
