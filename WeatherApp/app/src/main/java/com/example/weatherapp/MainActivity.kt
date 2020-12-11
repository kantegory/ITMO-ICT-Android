package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val FBTRecycler: RecyclerView = findViewById(R.id.recyclerViewFBT)

        // значения заглушек
        val FBTList : List<FBT> = listOf(
            FBT("12:00", "2", R.drawable.cloud),
            FBT("14:00", "5", R.drawable.sun),
            FBT("16:00", "0", R.drawable.snow),
            FBT("18:00", "0", R.drawable.snow),
            FBT("20:00", "3", R.drawable.cloud),
            FBT("22:00", "1", R.drawable.snow),
            FBT("00:00", "4", R.drawable.snow)
        )

        val FBTAdapter = FBTAdapter(FBTList.size, FBTList)

        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        FBTRecycler.layoutManager = layoutManager
        FBTRecycler.adapter = FBTAdapter
    }
}
