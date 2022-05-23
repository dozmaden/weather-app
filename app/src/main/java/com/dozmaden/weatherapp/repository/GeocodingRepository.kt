package com.dozmaden.weatherapp.repository

import com.dozmaden.weatherapp.api.GeocodingInstance
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object GeocodingRepository {
    internal fun geocode(city: String) {
        GeocodingInstance.GEOCODING_API.directGeocoding(city)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
    }

    internal fun reverseGeocode(lat: Double, lon: Double) {
        GeocodingInstance.GEOCODING_API.reverseGeocoding(lat, lon)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
    }
}
