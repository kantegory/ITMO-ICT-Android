package com.example.tabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_nav)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, FragmentHome())
            .commit()

        bottomNav.setOnNavigationItemSelectedListener {
            val selectedFragment : Fragment = when (it.itemId) {
                R.id.nav_home -> {
                    FragmentHome()
                }
                R.id.nav_favorite -> {
                    FragmentFavorite()
                }
                R.id.nav_stat -> {
                    FragmentStats()
                }
                else -> {
                    FragmentHome()
                }
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()

            true
        }
    }

}