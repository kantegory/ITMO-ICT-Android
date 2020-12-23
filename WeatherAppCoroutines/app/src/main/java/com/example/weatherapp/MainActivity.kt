package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    companion object{
        const val URL = "https://api.openweathermap.org/data/2.5/"
        const val apiKey = BuildConfig.OWMKey
    }

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    interface OpenWeatherMapService {
        @GET("onecall?lat=59.937500&lon=30.308611&exclude=minutely,alerts&units=metric&appid=${apiKey}")
        fun getWeather(): Call<WeatherResponse>
    }

    private val service = retrofit.create(OpenWeatherMapService::class.java)
    private var call: Call<WeatherResponse>? = service.getWeather()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val FBTRecycler: RecyclerView = findViewById(R.id.recyclerViewFBT)

        // заведём пустые листы, выполним запрос, чтобы их заполнить
        val FBTList : MutableList<FBT> = mutableListOf()
        val FBDList : MutableList<FBD> = mutableListOf()

        fun showAllWeather(weatherResponse: WeatherResponse) {
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

            // заполняем прогноз на 48 часов
            for (FBTItem in weatherResponse.hourly) {
                val forecastHourlyIcon = FBTItem.weather[0].icon
                val forecastHourlyTemp = "${FBTItem.temp.roundToInt()}°C"

                val sdf = SimpleDateFormat("hh:mm")
                val currTimeNameNow = Date(FBTItem.dt.toLong() * 1000)
                val formattedDate = sdf.format(currTimeNameNow)

                FBTList += FBT(
                    formattedDate,
                    forecastHourlyTemp,
                    forecastHourlyIcon
                )

                Log.d("FBT", FBTList.toString())
            }

            // заполняем прогноз на 7 дней
            for (FBDItem in weatherResponse.daily) {
                val forecastDailyIcon = FBDItem.weather[0].icon
                val forecastDailyTemp = "${FBDItem.temp.day.roundToInt()}°C"

                val sdf = SimpleDateFormat("dd.MM")
                val currDayNameNow = Date(FBDItem.dt.toLong() * 1000)
                val formattedDate = sdf.format(currDayNameNow)

                FBDList += FBD(
                    formattedDate,
                    forecastDailyTemp,
                    forecastDailyIcon
                )

                Log.d("FBD", FBDList.toString())
            }

            // заводим ресайклер для hourly
            val FBTAdapter = FBTAdapter(FBTList.size, FBTList)

            val FBTlayoutManager = LinearLayoutManager(
                applicationContext, LinearLayoutManager.HORIZONTAL, false
            )

            FBTRecycler.layoutManager = FBTlayoutManager
            FBTRecycler.adapter = FBTAdapter

            // заводим ресайклер для daily
            val FBDRecycler: RecyclerView = findViewById(R.id.recyclerViewFBD)

            val FBDAdapter = FBDAdapter(FBDList.size, FBDList)

            val FBDlayoutManager = LinearLayoutManager(
                applicationContext
            )

            FBDRecycler.layoutManager = FBDlayoutManager
            FBDRecycler.adapter = FBDAdapter
        }

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "appDB"
        ).build()

        suspend fun updateDataInDB(weatherResponseData: WeatherResponseRoom) {
            // очищаем все прошлые данные
            db.weatherResponseDAO().deleteAll()

            // записываем новые
            db.weatherResponseDAO().insert(weatherResponseData)
        }

        // начинаем запрос
        call?.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                val weatherResponse = response.body()!!

                // переводим всё в json
                val moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<WeatherResponse> = moshi.adapter<WeatherResponse>(
                    weatherResponse::class.java
                )
                val json: String = jsonAdapter.toJson(weatherResponse)

                // записываем значение
                val weatherResponseData = WeatherResponseRoom(
                    id = 1,
                    response = json
                )

                // запись данных в бд
                GlobalScope.launch {
                    updateDataInDB(weatherResponseData)
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("OWS Error", t.toString())
            }
        })

        GlobalScope.launch {
            db.weatherResponseDAO().getAll().collect {
                    data ->
                        try {
                            // достаём данные
                            val weatherData = data.response

                            // десериализуем
                            val moshi = Moshi.Builder().build()
                            val jsonAdapter: JsonAdapter<WeatherResponse> =
                                moshi.adapter<WeatherResponse>(WeatherResponse::class.java)
                            val weatherResponse: WeatherResponse =
                                jsonAdapter.fromJson(weatherData)!!

                            // показываем погоду
                            runOnUiThread {
                                showAllWeather(weatherResponse)
                            }
                        } catch (e: Throwable) {
                            Log.d("COROUTINE", "FAILED")
                        }
            }
        }
    }

    fun changeTheme(view: View) {
        setTheme(R.style.BlackTheme)
    }

    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
        call = null
    }
}
