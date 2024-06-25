package com.example.clickerevolution.presentation.host

import android.media.SoundPool
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.presentation.home_fragment.HomeFragment
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.presentation.shop_fragment.ShopFragment
import com.example.clickerevolution.databinding.ActivityHostBinding
import com.example.clickerevolution.presentation.dialog_fragment.DialogFragment
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.upgrades_fragment.UpgradesFragment
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModel
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import com.example.clickerevolution.utils.AnimationUtils.startHostDiamondAnimation
import com.example.clickerevolution.utils.StringUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private lateinit var goldCounterTV: TextView
    private lateinit var diamondCounterTV: TextView
    private lateinit var bottomNavigationView: BottomNavigationView

    @Inject
    lateinit var viewModelFactory: SharedViewModelFactory
    private lateinit var viewModel: SharedViewModel

    @Inject
    lateinit var upgradesViewModelFactory: UpgradesViewModelFactory
    private lateinit var upgradesViewModel: UpgradesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.injectHostActivity(this)

        val soundPool = SoundPool.Builder().setMaxStreams(2).build()
        val soundIdDiamond = soundPool.load(this, R.raw.sound_diamonds, 1)

        injectSharedViewModel()

        bindViews()

        prepareBottomNav()

//        upgradesViewModel.getUpgradesClickList()
//        upgradesViewModel.getUpgradesPerSecList()

        lifecycleScope.launch {
            viewModel.currentResources.collect {
                goldCounterTV.text = StringUtil.addCommaEveryThreeDigits(it.gold)

//                if (it.diamonds > diamondCounterTV.text.toString()
//                        .toInt()
//                ) startHostDiamondAnimation(binding.iconTopBarDiamond)
//                diamondCounterTV.text = StringUtil.addCommaEveryThreeDigits(it.diamonds)


                val diamondsString = StringUtil.addCommaEveryThreeDigits(it.diamonds)
                if (diamondsString != diamondCounterTV.text.toString()) {
                    diamondCounterTV.text = diamondsString
                }
            }
        }

        fun diamondsIncrementUiActions() {
            soundPool.play(soundIdDiamond, 0.8f, 0.8f, 1, 0, 1.0f)
            startHostDiamondAnimation(binding.iconTopBarDiamond)
        }

        diamondCounterTV.addTextChangedListener {

            soundPool.play(soundIdDiamond, 0.8f, 0.8f, 1, 0, 1.0f)

            startHostDiamondAnimation(binding.iconTopBarDiamond)
        }

        val offlineEarned = viewModel.calculateGoldForOfflineTime()

        if (offlineEarned > 0) {
            showDialogGoldOfflineEarned(offlineEarned)
        }
    }

//    override fun onStart() {
//        super.onStart()
//
//        val offlineEarned = viewModel.calculateGoldForOfflineTime()
//
//        if (offlineEarned > 0) {
//            val dialogFragment = DialogFragment(offlineEarned)
//            dialogFragment.show(supportFragmentManager, "Dialog Fragment")
//        }
//    }

//    override fun onStop() {
//        super.onStop()
//        viewModel.saveLastExitTime()
//    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.saveResources()
        viewModel.saveStats()
        viewModel.saveLastExitTime()
    }

    private fun showDialogGoldOfflineEarned(goldValue: Int) {
        val dialogFragment = DialogFragment(goldValue)
        dialogFragment.show(supportFragmentManager, "Dialog Fragment")
    }

    private fun bindViews() {
        goldCounterTV = binding.textViewTopBarGoldCounter
        diamondCounterTV = binding.textViewTopBarDiamondCounter
        bottomNavigationView = binding.bottomNavigationView
    }

    private fun prepareBottomNav() {

        bottomNavigationView.itemIconTintList = null

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.shopFragment -> {
                    loadFragment(ShopFragment())
                    true
                }

                R.id.homeFragment -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.upgradesFragment -> {
                    loadFragment(UpgradesFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.commit()
    }

    private fun injectSharedViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]

        upgradesViewModel =
            ViewModelProvider(this, upgradesViewModelFactory)[UpgradesViewModel::class.java]
    }
}