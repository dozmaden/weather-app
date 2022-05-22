package com.dozmaden.weatherapp

import android.content.Context
import android.location.Location

interface GeolocationProvider {
    fun getLocation(context: Context? = null): Location
}