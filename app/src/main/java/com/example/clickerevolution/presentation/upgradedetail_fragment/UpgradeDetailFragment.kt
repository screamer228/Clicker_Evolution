package com.example.clickerevolution.presentation.upgradedetail_fragment

import android.app.Dialog
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.common.ANIMATION_SCALE
import com.example.clickerevolution.databinding.FragmentUpgradeDetailBinding
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.presentation.upgradedetail_fragment.viewmodel.UpgradeDetailViewModel
import com.example.clickerevolution.presentation.upgradedetail_fragment.viewmodel.UpgradeDetailViewModelFactory
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import com.example.clickerevolution.utils.AnimationUtils.setOnTouchAnimation
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

    @Inject
    lateinit var upgradeDetailViewModelFactory: UpgradeDetailViewModelFactory
    private lateinit var upgradeDetailViewModel: UpgradeDetailViewModel

    private lateinit var soundPool: SoundPool

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
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
        injectUpgradeDetailViewModel()

        binding = FragmentUpgradeDetailBinding.inflate(inflater, container, false)

        soundPool = SoundPool.Builder().setMaxStreams(3).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upgradeDetailViewModel.getDetailUpgrade(upgradeId)

        val soundIdBuy = soundPool.load(requireContext(), R.raw.sound_buy, 1)
        val soundIdReject = soundPool.load(requireContext(), R.raw.sound_reject, 1)

        viewLifecycleOwner.lifecycleScope.launch {
            upgradeDetailViewModel.detailUpgrade.collect { upgrade ->
                binding.upgradeDetailTitle.text = upgrade.title
                binding.upgradeDetailImage.load(upgrade.imageId)

                when (upgrade.level) {
                    MAX_LEVEL -> {
                        binding.upgradeDetailPrice.isVisible = false
                        binding.upgradeDetailIcPriceDiamond.isVisible = false
                        binding.upgradeDetailPower.text = when (upgrade.title) {
                            "Сон" -> {
                                "Сила: ${15 + (upgrade.power * upgrade.level)}%"
                            }

                            " Жадность " -> {
                                "Сила: ${upgrade.power * upgrade.level}"
                            }

                            else -> {
                                ""
                            }
                        }
                        binding.upgradeDetailActionText.text = "Макс. уровень"
                        binding.upgradeDetailButtonUpgrade.setCardBackgroundColor(
                            getColor(
                                binding.root.context,
                                R.color.gray
                            )
                        )
                    }

                    else -> {
                        binding.upgradeDetailPower.text = when (upgrade.title) {
                            "Сон" -> {
                                "Сила: ${15 + (upgrade.power * upgrade.level)}% + ${upgrade.power}%"
                            }

                            " Жадность " -> {
                                "Сила: ${upgrade.power * upgrade.level} + ${upgrade.power}"
                            }

                            else -> {
                                ""
                            }
                        }
                    }
                }

                binding.upgradeDetailLevel.text = "Уровень: ${upgrade.level}"

                binding.upgradeDetailDescription.text = upgrade.description

                binding.upgradeDetailPrice.text = upgrade.price.value.toString()

                binding.upgradeDetailButtonUpgrade.apply {
                    setOnTouchAnimation(ANIMATION_SCALE)
                    setOnClickListener {
                        if (sharedViewModel.currentResources.value.diamonds >= upgrade.price.value) {
                            buyUpgrade(upgrade)
                            playSound(soundPool, soundIdBuy)
                        } else {
                            playSound(soundPool, soundIdReject)
                            Toast.makeText(
                                requireContext(),
                                "Не хватает алмазов!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun buyUpgrade(upgrade: Upgrade) {
        sharedViewModel.subtractDiamonds(upgrade.price.value)
        when (upgrade.title) {
            "Сон" -> {
                sharedViewModel.setCurrentGoldOfflineMultiplier(upgrade.power)
            }

            " Жадность " -> {
                sharedViewModel.setCurrentDiamondsTick(upgrade.power)
            }
        }

        upgradeDetailViewModel.upgradeLevelAndPriceSpecial(upgrade.id)
        upgradesViewModel.getUpgradesSpecialList()
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

    private fun injectUpgradeDetailViewModel() {
        upgradeDetailViewModel =
            ViewModelProvider(
                requireActivity(),
                upgradeDetailViewModelFactory
            )[UpgradeDetailViewModel::class.java]
    }
}

private const val MAX_LEVEL = 10