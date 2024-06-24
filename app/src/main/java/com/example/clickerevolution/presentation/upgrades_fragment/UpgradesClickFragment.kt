package com.example.clickerevolution.presentation.upgrades_fragment

import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.databinding.FragmentUpgradesClickBinding
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.presentation.upgrades_fragment.adapter.UpgradesAdapter
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesClickFragment : Fragment() {

    private lateinit var binding: FragmentUpgradesClickBinding
    private lateinit var adapter: UpgradesAdapter

    private lateinit var soundPoolBuy: SoundPool
    private lateinit var soundPoolReject: SoundPool
//    private lateinit var soundPoolSwipe: SoundPool

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
        (requireActivity().applicationContext as App).appComponent.injectUpgradesClickFragment(this)

        injectSharedViewModel()
        injectUpgradesViewModel()

        binding = FragmentUpgradesClickBinding.inflate(inflater, container, false)

        soundPoolBuy = SoundPool.Builder().setMaxStreams(3).build()
        soundPoolReject = SoundPool.Builder().setMaxStreams(3).build()
//        soundPoolSwipe = SoundPool.Builder().setMaxStreams(2).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upgradesViewModel.getUpgradesClickList()
        upgradesViewModel.getUpgradesPerSecList()

        val soundIdBuy = soundPoolBuy.load(requireContext(), R.raw.sound_buy, 1)
        val soundIdReject = soundPoolReject.load(requireContext(), R.raw.sound_reject, 1)

        adapter = UpgradesAdapter(UpgradeType.CLICK_TICK) { upgrade ->
            if (sharedViewModel.currentResources.value.gold >= upgrade.price) {
                buyUpgrade(upgrade.price, upgrade.power, upgrade.id)
                playSound(soundPoolBuy, soundIdBuy)
            } else {
                playSound(soundPoolReject, soundIdReject)
                Toast.makeText(requireContext(), "Не хватает золота!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.recyclerViewUpgradesClick.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            upgradesViewModel.upgradesClickList.collect {
                Log.d("upgrades check", "UpgradesClickFragment observer: ${it.size}")
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
        Log.d("upgrades check", "UpgradesClickFragment updatesAdapterState")
    }

    private fun buyUpgrade(price: Int, power: Int, id: Int) {
        sharedViewModel.subtractGold(price)
        sharedViewModel.setCurrentClickTick(power)
        upgradesViewModel.upgradeLevelAndPrice(id)
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

//    override fun onResume() {
//        super.onResume()
//
//        val soundIdSwipe = soundPoolSwipe.load(requireContext(), R.raw.sound_swipe, 1)
//        playSound(soundPoolSwipe, soundIdSwipe)
//    }

    companion object {
        fun newInstance() = UpgradesClickFragment()
    }
}