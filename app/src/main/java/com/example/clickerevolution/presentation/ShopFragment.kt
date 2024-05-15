package com.example.clickerevolution.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentHomeBinding
import com.example.clickerevolution.databinding.FragmentShopBinding
import com.example.clickerevolution.presentation.adapter.SkinsAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    @Inject
    lateinit var viewModelFactory: SharedViewModelFactory
    private lateinit var viewModel: SharedViewModel
    private val adapter: SkinsAdapter = SkinsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectShopFragment(this)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[SharedViewModel::class.java]

        binding = FragmentShopBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSkins.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resourcesFlow.collect {
                adapter.updateList(it.skinsList)
            }
        }
    }
}