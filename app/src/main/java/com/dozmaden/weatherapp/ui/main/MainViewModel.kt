package com.dozmaden.weatherapp.ui.main

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dozmaden.weatherapp.geolocation.GeolocationProvider
import com.dozmaden.weatherapp.geolocation.GeolocationProviderFactory

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val geolocationProvider: GeolocationProvider =
        GeolocationProviderFactory.getGeolocationProvider(application)

    private val _currentGeolocation = MutableLiveData<Location?>()
    internal val currentGeolocation: LiveData<Location?> = _currentGeolocation

    private val _locationUnavailable = MutableLiveData<Boolean>()
    internal val locationUnavailable: LiveData<Boolean> = _locationUnavailable

    fun getCurrentLocation(): Location? {
        val currentLocation = geolocationProvider.getLocation()

        if (currentLocation != null) {
            _currentGeolocation.postValue(currentLocation)
        } else {
            _locationUnavailable.postValue(true)
        }

        return currentLocation
    }

    // TODO: Implement the ViewModel
}
