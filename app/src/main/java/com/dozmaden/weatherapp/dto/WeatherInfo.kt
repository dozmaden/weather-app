package com.dozmaden.weatherapp.dto

data class WeatherInfo(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
//    val alerts: List<Alert>
)