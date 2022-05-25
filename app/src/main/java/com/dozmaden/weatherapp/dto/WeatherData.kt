package com.dozmaden.weatherapp.dto

data class WeatherData(
    val current: CurrentWeather,
    val daily: List<DailyWeather>,
    val hourly: List<HourlyWeather>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)