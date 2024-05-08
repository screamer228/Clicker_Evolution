package com.example.clickerevolution.presentation.host

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.clickerevolution.presentation.HomeFragment
import com.example.clickerevolution.presentation.HomeViewModel
import com.example.clickerevolution.R
import com.example.clickerevolution.presentation.ShopFragment
import com.example.clickerevolution.presentation.UpgradeFragment
import com.example.clickerevolution.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareBottomNav()


    }

    private fun prepareBottomNav() {

        binding.bottomNavigationView.itemIconTintList = null

        binding.bottomNavigationView.setOnItemSelectedListener {
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