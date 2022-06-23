package com.dozmaden.weatherapp.api

import com.dozmaden.weatherapp.dto.GeocodeArray
import com.dozmaden.weatherapp.dto.LocationInfo
import com.dozmaden.weatherapp.dto.WeatherData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/"

interface OpenWeatherApi {
    @GET("/data/2.5/onecall")
    fun getWeatherInfo(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("exclude") exclude: String = "minutely,alerts"
    ): Single<WeatherData>

    @GET("geo/1.0/direct")
    fun directGeocoding(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5
    ): Single<LocationInfo>

    @GET("/geo/1.0/reverse")
    fun reverseGeocoding(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int = 1,
    ): Single<GeocodeArray>
}
