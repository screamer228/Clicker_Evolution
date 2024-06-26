package com.example.clickerevolution.presentation.upgrades_fragment

import android.media.SoundPool
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentUpgradesBinding
import com.example.clickerevolution.presentation.upgrades_fragment.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UpgradesFragment : Fragment() {

    private lateinit var _binding: FragmentUpgradesBinding
    private val binding get() = _binding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var soundPool: SoundPool
    private var soundIdSwipe: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectUpgradesFragment(this)

        soundPool = SoundPool.Builder().setMaxStreams(2).build()
        soundIdSwipe = soundPool.load(requireContext(), R.raw.sound_swipe, 1)

        _binding = FragmentUpgradesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()

        prepareViewPager()

        customizeTabLayout()

        setInitialTabTextSize()
    }

    private fun playSoundSwipe() {
        soundPool.play(soundIdSwipe, 0.2f, 0.2f, 1, 0, 1.0f)
    }

    private fun prepareViewPager() {
        val fragmentList = arrayListOf(
            UpgradesClickFragment.newInstance(),
            UpgradesPerSecFragment.newInstance()
        )
        val tabTitleArray = arrayOf("За клик", "В секунду")

        viewPager.adapter = ViewPagerAdapter(requireActivity(), fragmentList)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                playSoundSwipe()
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = createTabView(tabTitleArray[position])
        }.attach()
    }

    private fun customizeTabLayout() {
        val tabTitleArray = arrayOf("За клик", "В секунду")

        for (i in tabTitleArray.indices) {
            val tab = tabLayout.getTabAt(i)
            tab?.customView = createTabView(tabTitleArray[i])
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabView = tab.customView as? TextView
                tabView?.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    14f
                ) // Increase text size for selected tab
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabView = tab.customView as? TextView
                tabView?.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    10f
                ) // Reset text size for unselected tab
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Optionally handle reselection
            }
        })
    }

    private fun setInitialTabTextSize() {
        val selectedTab = tabLayout.getTabAt(tabLayout.selectedTabPosition)
        val selectedTabView = selectedTab?.customView as? TextView
        selectedTabView?.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            14f
        ) // Increase text size for selected tab

        for (i in 0 until tabLayout.tabCount) {
            if (i != tabLayout.selectedTabPosition) {
                val tab = tabLayout.getTabAt(i)
                val tabView = tab?.customView as? TextView
                tabView?.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    10f
                ) // Reset text size for unselected tab
            }
        }
    }

    private fun createTabView(tabTitle: String): View {
        val tabView = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
        tabView.text = tabTitle
        return tabView
    }

    private fun bindViews() {
        tabLayout = binding.tabLayoutUpgrades
        viewPager = binding.viewPagerUpgrades
    }
}