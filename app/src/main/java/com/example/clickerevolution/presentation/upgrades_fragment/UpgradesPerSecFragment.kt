package com.example.clickerevolution.presentation.upgrades_fragment

import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.databinding.FragmentUpgradesPerSecBinding
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.upgrades_fragment.adapter.UpgradesAdapter
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import com.example.clickerevolution.utils.NoAnimationItemAnimator
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesPerSecFragment : Fragment() {

    private lateinit var binding: FragmentUpgradesPerSecBinding
    private lateinit var adapter: UpgradesAdapter

    private lateinit var soundPoolBuy: SoundPool
    private lateinit var soundPoolReject: SoundPool

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var upgradesViewModelFactory: UpgradesViewModelFactory
    private lateinit var upgradesViewModel: UpgradesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectUpgradesPerSecFragment(this)

        injectSharedViewModel()
        injectUpgradesViewModel()

        binding = FragmentUpgradesPerSecBinding.inflate(inflater, container, false)

        soundPoolBuy = SoundPool.Builder().setMaxStreams(3).build()
        soundPoolReject = SoundPool.Builder().setMaxStreams(3).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundIdBuy = soundPoolBuy.load(requireContext(), R.raw.sound_buy, 1)
        val soundIdReject = soundPoolReject.load(requireContext(), R.raw.sound_reject, 1)

        adapter = UpgradesAdapter(UpgradeType.TICK_PER_SEC) { upgrade ->
            if (sharedViewModel.currentResources.value.gold >= upgrade.price.value) {
                buyUpgrade(upgrade)
                playSound(soundPoolBuy, soundIdBuy)
            } else {
                playSound(soundPoolReject, soundIdReject)
                Toast.makeText(requireContext(), "Не хватает золота!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.recyclerViewUpgradesPerSec.adapter = adapter
        binding.recyclerViewUpgradesPerSec.itemAnimator = NoAnimationItemAnimator()

        viewLifecycleOwner.lifecycleScope.launch {
            upgradesViewModel.upgradesPerSecList.collect {
                updatesAdapterState(it)
            }
        }
    }

    private fun updatesAdapterState(upgrades: List<Upgrade>) {
        val updatedList = upgrades.mapIndexed { index, upgrade ->
            if (index == 0 || upgrades[index - 1].level >= 1) {
                upgrade.copy(isEnabled = true)
            } else {
                upgrade.copy(isEnabled = false)
            }
        }
        adapter.updateList(updatedList)
    }

    private fun buyUpgrade(upgrade: Upgrade) {
        sharedViewModel.subtractGold(upgrade.price.value)
        sharedViewModel.setCurrentTickPerSec(upgrade.power)
        upgradesViewModel.upgradeLevelAndPrice(upgrade.id, upgrade.type)
    }

    private fun playSound(soundPool: SoundPool, soundId: Int) {
        soundPool.play(soundId, 0.9f, 1.0f, 1, 0, 1.0f)
    }

    private fun injectSharedViewModel() {
        sharedViewModel =
            ViewModelProvider(
                requireActivity(),
                sharedViewModelFactory
            )[SharedViewModel::class.java]
    }

    private fun injectUpgradesViewModel() {
        upgradesViewModel =
            ViewModelProvider(
                requireActivity(),
                upgradesViewModelFactory
            )[UpgradesViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        Log.d("upgrades check", "UpgradesPerSecFragment onResume()")
    }

    companion object {
        fun newInstance() = UpgradesPerSecFragment()
    }
}