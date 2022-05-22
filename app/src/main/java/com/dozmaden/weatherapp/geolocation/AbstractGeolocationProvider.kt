package com.dozmaden.weatherapp.geolocation

import android.location.Location

internal abstract class AbstractGeolocationProvider : GeolocationProvider {

    protected lateinit var currentLocation: Location

    override fun getLocation(): Location {
        return currentLocation
    }
}
