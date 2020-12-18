package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    companion object{
        const val URL = "https://api.openweathermap.org/data/2.5/"
    }

    interface OpenWeatherMapService {
        @GET("onecall?lat=59.937500&lon=30.308611&exclude=minutely,hourly,alerts&units=metric&appid=508af3c89d5fdcb4b9f030cea26e964e")
        fun listWeather(): Call<WeatherResponse>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val FBTRecycler: RecyclerView = findViewById(R.id.recyclerViewFBT)

        // значения заглушек
        val FBTList : List<FBT> = listOf(
            FBT("12:00", "2℃", R.drawable.cloud),
            FBT("14:00", "5℃", R.drawable.sun),
            FBT("16:00", "0℃", R.drawable.snow),
            FBT("18:00", "0℃", R.drawable.snow),
            FBT("20:00", "3℃", R.drawable.cloud),
            FBT("22:00", "1℃", R.drawable.snow),
            FBT("00:00", "4℃", R.drawable.snow)
        )

        val FBTAdapter = FBTAdapter(FBTList.size, FBTList)

        val FBTlayoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        FBTRecycler.layoutManager = FBTlayoutManager
        FBTRecycler.adapter = FBTAdapter

        val FBDRecycler: RecyclerView = findViewById(R.id.recyclerViewFBD)

        // значения заглушек
        val FBDList : List<FBD> = listOf(
            FBD("12, Дек", "2℃", R.drawable.cloud),
            FBD("13, Дек", "5℃", R.drawable.sun),
            FBD("14, Дек", "0℃", R.drawable.snow),
            FBD("15, Дек", "0℃", R.drawable.snow),
            FBD("16, Дек", "3℃", R.drawable.cloud),
            FBD("17, Дек", "1℃", R.drawable.snow),
            FBD("18, Дек", "4℃", R.drawable.snow)
        )

        val FBDAdapter = FBDAdapter(FBDList.size, FBDList)

        val FBDlayoutManager = LinearLayoutManager(
            this
        )

        FBDRecycler.layoutManager = FBDlayoutManager
        FBDRecycler.adapter = FBDAdapter
    }

    fun changeTheme (view: View) {
        setTheme(R.style.BlackTheme)
    }
}
