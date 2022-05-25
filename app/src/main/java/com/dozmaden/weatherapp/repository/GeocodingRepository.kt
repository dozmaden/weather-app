package com.dozmaden.weatherapp.repository

import com.dozmaden.weatherapp.api.GeocodingInstance
import com.dozmaden.weatherapp.dto.LocationInfo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object GeocodingRepository {
    internal fun geocode(city: String) {
        GeocodingInstance.GEOCODING_API.directGeocoding(city)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
    }

    internal fun reverseGeocode(lat: Double, lon: Double): Single<List<LocationInfo>> {
        return GeocodingInstance.GEOCODING_API.reverseGeocoding(lat, lon)
            .retry(4L)
            .delay(300L, TimeUnit.MILLISECONDS, true)
            .subscribeOn(Schedulers.io())
    }
}
