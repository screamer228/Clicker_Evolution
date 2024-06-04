package com.example.clickerevolution.presentation.upgrades_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentUpgradesBinding
import com.example.clickerevolution.presentation.upgrades_fragment.adapter.ViewPagerAdapter
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class UpgradesFragment : Fragment() {

    private lateinit var _binding: FragmentUpgradesBinding
    private val binding get() = _binding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

//    @Inject
//    lateinit var upgradesViewModelFactory: UpgradesViewModelFactory
//    private lateinit var upgradesViewModel: UpgradesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectUpgradesFragment(this)

//        upgradesViewModel =
//            ViewModelProvider(
//                this,
//                upgradesViewModelFactory
//            )[UpgradesViewModel::class.java]

        _binding = FragmentUpgradesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()

        prepareViewPager()
    }

    private fun prepareViewPager() {
        val fragmentList = arrayListOf(
            UpgradesClickFragment.newInstance(),
            UpgradesPerSecFragment.newInstance()
        )
        val tabTitleArray = arrayOf("Click", "Per Sec")

        viewPager.adapter = ViewPagerAdapter(requireActivity(), fragmentList)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    override fun onResume() {
        super.onResume()

        Log.d("upgrades check", "UpgradesFragment onResume()")
    }

    private fun bindViews() {
        tabLayout = binding.tabLayoutUpgrades
        viewPager = binding.viewPagerUpgrades
    }
}