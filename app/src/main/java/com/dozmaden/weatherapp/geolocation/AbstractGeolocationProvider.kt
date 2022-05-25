package com.dozmaden.weatherapp.geolocation

import android.location.Location

internal abstract class AbstractGeolocationProvider : GeolocationProvider {

    // fusedClient and LocationManager can return null
    protected var currentLocation: Location? = null

    override fun getLocation(): Location? {
        return currentLocation
    }
}
