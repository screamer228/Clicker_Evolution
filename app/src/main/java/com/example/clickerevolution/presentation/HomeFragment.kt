package com.example.clickerevolution.presentation

import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentHomeBinding
import com.example.clickerevolution.presentation.dailyreward_fragment.DailyRewardsFragment
import com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel.DailyRewardsViewModel
import com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel.DailyRewardsViewModelFactory
import com.example.clickerevolution.presentation.dialog_fragment.DialogFragment
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModelFactory
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var imageToClick: ImageView
    private lateinit var clickTick: TextView
    private lateinit var tickPerSec: TextView
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var imageDailyReward: ImageView

    private lateinit var soundPool: SoundPool

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var dailyRewardsViewModelFactory: DailyRewardsViewModelFactory
    private lateinit var dailyRewardsViewModel: DailyRewardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectHomeFragment(this)

        injectSharedViewModel()

        dailyRewardsViewModel =
            ViewModelProvider(
                this,
                dailyRewardsViewModelFactory
            )[DailyRewardsViewModel::class.java]

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindViews()

        soundPool = SoundPool.Builder().setMaxStreams(4).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var resIdClick = R.raw.sound_cookie_click

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.currentSkin.collect {
                imageToClick.setImageResource(it.imageId)
                resIdClick = it.soundId
            }
        }

        val soundIdClick = soundPool.load(requireContext(), resIdClick, 1)

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.currentStats.collect {
                progressBar.progress = it.diamondProgressBar
                //TODO анимация прибавления алмаза
                clickTick.text = "+${it.goldClickTickValue} за клик"
                tickPerSec.text = "+${it.goldTickPerSecValue} в секунду"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            dailyRewardsViewModel.dailyRewardAvailable.collect {
                //TODO проверить изменяется ли сразу после получения
                binding.cardViewIndicatorDailyReward.visibility = if (it) View.VISIBLE else View.GONE
                Log.d("DailyRewards", "HomeFragment: collect indicator visible")
//                binding.cardViewIndicatorDailyReward.isVisible = it
            }
        }

        imageDailyReward.setOnClickListener {
            val dialogFragment = DailyRewardsFragment()
            dialogFragment.show(parentFragmentManager, "Daily Rewards Fragment")
        }

        imageToClick.setOnClickListener {

            sharedViewModel.onButtonClick()

            soundPool.play(soundIdClick, 1.0f, 1.0f, 1, 0, 1.0f)

            imageToClick.animate().apply {
                duration = 50
                scaleXBy(1.0F)
                scaleX(0.9F)
                scaleYBy(1.0F)
                scaleY(0.9F)
            }.withEndAction {
                imageToClick.animate().apply {
                    duration = 50
                    scaleXBy(0.9F)
                    scaleX(1.0F)
                    scaleYBy(0.9F)
                    scaleY(1.0F)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        soundPool.release()
    }

    private fun injectSharedViewModel() {
        sharedViewModel =
            ViewModelProvider(
                requireActivity(),
                sharedViewModelFactory
            )[SharedViewModel::class.java]
    }

    private fun bindViews() {
        imageToClick = binding.imageViewImageToClick
        clickTick = binding.textViewClickTickValue
        tickPerSec = binding.textViewTickPerSecondValue
        progressBar = binding.progressBarDiamonds
        imageDailyReward = binding.imageViewImageDailyReward
    }
}