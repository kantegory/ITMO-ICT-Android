package com.example.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.navigate
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment(R.layout.fragment_notes) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val increment = NotesFragmentArgs.fromBundle(requireArguments()).increment
        notesInc.text = "$increment"

        notesWrapper.setOnClickListener {
            navigate(NotesFragmentDirections.actionNotesFragmentSelf(increment + 1))
        }
    }
}