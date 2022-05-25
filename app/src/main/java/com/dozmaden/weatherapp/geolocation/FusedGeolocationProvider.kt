package com.dozmaden.weatherapp.geolocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

internal class FusedGeolocationProvider(private val context: Context) :
    AbstractGeolocationProvider() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun getLocation(): Location? {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.i("FusedGeolocationProvider", "No permissions!")
        } else {

            fusedLocationClient
                .getCurrentLocation(
                    PRIORITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                            CancellationTokenSource().token
                        override fun isCancellationRequested() = false
                    }
                )
                .addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation = location
                        Log.i("FusedGeolocationProvider", "Got current location!")
                    } else {
                        Log.i("FusedGeolocationProvider", "Location is null!")
                    }
                }

            if (currentLocation == null) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        currentLocation = it
                        Log.i("FusedGeolocationProvider", "Got last available location!")
                    } else {
                        Log.i("FusedGeolocationProvider", "Location is null!")
                    }
                }
            }

            currentLocation?.let {
                Log.i(
                    "FusedGeolocationProvider",
                    "Latitude: " + currentLocation?.latitude.toString()
                )
                Log.i(
                    "FusedGeolocationProvider",
                    "Longitude: " + currentLocation?.longitude.toString()
                )
            }
        }
        return currentLocation
    }
}
