package com.example.clickerevolution.presentation.dailyreward_fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.R
import com.example.clickerevolution.common.CurrencyType
import com.example.clickerevolution.common.RewardButtonState
import com.example.clickerevolution.databinding.ItemDailyRewardBinding
import com.example.clickerevolution.presentation.model.DailyReward
import com.example.clickerevolution.presentation.model.Skin
import com.example.clickerevolution.presentation.shop_fragment.adapter.SkinsAdapter
import com.example.clickerevolution.utils.DailyRewardDiffUtil

class DailyRewardAdapter(
    private val onAction: (DailyReward, Action) -> Unit
) : RecyclerView.Adapter<DailyRewardAdapter.ViewHolder>() {

    private var rewardsList: List<DailyReward> = emptyList()
    private var currentStreak: Int = 0
    private var rewardAvailable: Boolean = false

    inner class ViewHolder(
        private val binding: ItemDailyRewardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DailyReward) {
            Log.d("DailyRewards", "adapter: bind($item)")

            binding.dailyRewardTitle.text = "${item.day} день"
            binding.dailyRewardReward.text = item.reward.value.toString()

            when (item.reward.type) {
                CurrencyType.GOLD -> binding.dailyRewardIconCoin.setImageResource(R.drawable.ic_coin)
                CurrencyType.DIAMOND -> binding.dailyRewardIconCoin.setImageResource(R.drawable.ic_diamond)
            }

            val buttonState = getButtonState(item.day, currentStreak, rewardAvailable)

            binding.dailyRewardButtonReceive.apply {
                when (buttonState) {
                    RewardButtonState.CLAIM -> {
                        isEnabled = true
                        setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
                        binding.dailyRewardTextViewReceive.text = "Получить"
                        setOnClickListener {
                            onAction(item, Action.CLAIM)
                        }
                    }

                    RewardButtonState.CLAIMED -> {
                        isEnabled = false
                        setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                        binding.dailyRewardTextViewReceive.text = "Получено"
                    }

                    RewardButtonState.NOT_AVAILABLE -> {
                        isEnabled = false
                        setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                        binding.dailyRewardTextViewReceive.text = "Недоступно"
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("DailyRewards", "Creating ViewHolder")
        val binding = ItemDailyRewardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rewardsList[position]
        Log.d("DailyRewards", "Binding ViewHolder at position $position with item $item")
        holder.bind(item)
    }

    override fun getItemCount(): Int = rewardsList.size

    fun updateList(newDataList: List<DailyReward>, currentStreak: Int, rewardAvailable: Boolean) {
        this.currentStreak = currentStreak
        this.rewardAvailable = rewardAvailable
        val diffUtil = DailyRewardDiffUtil(rewardsList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        rewardsList = newDataList
        Log.d("DailyRewards", "New RewardsList in adapter: $rewardsList")
        diffResult.dispatchUpdatesTo(this)

        rewardsList = newDataList
        notifyDataSetChanged()
    }

    private fun getButtonState(
        day: Int,
        currentStreak: Int,
        rewardAvailable: Boolean
    ): RewardButtonState {
        return when {
            currentStreak >= day -> RewardButtonState.CLAIMED
            currentStreak == day && rewardAvailable -> RewardButtonState.CLAIM
            else -> RewardButtonState.NOT_AVAILABLE
        }
    }

    enum class Action {
        CLAIM
    }
}