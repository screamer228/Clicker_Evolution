package com.example.clickerevolution.presentation.dailyreward_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clickerevolution.R
import com.example.clickerevolution.common.ANIMATION_SCALE
import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.common.RewardButtonState
import com.example.clickerevolution.databinding.ItemDailyRewardBinding
import com.example.clickerevolution.presentation.model.DailyReward
import com.example.clickerevolution.utils.AnimationUtils.setOnTouchAnimation
import com.example.clickerevolution.utils.DailyRewardsDiffUtil

class DailyRewardsAdapter(
    private val onAction: (DailyReward, Action) -> Unit
) : RecyclerView.Adapter<DailyRewardsAdapter.ViewHolder>() {

    private var rewardsList: List<DailyReward> = emptyList()
    private var currentStreak: Int = 0
    private var rewardAvailable: Boolean = false

    inner class ViewHolder(
        private val binding: ItemDailyRewardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DailyReward) {
            binding.dailyRewardTitle.text = "${item.day} день "
            binding.dailyRewardReward.text = item.reward.value.toString()

            when (item.reward.type) {
                Currency.GOLD -> binding.dailyRewardIconCoin.setImageResource(R.drawable.ic_coin)
                Currency.DIAMOND -> binding.dailyRewardIconCoin.setImageResource(R.drawable.ic_diamond)
            }

            binding.dailyRewardTextViewReceive.text = "Получить"

            val buttonState = getButtonState(item.day, currentStreak, rewardAvailable)

            binding.dailyRewardButtonReceive.apply {
                when (buttonState) {
                    RewardButtonState.CLAIM -> {
                        binding.dailyRewardImageCheckMark.visibility = View.GONE
                        binding.dailyRewardTextViewReceive.setTextColor(
                            getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )
                        isEnabled = true
                        setCardBackgroundColor(getColor(context, R.color.green))
                        strokeColor = getColor(context, R.color.black)
                        setOnTouchAnimation(ANIMATION_SCALE)
                        setOnClickListener {
                            onAction(item, Action.CLAIM)
                        }
                    }

                    RewardButtonState.CLAIMED -> {
                        binding.dailyRewardImageCheckMark.visibility = View.VISIBLE
                        isEnabled = false
                        this.visibility = View.INVISIBLE
                    }

                    RewardButtonState.NOT_AVAILABLE -> {
                        binding.dailyRewardImageCheckMark.visibility = View.GONE
                        binding.dailyRewardTextViewReceive.setTextColor(
                            getColor(
                                binding.root.context,
                                R.color.gray
                            )
                        )
                        isEnabled = false
                        setCardBackgroundColor(getColor(context, R.color.gray))
                        strokeColor = getColor(context, R.color.gray)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDailyRewardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rewardsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = rewardsList.size

    fun updateList(newDataList: List<DailyReward>, currentStreak: Int, rewardAvailable: Boolean) {
        this.currentStreak = currentStreak
        this.rewardAvailable = rewardAvailable
        val diffUtil = DailyRewardsDiffUtil(rewardsList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        rewardsList = newDataList
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
            currentStreak > day -> RewardButtonState.CLAIMED
            currentStreak == day && !rewardAvailable -> RewardButtonState.CLAIMED
            currentStreak == day && rewardAvailable -> RewardButtonState.CLAIM
            else -> RewardButtonState.NOT_AVAILABLE
        }
    }

    enum class Action {
        CLAIM
    }
}