package com.example.clickerevolution.presentation.upgrades_fragment

import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentUpgradesSpecialBinding
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.upgradedetail_fragment.UpgradeDetailFragment
import com.example.clickerevolution.presentation.upgrades_fragment.adapter.UpgradesSpecialAdapter
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import com.example.clickerevolution.utils.NoAnimationItemAnimator
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesSpecialFragment : Fragment() {

    private lateinit var binding: FragmentUpgradesSpecialBinding
    private lateinit var adapter: UpgradesSpecialAdapter

    private lateinit var soundPool: SoundPool

    @Inject
    lateinit var upgradesViewModelFactory: UpgradesViewModelFactory
    private lateinit var upgradesViewModel: UpgradesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectUpgradesSpecialFragment(this)

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
        binding.recyclerViewUpgradesSpecial.itemAnimator = NoAnimationItemAnimator()

        viewLifecycleOwner.lifecycleScope.launch {
            upgradesViewModel.upgradesSpecialList.collect {
                adapter.updateList(it)
            }
        }
    }

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