package com.example.clickerevolution.presentation.shop_fragment.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.R
import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.common.Rarity
import com.example.clickerevolution.databinding.ItemSkinBinding
import com.example.clickerevolution.presentation.model.Skin
import com.example.clickerevolution.utils.AnimationUtils.setTouchAnimation
import com.example.clickerevolution.utils.SkinsDiffUtil
import com.example.clickerevolution.utils.StringUtil.addCommaEveryThreeDigits

class SkinsAdapter(
    private val onAction: (Skin, Action) -> Unit
) : RecyclerView.Adapter<SkinsAdapter.ViewHolder>() {

    private var skinsList: List<Skin> = emptyList()

    inner class ViewHolder(
        private val binding: ItemSkinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Skin) {
            binding.skinTitle.text = item.title
            binding.skinImage.setImageResource(item.imageId)

            when (item.price.type) {
                Currency.GOLD -> {
                    binding.skinIconPrice.setImageResource(R.drawable.ic_coin)
                }

                Currency.DIAMOND -> {
                    binding.skinIconPrice.setImageResource(R.drawable.ic_diamond)
                }
            }

            binding.skinPrice.text = addCommaEveryThreeDigits(item.price.value)

            when (item.rarity) {
                Rarity.COMMON -> {
                    binding.skinCardView.setCardBackgroundColor(
                        getColor(binding.root.context, R.color.light_blue)
                    )
                }

                Rarity.RARE -> {
                    binding.skinCardView.setCardBackgroundColor(
                        getColor(
                            binding.root.context, R.color.light_green
                        )
                    )
                }

                Rarity.EPIC -> {
                    binding.skinCardView.setCardBackgroundColor(
                        getColor(
                            binding.root.context, R.color.violet
                        )
                    )
                }

                Rarity.LEGENDARY -> {
                    binding.skinCardView.setCardBackgroundColor(
                        getColor(
                            binding.root.context, R.color.orange
                        )
                    )
                }
            }

            binding.skinButtonAction.setTouchAnimation(0.9f)

            when {
                !item.isPurchased -> {
                    binding.skinButtonAction.setCardBackgroundColor(
                        getColor(
                            binding.root.context, R.color.green
                        )
                    )
                    binding.skinActionText.text = "Купить"
                    binding.skinButtonAction.setOnClickListener {
                        onAction(item, Action.PURCHASE)
                    }
                }

                item.isPurchased && !item.isEquipped -> {
                    binding.skinButtonAction.setCardBackgroundColor(
                        getColor(
                            binding.root.context, R.color.blue
                        )
                    )
                    binding.skinActionLinear.visibility = GONE
                    binding.skinActionText.text = "Одеть"
                    binding.skinButtonAction.setOnClickListener {
                        onAction(item, Action.EQUIP)
                    }
                }

                item.isPurchased && item.isEquipped -> {
                    binding.skinButtonAction.setCardBackgroundColor(
                        getColor(
                            binding.root.context, R.color.red
                        )
                    )
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