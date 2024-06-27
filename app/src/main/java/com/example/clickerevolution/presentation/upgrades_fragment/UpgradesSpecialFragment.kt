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
import com.example.clickerevolution.databinding.FragmentUpgradesSpecialBinding
import com.example.clickerevolution.presentation.dialog_fragment.DialogFragment
import com.example.clickerevolution.presentation.model.Upgrade
import com.example.clickerevolution.presentation.upgrades_fragment.adapter.UpgradesAdapter
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.upgrades_fragment.adapter.UpgradesSpecialAdapter
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesSpecialFragment : Fragment() {

    private lateinit var binding: FragmentUpgradesSpecialBinding
    private lateinit var adapter: UpgradesSpecialAdapter

    private lateinit var soundPool: SoundPool

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
        (requireActivity().applicationContext as App).appComponent.injectUpgradesSpecialFragment(this)

//        injectSharedViewModel()
        injectUpgradesViewModel()

        binding = FragmentUpgradesSpecialBinding.inflate(inflater, container, false)

        soundPool = SoundPool.Builder().setMaxStreams(3).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundIdDetail = soundPool.load(requireContext(), R.raw.sound_action1, 1)

        adapter = UpgradesSpecialAdapter { id ->
            val dialogFragment = UpgradeDetailFragment(id)
            dialogFragment.show(parentFragmentManager, "Upgrade Detail Dialog Fragment")
            soundPool.play(soundIdDetail, 0.9f, 0.9f, 1, 0, 1.0f)
        }

        binding.recyclerViewUpgradesSpecial.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            upgradesViewModel.upgradesSpecialList.collect {
                adapter.updateList(it)
            }
        }
    }

    private fun playSound(soundPool: SoundPool, soundId: Int) {
        soundPool.play(soundId, 0.9f, 1.0f, 1, 0, 1.0f)
    }

//    private fun injectSharedViewModel() {
//        sharedViewModel =
//            ViewModelProvider(
//                requireActivity(),
//                sharedViewModelFactory
//            )[SharedViewModel::class.java]
//    }

    private fun injectUpgradesViewModel() {
        upgradesViewModel =
            ViewModelProvider(
                requireActivity(),
                upgradesViewModelFactory
            )[UpgradesViewModel::class.java]
    }

    companion object {
        fun newInstance() = UpgradesSpecialFragment()
    }
}