package com.example.clickerevolution.presentation.host

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.presentation.HomeFragment
import com.example.clickerevolution.presentation.SharedViewModel
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.presentation.ShopFragment
import com.example.clickerevolution.presentation.UpgradeFragment
import com.example.clickerevolution.databinding.ActivityHostBinding
import com.example.clickerevolution.presentation.SharedViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private lateinit var topBarCounterTV: TextView
    private lateinit var bottomNavigationView: BottomNavigationView

    @Inject
    lateinit var viewModelFactory: SharedViewModelFactory
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (this.applicationContext as App).appComponent.injectHostActivity(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]

        bindViews()

        prepareBottomNav()

        lifecycleScope.launch {
            viewModel.resourcesFlow.collect {

                topBarCounterTV.text = it.gold.toString()
            }
        }

    }

    override fun onStop() {
        viewModel.saveGoldValue(topBarCounterTV.text.toString())

        super.onStop()
    }

    private fun bindViews() {
        topBarCounterTV = binding.textViewTopBarCounter
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
                R.id.upgradeFragment -> {
                    loadFragment(UpgradeFragment())
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView,fragment)
        transaction.commit()
    }
}