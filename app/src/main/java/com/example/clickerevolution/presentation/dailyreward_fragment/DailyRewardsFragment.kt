package com.example.clickerevolution.presentation.dailyreward_fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentDailyRewardsBinding
import com.example.clickerevolution.presentation.dailyreward_fragment.adapter.DailyRewardAdapter
import com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel.DailyRewardsViewModel
import com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel.DailyRewardsViewModelFactory
import com.example.clickerevolution.presentation.model.DailyReward
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModelFactory
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyRewardsFragment() : DialogFragment() {

    private lateinit var binding: FragmentDailyRewardsBinding
    private lateinit var adapter: DailyRewardAdapter

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var dailyRewardsViewModelFactory: DailyRewardsViewModelFactory
    private lateinit var dailyRewardsViewModel: DailyRewardsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectDailyRewardsFragment(this)

        sharedViewModel =
            ViewModelProvider(
                requireActivity(),
                sharedViewModelFactory
            )[SharedViewModel::class.java]

        dailyRewardsViewModel =
            ViewModelProvider(
                requireActivity(),
                dailyRewardsViewModelFactory
            )[DailyRewardsViewModel::class.java]

        binding = FragmentDailyRewardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DailyRewardAdapter { reward, action ->
            when (action) {
                DailyRewardAdapter.Action.CLAIM -> {
                    sharedViewModel.claimReward(reward.reward)
                    dailyRewardsViewModel.claimDailyReward()
                }
            }
        }

        binding.recyclerViewDailyRewards.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            combine(
                dailyRewardsViewModel.loginStreak,
                dailyRewardsViewModel.dailyRewardAvailable
            ) { streak, rewardAvailable ->
                streak to rewardAvailable
            }.collect { (streak, rewardAvailable) ->
                Log.d("DailyRewards", "Streak: $streak, RewardAvailable: $rewardAvailable")
                val rewards = (1..7).map { day ->
                    DailyReward(day, dailyRewardsViewModel.generateReward(day))
                }
                Log.d("DailyRewards", "Updates rewards: $rewards")
                adapter.updateList(rewards, streak, rewardAvailable)
            }
        }

        binding.dailyRewardButtonClose.setOnClickListener {
            dismiss()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        observers()
//    }
}