package com.dozmaden.weatherapp.api

import com.dozmaden.weatherapp.dto.WeatherInfo
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

interface WeatherInfoApi {
    @GET("/weather?lat={lat}&lon={lon}&units={units}&appid={api}")
    fun getWeatherInfo(
        @Path("lat") latitude: String,
        @Path("lon") longitude: String,
        @Path("units") units: String,
        @Path("api") apiKey: String
    ): WeatherInfo
}