package com.dozmaden.weatherapp.ui.main

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dozmaden.weatherapp.dto.Weathers
import com.dozmaden.weatherapp.geolocation.GeolocationProvider
import com.dozmaden.weatherapp.geolocation.GeolocationProviderFactory
import com.dozmaden.weatherapp.repository.WeatherInfoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val geolocationProvider: GeolocationProvider =
        GeolocationProviderFactory.getGeolocationProvider(application)

    private val _currentGeolocation = MutableLiveData<Location?>()
    internal val currentGeolocation: LiveData<Location?> = _currentGeolocation

    private val _currentWeatherInfo = MutableLiveData<Weathers>()
    internal val currentWeatherInfo: LiveData<Weathers> = _currentWeatherInfo

    fun getCurrentWeather() {

        //        val currentLocation = geolocationProvider.getLocation()
        //
        //        if (currentLocation != null) {
        //            Log.i("MainViewModel", "Geolocation is not null!")
        //            _currentGeolocation.postValue(currentLocation)
        //        } else {
        //            _currentGeolocation.postValue(currentLocation)
        //            Log.i("MainViewModel", "Geolocation is null!")
        //        }

        val lat = 55.7558
        val lon = 37.6173

        //        currentGeolocation.value?.let {
        Log.i("MainViewModel", "Trying to call API!")
        WeatherInfoRepository.getWeatherInfo(lat, lon, "metric")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { weathers ->
                    Log.i("MainViewModel", "Getting weather from API!")
                    _currentWeatherInfo.postValue(weathers)
                },
                onError = { Log.i("MainViewModel", "Didn't get weather from API!") }
            )
        //        }
    }

    // TODO: Implement the ViewModel
}
