package com.example.clickerevolution.presentation.dailyreward_fragment

import android.app.Dialog
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.common.ANIMATION_SCALE
import com.example.clickerevolution.databinding.FragmentDailyRewardsBinding
import com.example.clickerevolution.presentation.dailyreward_fragment.adapter.DailyRewardsAdapter
import com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel.DailyRewardsViewModel
import com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel.DailyRewardsViewModelFactory
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.model.DailyReward
import com.example.clickerevolution.utils.AnimationUtils.setOnTouchAnimation
import com.example.clickerevolution.utils.NoAnimationItemAnimator
import com.example.clickerevolution.utils.NonScrollableLinearLayoutManager
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyRewardsFragment() : DialogFragment() {

    private lateinit var binding: FragmentDailyRewardsBinding
    private lateinit var adapter: DailyRewardsAdapter
    private lateinit var soundPool: SoundPool

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

        soundPool = SoundPool.Builder().setMaxStreams(2).build()
        binding = FragmentDailyRewardsBinding.inflate(inflater, container, false)

        binding.recyclerViewDailyRewards.layoutManager =
            NonScrollableLinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundIdReceive = soundPool.load(requireContext(), R.raw.sound_action1, 1)
        val soundIdClose = soundPool.load(requireContext(), R.raw.sound_action2, 1)

        adapter = DailyRewardsAdapter { reward, action ->
            when (action) {
                DailyRewardsAdapter.Action.CLAIM -> {
                    sharedViewModel.claimReward(reward.reward)
                    dailyRewardsViewModel.claimDailyReward()
                    soundPool.play(soundIdReceive, 0.9f, 0.9f, 1, 0, 1.0f)
                }
            }
        }

        binding.recyclerViewDailyRewards.adapter = adapter
        binding.recyclerViewDailyRewards.itemAnimator = NoAnimationItemAnimator()

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

        binding.dailyRewardButtonClose.apply {
            setOnTouchAnimation(ANIMATION_SCALE)
            setOnClickListener {
                soundPool.play(soundIdClose, 0.9f, 0.9f, 1, 0, 1.0f)
                dismiss()
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        observers()
//    }
}