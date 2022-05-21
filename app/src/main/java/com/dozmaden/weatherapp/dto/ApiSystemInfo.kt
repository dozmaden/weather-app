package com.dozmaden.weatherapp.dto

data class ApiSystemInfo(
    val type: Int,
    val id: Int,
    val message: Double,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
