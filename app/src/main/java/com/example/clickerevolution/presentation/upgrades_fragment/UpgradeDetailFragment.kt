package com.example.clickerevolution.presentation.upgrades_fragment

import android.app.Dialog
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentDialogBinding
import com.example.clickerevolution.databinding.FragmentUpgradeDetailBinding
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import com.example.clickerevolution.utils.StringUtil.addCommaEveryThreeDigits
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradeDetailFragment(
    private val upgradeId: Int
) : DialogFragment() {

    private lateinit var binding: FragmentUpgradeDetailBinding

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var upgradesViewModelFactory: UpgradesViewModelFactory
    private lateinit var upgradesViewModel: UpgradesViewModel

    private lateinit var soundPool: SoundPool

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectUpgradeDetailFragment(this)

        injectSharedViewModel()
        injectUpgradesViewModel()

        binding = FragmentUpgradeDetailBinding.inflate(inflater, container, false)

        soundPool = SoundPool.Builder().setMaxStreams(3).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundIdBuy = soundPool.load(requireContext(), R.raw.sound_buy, 1)
        val soundIdReject = soundPool.load(requireContext(), R.raw.sound_reject, 1)

//        var upgrade = Upgrade()

        viewLifecycleOwner.lifecycleScope.launch {
            upgradesViewModel.upgradesSpecialList.collect {
                val upgrade = it[id]
                binding.upgradeDetailTitle.text = upgrade.title
                binding.upgradeDetailPower.text = "Сила: ${upgrade.power}%"
                binding.upgradeDetailLevel.text = "Уровень: ${upgrade.level}"
                binding.upgradeDetailPrice.text = upgrade.price.value.toString()

                binding.upgradeDetailButtonUpgrade.setOnClickListener {
                    if (sharedViewModel.currentResources.value.diamonds >= upgrade.price.value) {
                        buyUpgrade(upgrade)
                        playSound(soundPool, soundIdBuy)
                    } else {
                        playSound(soundPool, soundIdReject)
                        Toast.makeText(
                            requireContext(),
                            "Не хватает золота!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    private fun buyUpgrade(upgrade: Upgrade) {
        sharedViewModel.subtractDiamonds(upgrade.price.value)
        sharedViewModel.setCurrentOfflineGoldMultiplier(upgrade.power)
        upgradesViewModel.upgradeLevelAndPrice(upgrade.id, upgrade.type)
    }

    private fun playSound(soundPool: SoundPool, soundId: Int) {
        soundPool.play(soundId, 0.9f, 0.9f, 1, 0, 1.0f)
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
}