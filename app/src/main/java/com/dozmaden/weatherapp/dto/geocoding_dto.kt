package com.dozmaden.weatherapp.dto

class geocoding_dto : ArrayList<geocoding_dtoItem>()

data class geocoding_dtoItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String
)

data class LocalNames(
    val ascii: String,
    val de: String,
    val en: String,
    val feature_name: String,
    val fr: String,
    val nl: String,
    val no: String,
    val ro: String
)
