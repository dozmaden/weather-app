package com.dozmaden.weatherapp.dto

data class DailyWeather(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val moonrise: Int,
    val moonset: Int,
    val moonphase: Double,
    val temp: Temp,
    val feels_like: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double,
    val weather: List<Weather>,
    val clouds: Int,
    val pop: Int,
    val uvi: Double
)
