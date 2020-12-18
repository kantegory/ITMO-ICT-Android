package com.example.weatherapp

import com.squareup.moshi.Json

class WeatherResponse(
    @field:Json(name = "timezone") var timezone: String,
    @field:Json(name = "current") var current: Current,
    @field:Json(name = "daily") var daily: List<Daily>
)

class Current(
    @field:Json(name = "humidity") var humidity: Int,
    @field:Json(name = "temp") var temp: Float,
    @field:Json(name = "weather") var weather: List<WeatherDescription>
)

class Daily(
    @field:Json(name = "dt") var dt: String,
    @field:Json(name = "temp") var temp: Temp,
    @field:Json(name = "weather") var weather: List<WeatherDescription>
)

class Temp(
    @field:Json(name = "day") var day: Float
)

class WeatherDescription(
    @field:Json(name = "main") var main: String,
    @field:Json(name = "icon") var icon: String
)
