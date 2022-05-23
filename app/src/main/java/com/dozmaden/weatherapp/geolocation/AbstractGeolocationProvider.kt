package com.dozmaden.weatherapp.geolocation

import android.location.Location

internal abstract class AbstractGeolocationProvider : GeolocationProvider {

    protected var currentLocation: Location? = null

    override fun getLocation(): Location? {
        return currentLocation
    }
}
