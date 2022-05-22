package com.dozmaden.weatherapp.geolocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest.PRIORITY_LOW_POWER
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
            Log.i("IMHERE", "nopermissions")
        } else {
            Log.i("IMHERE", "havePermissions")

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
                    Log.i("IMHERE", "CURRENT LOCATION SUCCESS")
                    Log.i("IMHERE", it.latitude.toString())
                    Log.i("IMHERE", it.longitude.toString())
                }
        }
        if (currentLocation == null) {
            Log.i("IMHERE", "currentLocation = null")
        }

        Log.i("IMHERE", "currentLocation")

        return currentLocation
    }
}
