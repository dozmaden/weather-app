package com.dozmaden.weatherapp.api

import com.dozmaden.weatherapp.dto.WeatherInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

interface WeatherInfoApi {
    @GET("/onecall?lat={lat}&lon={lon}&units={units}&exclude=minutely,alerts")
    // &appid={api}
    fun getWeatherInfo(
        @Path("lat") latitude: Double,
        @Path("lon") longitude: Double,
        @Path("units") units: String,
//        @Path("api") apiKey: String
    ): Single<WeatherInfo>
}