package com.dozmaden.weatherapp.repository

import android.util.Log
import com.dozmaden.weatherapp.api.WeatherInfoInstance
import com.dozmaden.weatherapp.dto.Weathers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object WeatherDataRepository {
    internal fun getWeatherData(lat: Double, lon: Double, units: String): Single<Weathers> {
        Log.i("WeatherDataRepository", "Getting weather from API!")
        return WeatherInfoInstance.WEATHER_API.getWeatherInfo(lat, lon, units)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
    }
}
