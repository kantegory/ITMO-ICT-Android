package com.example.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.navigate
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val increment = HomeFragmentArgs.fromBundle(requireArguments()).increment
        homeInc.text = "$increment"

        homeWrapper.setOnClickListener {
            navigate(HomeFragmentDirections.actionHomeFragmentSelf(increment + 1))
        }

        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.home)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}
