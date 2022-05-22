package com.dozmaden.weatherapp.geolocation

import android.location.Location
import android.util.Log

internal abstract class AbstractGeolocationProvider : GeolocationProvider {

    protected var currentLocation: Location? = null

    override fun getLocation(): Location? {
        return currentLocation
    }
}
