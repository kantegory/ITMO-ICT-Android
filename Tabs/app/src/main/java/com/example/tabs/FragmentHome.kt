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

//    private val CURRENT_FRAGMENT_TAG = "current_fragment_tag"
//    private var mCurrentFragmentTag: String? = null // Tag for current fragment instance
//    private val FRAGMENT_TAG_SEPERATOR = ":"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        // сохраняем стейт
        savedInstanceState.putString("result", homeInc.text.toString())
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
            println("SAVING FUCKING STATE")
            homeInc.text = savedInstanceState.getString("result")
        }

    }

//    fun getFragmentTag(): String? {
//        if (mCurrentFragmentTag == null) {
//            mCurrentFragmentTag = this.javaClass.simpleName + FRAGMENT_TAG_SEPERATOR + this.hashCode()
//        }
//        return mCurrentFragmentTag
//    }
}