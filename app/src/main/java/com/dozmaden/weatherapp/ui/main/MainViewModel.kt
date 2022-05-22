package com.dozmaden.weatherapp.ui.main

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dozmaden.weatherapp.GeolocationProvider
import com.dozmaden.weatherapp.dto.WeatherInfo
import com.dozmaden.weatherapp.utils.GeolocationUtility
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val geolocationProvider: GeolocationProvider =
        GeolocationUtility.getGeolocationProvider(application)

    private val _currentGeolocation = MutableLiveData<Location>()
    internal val currentGeolocation: LiveData<Location> = _currentGeolocation.apply {
        postValue(geolocationProvider.getLocation())
    }

    // TODO: Implement the ViewModel
}