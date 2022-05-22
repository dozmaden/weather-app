package com.dozmaden.weatherapp.geolocation

import android.content.Context
import android.util.Log
import com.dozmaden.weatherapp.utils.GeolocationPermissionsUtility
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

object GeolocationProviderFactory {
    fun getGeolocationProvider(context: Context): GeolocationProvider {

        if (checkPlayServices(context)) {
            Log.i("GeolocationUtility", "Google Play Services are available!")
        } else {
            Log.i("GeolocationUtility", "Google Play Services are unavailable!")
        }

        return if (GeolocationPermissionsUtility.hasLocationPermissions(context)) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            FusedGeolocationProvider(context)
        } else SystemGeolocationProvider()
    }

    private fun checkPlayServices(context: Context): Boolean {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(context)
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                //                private val PLAY_SERVICES_RESOLUTION_REQUEST = 1000
                //                googleAPI.getErrorDialog(this, result,
                // PLAY_SERVICES_RESOLUTION_REQUEST)
                //                    ?.show()
            } else {
                Log.i("MainFragment", "This device is not supported.")
                //                finish()
            }
            return false
        }
        return true
    }
}
