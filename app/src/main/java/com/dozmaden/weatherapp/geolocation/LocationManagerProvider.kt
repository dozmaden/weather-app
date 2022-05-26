package com.dozmaden.weatherapp.geolocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat

internal class LocationManagerProvider(private val context: Context) :
    AbstractGeolocationProvider() {
    override fun getLocation(): Location? {

        val geolocationProvider =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: throw error (request the missing permissions!)
            Log.i("LocationManagerProvider", "No permissions!")
            null
        } else {
            geolocationProvider.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        }
    }
}
