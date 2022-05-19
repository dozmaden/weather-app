package com.dozmaden.weatherapp.api

import retrofit2.http.GET

const val BASE_URL = "https://api.openweathermap.org/"

interface WeatherInfoApi {
    @GET(BASE_URL)
    fun getWeatherInfo()
}