package com.dozmaden.weatherapp.dto

class LocationInfo : ArrayList<LocationInfoItem>()

data class LocationInfoItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)
