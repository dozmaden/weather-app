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

    private val _currentGeolocation = MutableLiveData<Location>()
    internal val currentGeolocation: LiveData<Location> = _currentGeolocation
//                .apply { postValue(geolocationProvider.getLocation()) }

    fun getLocation() {
        _currentGeolocation.postValue(geolocationProvider.getLocation())
    }

    // TODO: Implement the ViewModel
}
