package com.dozmaden.weatherapp.geolocation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationRequest.PRIORITY_LOW_POWER
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

internal class FusedGeolocationProvider(private val context: Context) :
    AbstractGeolocationProvider() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun getLocation(): Location? {

        fusedLocationClient
            .getCurrentLocation(
                PRIORITY_LOW_POWER,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token
                    override fun isCancellationRequested() = false
                }
            )
            .addOnSuccessListener {
                currentLocation = it
                Log.i("FusedGeolocationProvider", "Got current location!")
                Log.i("IMHERE", it.latitude.toString())
                Log.i("IMHERE", it.longitude.toString())
            }

        currentLocation?.let {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                currentLocation = it
                Log.i("FusedGeolocationProvider", "Got last available location!")
            }
        }

        return currentLocation
    }
}
