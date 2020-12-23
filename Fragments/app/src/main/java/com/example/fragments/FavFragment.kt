package com.example.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.navigate
import kotlinx.android.synthetic.main.fragment_fav.*

class FavFragment : Fragment(R.layout.fragment_fav) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val increment = FavFragmentArgs.fromBundle(requireArguments()).increment
        favInc.text = "$increment"

        favWrapper.setOnClickListener {
            navigate(FavFragmentDirections.actionFavFragmentSelf(increment + 1))
        }
    }
}