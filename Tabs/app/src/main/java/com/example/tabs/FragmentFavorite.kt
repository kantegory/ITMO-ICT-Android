package com.example.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_favorite.*

class FragmentFavorite : Fragment() {
    companion object {
        fun newInstance(): FragmentFavorite {
            return FragmentFavorite()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        // сохраняем стейт
        savedInstanceState.putString(FragmentFavorite().tag, favInc.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favWrapper.setOnClickListener {
            // считаем результат инкремента
            val result = favInc.text.toString().toInt() + 1

            // записываем его
            favInc.text = result.toString()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // восстанавливаем данные из стейта
        if (savedInstanceState != null) {
            favInc.text = savedInstanceState.getString(FragmentFavorite().tag)
        }

    }

}