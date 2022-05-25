package com.dozmaden.weatherapp.geolocation

import android.content.Context
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

object GeolocationProviderFactory {
    fun getGeolocationProvider(context: Context): GeolocationProvider {
        //        return if (checkPlayServices(context)) {
        //            Log.i("GeolocationProviderFactory", "Google Play Services are available!")
        //            FusedGeolocationProvider(context)
        //        } else
        return LocationManagerProvider(context)
    }

    private fun checkPlayServices(context: Context): Boolean {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(context)
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                Log.i("GeolocationProviderFactory", "Something went wrong!")
            } else {
                Log.i("GeolocationProviderFactory", "This device is not supported.")
            }
            return false
        }
        return true
    }
}
