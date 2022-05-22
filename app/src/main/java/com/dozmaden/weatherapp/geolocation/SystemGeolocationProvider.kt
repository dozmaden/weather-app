package com.dozmaden.weatherapp.geolocation

import android.location.Location
import android.util.Log

internal class SystemGeolocationProvider() : AbstractGeolocationProvider() {
    override fun getLocation(): Location {
        Log.i("SystemGeolocationProvider", "here!")
        TODO()
    }
}
