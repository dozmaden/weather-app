package com.dozmaden.weatherapp.dto

data class GeocodedItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String
)