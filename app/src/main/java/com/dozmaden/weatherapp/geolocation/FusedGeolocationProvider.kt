package com.dozmaden.weatherapp.geolocation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices

internal class FusedGeolocationProvider(context: Context) : AbstractGeolocationProvider() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun getLocation(): Location {
        fusedLocationClient.lastLocation.addOnSuccessListener { fusedLocation: Location? ->
            if (fusedLocation != null) run { currentLocation = fusedLocation }
            else {
                Log.d("FusedLocationProvider", "NULL LOCATION!")
            }
        }
        return super.getLocation()
    }
}
