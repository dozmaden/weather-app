package com.dozmaden.weatherapp.geolocation

import android.location.Location

interface GeolocationProvider {
    fun getLocation(): Location
}
