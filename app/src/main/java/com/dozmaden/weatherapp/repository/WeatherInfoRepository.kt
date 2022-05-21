package com.dozmaden.weatherapp.repository

import com.dozmaden.weatherapp.api.WeatherInfoInstance
import com.dozmaden.weatherapp.dto.WeatherInfo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object WeatherInfoRepository {
    internal fun getWeatherInfo(lat: Double, lon: Double, units: String): Single<WeatherInfo> =
        WeatherInfoInstance.WEATHER_API.getWeatherInfo(lat, lon, units)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
}