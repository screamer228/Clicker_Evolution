package com.example.clickerevolution.presentation.upgrade_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentUpgradesBinding
import com.example.clickerevolution.presentation.upgrade_fragment.adapter.UpgradesAdapter
import com.example.clickerevolution.presentation.viewmodel.SharedViewModel
import com.example.clickerevolution.presentation.viewmodel.SharedViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesFragment : Fragment() {

    private lateinit var binding: FragmentUpgradesBinding
    private lateinit var adapter: UpgradesAdapter

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

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

        binding = FragmentUpgradesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UpgradesAdapter { upgrade ->

        }

        binding.recyclerViewUpgrades.adapter = adapter

//        viewLifecycleOwner.lifecycleScope.launch {
//            upgradesViewModel.upgradesList.collect {
//                adapter.updateList(it)
//            }
//        }
    }
}