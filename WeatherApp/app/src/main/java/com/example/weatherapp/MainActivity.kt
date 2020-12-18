package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.*
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    companion object{
        const val URL = "https://api.openweathermap.org/data/2.5/"
    }

    interface OpenWeatherMapService {
        @GET("onecall?lat=59.937500&lon=30.308611&exclude=minutely,hourly,alerts&units=metric&appid=508af3c89d5fdcb4b9f030cea26e964e")
        fun listWeather(): Call<WeatherResponse>
    }

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    private val service = retrofit.create(OpenWeatherMapService::class.java)
    private var call: Call<WeatherResponse>? = service.listWeather()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val FBTRecycler: RecyclerView = findViewById(R.id.recyclerViewFBT)

        // заведём пустые листы, выполним запрос, чтобы их заполнить
        val FBTList : MutableList<FBT> = mutableListOf()
        val FBDList : MutableList<FBD> = mutableListOf()

        call?.enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                val weatherResponse = response.body()!!

                val weatherTemp = weatherResponse.current.temp.roundToInt()

                // приводим значение температуры к необходимому виду
                // и выводим её
                val tempNow = "${weatherTemp}°C"
                forecastNowTemp.text = tempNow

                forecastNowHumValue.text = weatherResponse.current.humidity.toString()

                val forecastNowIcon = weatherResponse.current.weather.first().icon

                Log.d("FORECAST NOW", weatherResponse.current.weather.size.toString())

                // получаем картинку для текущего прогноза
                Picasso.get()
                    .load("https://openweathermap.org/img/wn/$forecastNowIcon@2x.png")
                    .fit()
                    .centerCrop()
                    .into(forecastNowImg)

                for (FBDItem in weatherResponse.daily) {
                    val forecastDailyIcon = FBDItem.weather[0].icon
                    val forecastDailyTemp = "${FBDItem.temp.day.roundToInt()}°C"

                    val sdf = SimpleDateFormat("dd.MM")
                    val currDayNameNow = Date(FBDItem.dt.toLong() * 1000)
                    val formattedDate = sdf.format(currDayNameNow)

                    FBDList += FBD(
                        formattedDate,
                        forecastDailyTemp,
                        forecastDailyIcon)

                    Log.d("FBD", FBDList.toString())
                }

                val FBDRecycler: RecyclerView = findViewById(R.id.recyclerViewFBD)

                val FBDAdapter = FBDAdapter(FBDList.size, FBDList)

                val FBDlayoutManager = LinearLayoutManager(
                    applicationContext
                )

                FBDRecycler.layoutManager = FBDlayoutManager
                FBDRecycler.adapter = FBDAdapter
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("OWS", "API ERROR")
            }
        })

        val FBTAdapter = FBTAdapter(FBTList.size, FBTList)

        val FBTlayoutManager = LinearLayoutManager(
            applicationContext, LinearLayoutManager.HORIZONTAL, false
        )

        FBTRecycler.layoutManager = FBTlayoutManager
        FBTRecycler.adapter = FBTAdapter
    }

    fun changeTheme (view: View) {
        setTheme(R.style.BlackTheme)
    }

    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
        call = null
    }
}
