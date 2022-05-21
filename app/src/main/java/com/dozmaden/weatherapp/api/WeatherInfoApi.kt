package com.dozmaden.weatherapp.api

import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

interface WeatherInfoApi {
    @GET("/weather?lat={lat}&lon={lon}&appid={api}")
    fun getWeatherInfo(
        @Path("lat") latitude: String,
        @Path("lon") longitude: String,
        @Path("api") apiKey: String
    )
}