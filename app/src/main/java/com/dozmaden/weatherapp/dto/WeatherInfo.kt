package com.dozmaden.weatherapp.dto

data class WeatherInfo (
    val coord: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: MainInfo,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: ApiSystemInfo,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)