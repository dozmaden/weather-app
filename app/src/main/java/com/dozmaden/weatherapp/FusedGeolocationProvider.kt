package com.dozmaden.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices

class FusedGeolocationProvider(private val context: Context) : AbstractGeolocationProvider() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun getLocation(context: Context?): Location {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { fusedLocation: Location? ->
                if (fusedLocation != null) {
                    location = fusedLocation
                } else {
                    Log.d("FusedLocationProvider", "NULL LOCATION!")
                }
            }
        return super.getLocation(context)
    }
}