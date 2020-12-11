package com.example.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {
    companion object {
        fun newInstance(): FragmentHome {
            return FragmentHome()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        // сохраняем стейт
        savedInstanceState.putString(FragmentHome().tag, homeInc.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeWrapper.setOnClickListener {
            // считаем результат инкремента
            val result = homeInc.text.toString().toInt() + 1

            // записываем его
            homeInc.text = result.toString()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // восстанавливаем данные из стейта
        if (savedInstanceState != null) {
            homeInc.text = savedInstanceState.getString(FragmentHome().tag)
        }

    }

}