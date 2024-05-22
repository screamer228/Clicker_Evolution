package com.example.clickerevolution.presentation.shop_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.app.App
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

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var shopViewModelFactory: ShopViewModelFactory
    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectShopFragment(this)

        sharedViewModel =
            ViewModelProvider(
                requireActivity(),
                sharedViewModelFactory
            )[SharedViewModel::class.java]

        shopViewModel =
            ViewModelProvider(this, shopViewModelFactory)[ShopViewModel::class.java]

        binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SkinsAdapter { skin, action ->
            when (action) {
                SkinsAdapter.Action.PURCHASE -> {
                    if (sharedViewModel.currentGold.value >= skin.price) {
                        sharedViewModel.subtractGold(skin.price)
                        shopViewModel.purchaseSkin(skin.id)
                    } else {
                        Toast.makeText(requireContext(), "Не хватает золота!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                SkinsAdapter.Action.EQUIP -> {
                    sharedViewModel.setCurrentSkin(CurrentSkin(skin.imageId, skin.soundId))
                    shopViewModel.equipSkin(skin.id)
                }

                SkinsAdapter.Action.UNEQUIP -> {
                    sharedViewModel.setCurrentSkin(CurrentSkin())
                    shopViewModel.unequipSkin(skin.id)
                }
            }
        }

        binding.recyclerViewSkins.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            shopViewModel.skinsList.collect {
                adapter.updateList(it)
            }
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            sharedViewModel.resourcesFlow.collectLatest {
//                adapter.gold = it.gold
//                adapter.notifyDataSetChanged() // Обновляем адаптер при изменении золота
//            }
//        }
    }
}