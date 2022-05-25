package com.dozmaden.weatherapp.api

import com.dozmaden.weatherapp.dto.Weathers
import com.dozmaden.weatherapp.dto.geocoding_dto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/"

interface OpenWeatherApi {
    //    @GET("/onecall?lat={lat}&lon={lon}&units={units}&exclude=minutely,alerts")
    @GET("/data/2.5/onecall")
    // &appid={api}
    fun getWeatherInfo(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        //        @Path("api") apiKey: String
        @Query("exclude") exclude: String = "minutely,alerts"
    ): Single<Weathers>
    //
    //    @GET("/direct")
    //    fun directGeocoding(
    //        @Path("city") city: String,
    //        @Path("limit") limit: Int = 5
    //    ): Single<List<LocationInfo>>

    @GET("/reverse")
    fun reverseGeocoding(
        @Path("lat") latitude: Double,
        @Path("lon") longitude: Double,
        @Path("limit") limit: Int = 1,
    ): Single<geocoding_dto>
}
