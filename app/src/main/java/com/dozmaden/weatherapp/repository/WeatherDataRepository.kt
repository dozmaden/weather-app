package com.dozmaden.weatherapp.repository

import android.util.Log
import com.dozmaden.weatherapp.api.OpenWeatherInstance
import com.dozmaden.weatherapp.dto.GeocodeArray
import com.dozmaden.weatherapp.dto.LocationInfo
import com.dozmaden.weatherapp.dto.WeatherData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object WeatherDataRepository {
    internal fun getWeatherData(lat: Double, lon: Double, units: String): Single<WeatherData> {
        Log.i("WeatherDataRepository", "Getting weather from API!")
        return OpenWeatherInstance.OPEN_WEATHER_API.getWeatherInfo(lat, lon, units)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
    }

    internal fun getLocationName(lat: Double, lon: Double): Single<GeocodeArray> {
        Log.i("WeatherDataRepository", "Reverse geocoding location from API!")
        return OpenWeatherInstance.OPEN_WEATHER_API.reverseGeocoding(lat, lon)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
    }

    internal fun searchLocation(location: String): Single<LocationInfo> {
        Log.i("WeatherDataRepository", "Direct geocoding location from API!")
        return OpenWeatherInstance.OPEN_WEATHER_API.directGeocoding(location)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
    }
}
