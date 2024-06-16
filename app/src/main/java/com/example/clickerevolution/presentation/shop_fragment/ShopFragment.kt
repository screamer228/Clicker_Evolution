package com.example.clickerevolution.presentation.shop_fragment

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
import com.example.clickerevolution.common.CurrencyType
import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.databinding.FragmentShopBinding
import com.example.clickerevolution.presentation.shop_fragment.adapter.SkinsAdapter
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.shop_fragment.viewmodel.ShopViewModel
import com.example.clickerevolution.presentation.shop_fragment.viewmodel.ShopViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private lateinit var adapter: SkinsAdapter

    private lateinit var soundPoolBuy: SoundPool
    private lateinit var soundPoolReject: SoundPool
    private lateinit var soundPoolEquip: SoundPool
    private lateinit var soundPoolUnequip: SoundPool

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var shopViewModelFactory: ShopViewModelFactory
    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectShopFragment(this)

        injectSharedViewModel()
        injectShopViewModel()

        binding = FragmentShopBinding.inflate(inflater, container, false)

        soundPoolBuy = SoundPool.Builder().setMaxStreams(3).build()
        soundPoolReject = SoundPool.Builder().setMaxStreams(3).build()
        soundPoolEquip = SoundPool.Builder().setMaxStreams(3).build()
        soundPoolUnequip = SoundPool.Builder().setMaxStreams(3).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundIdBuy = soundPoolBuy.load(requireContext(), R.raw.sound_buy, 1)
        val soundIdReject = soundPoolReject.load(requireContext(), R.raw.sound_reject, 1)
        val soundIdEquip = soundPoolEquip.load(requireContext(), R.raw.sound_equip, 1)
        val soundIdUnequip = soundPoolUnequip.load(requireContext(), R.raw.sound_unequip, 1)

        adapter = SkinsAdapter { skin, action ->
            when (action) {
                SkinsAdapter.Action.PURCHASE -> {
                    when (skin.price.type) {

                        CurrencyType.GOLD -> {
                            if (sharedViewModel.currentResources.value.gold >= skin.price.value) {
                                buySkin(skin.price, skin.id)
                                playSound(soundPoolBuy, soundIdBuy)
                            } else {
                                playSound(soundPoolReject, soundIdReject)
                                Toast.makeText(
                                    requireContext(),
                                    "Не хватает золота!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }

                        CurrencyType.DIAMOND -> {
                            if (sharedViewModel.currentResources.value.diamonds >= skin.price.value) {
                                buySkin(skin.price, skin.id)
                                playSound(soundPoolBuy, soundIdBuy)
                            } else {
                                playSound(soundPoolReject, soundIdReject)
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

                SkinsAdapter.Action.EQUIP -> {
                    equipSkin(skin.imageId, skin.soundId, skin.id)
                    playSound(soundPoolEquip, soundIdEquip)
                }

                SkinsAdapter.Action.UNEQUIP -> {
                    unequipSkin(skin.id)
                    playSound(soundPoolUnequip, soundIdUnequip)
                }
            }
        }

        binding.recyclerViewSkins.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            shopViewModel.skinsList.collect {
                adapter.updateList(it)
            }
        }
    }

    private fun buySkin(price: Currency, id: Int) {
        when (price.type) {
            CurrencyType.GOLD -> sharedViewModel.subtractGold(price.value)
            CurrencyType.DIAMOND -> sharedViewModel.subtractDiamonds(price.value)
        }
        shopViewModel.purchaseSkin(id)
    }

    private fun equipSkin(imageId: Int, soundId: Int, id: Int) {
        sharedViewModel.setCurrentSkin(CurrentSkin(imageId, soundId))
        shopViewModel.equipSkin(id)
    }

    private fun unequipSkin(id: Int) {
        sharedViewModel.setCurrentSkin(CurrentSkin())
        shopViewModel.unequipSkin(id)
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

    private fun injectShopViewModel() {
        shopViewModel =
            ViewModelProvider(this, shopViewModelFactory)[ShopViewModel::class.java]
    }
}