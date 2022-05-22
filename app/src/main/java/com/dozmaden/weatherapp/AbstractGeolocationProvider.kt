package com.dozmaden.weatherapp

import android.content.Context
import android.location.Location

abstract class AbstractGeolocationProvider : GeolocationProvider {
    protected lateinit var location: Location

    override fun getLocation(context: Context?): Location {
        return location
    }
}