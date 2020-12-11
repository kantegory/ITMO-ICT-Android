package com.example.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_stats.*

class FragmentStats : Fragment() {
    companion object {
        fun newInstance(): FragmentStats {
            return FragmentStats()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_stats, container, false)
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        // сохраняем стейт
        savedInstanceState.putString(FragmentStats().tag, statsInc.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statsWrapper.setOnClickListener {
            // считаем результат инкремента
            val result = statsInc.text.toString().toInt() + 1

            // записываем его
            statsInc.text = result.toString()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // восстанавливаем данные из стейта
        if (savedInstanceState != null) {
            statsInc.text = savedInstanceState.getString(FragmentStats().tag)
        }

    }

}