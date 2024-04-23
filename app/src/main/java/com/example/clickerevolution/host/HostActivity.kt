package com.example.clickerevolution.host

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.clickerevolution.HomeFragment
import com.example.clickerevolution.R
import com.example.clickerevolution.ShopFragment
import com.example.clickerevolution.UpgradeFragment
import com.example.clickerevolution.databinding.ActivityMainBinding
import com.example.clickerevolution.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HostActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        loadFragment(HomeFragment())

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

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView,fragment)
        transaction.commit()
    }
}