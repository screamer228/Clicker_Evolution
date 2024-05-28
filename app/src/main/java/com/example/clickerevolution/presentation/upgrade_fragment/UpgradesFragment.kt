package com.example.clickerevolution.presentation.upgrade_fragment

import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentUpgradesBinding
import com.example.clickerevolution.presentation.upgrade_fragment.adapter.UpgradesAdapter
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.upgrade_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrade_fragment.viewmodel.UpgradesViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesFragment : Fragment() {

    private lateinit var binding: FragmentUpgradesBinding
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
        (requireActivity().applicationContext as App).appComponent.injectUpgradeFragment(this)

        sharedViewModel =
            ViewModelProvider(
                requireActivity(),
                sharedViewModelFactory
            )[SharedViewModel::class.java]

        upgradesViewModel =
            ViewModelProvider(this, upgradesViewModelFactory)[UpgradesViewModel::class.java]

        binding = FragmentUpgradesBinding.inflate(inflater, container, false)

        soundPoolBuy = SoundPool.Builder().setMaxStreams(3).build()
        soundPoolReject = SoundPool.Builder().setMaxStreams(3).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundIdBuy = soundPoolBuy.load(requireContext(), R.raw.sound_buy, 1)
        val soundIdReject = soundPoolReject.load(requireContext(), R.raw.sound_reject, 1)

        adapter = UpgradesAdapter { upgrade ->
            if (sharedViewModel.currentGold.value >= upgrade.price) {
                buyUpgrade(upgrade.price, upgrade.power, upgrade.id)
                playSound(soundPoolBuy, soundIdBuy)
            } else {
                playSound(soundPoolReject, soundIdReject)
                Toast.makeText(requireContext(), "Не хватает золота!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.recyclerViewUpgrades.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            upgradesViewModel.upgradesList.collect {
                adapter.updateList(it)
            }
        }
    }

    private fun buyUpgrade(price: Int, power: Int, id: Int) {
        sharedViewModel.subtractGold(price)
        sharedViewModel.setCurrentClickTick(power)
        upgradesViewModel.upgradeLevelAndPrice(id)
    }

    private fun playSound(soundPool: SoundPool, soundId: Int) {
        soundPool.play(soundId, 0.9f, 1.0f, 1, 0, 1.0f)
    }
}