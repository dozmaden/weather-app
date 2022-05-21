package com.dozmaden.weatherapp.api

import com.dozmaden.weatherapp.dto.Location
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

const val GEOCODING_URL = "http://api.openweathermap.org/geo/1.0/"

interface GeocodingApi {

    @GET("/direct?q={city}&limit=5")
    // &appid={api}
    fun directGeocoding(
        @Path("city") city: String,
//        @Path("api") apiKey: String
    ): Single<List<Location>>

    @GET("/reverse?lat={lat}&lon={lon}&limit=1&appid={api}")
    // &appid={api}
    fun reverseGeocoding(
        @Path("lat") latitude: Double,
        @Path("lon") longitude: Double,
//        @Path("api") apiKey: String
    ): Single<List<Location>>
}