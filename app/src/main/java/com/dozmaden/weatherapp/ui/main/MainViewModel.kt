package com.dozmaden.weatherapp.ui.main

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dozmaden.weatherapp.dto.CurrentWeather
import com.dozmaden.weatherapp.dto.DailyWeather
import com.dozmaden.weatherapp.dto.HourlyWeather
import com.dozmaden.weatherapp.geolocation.GeolocationProviderFactory
import com.dozmaden.weatherapp.preferences.WeatherPreferences
import com.dozmaden.weatherapp.repository.WeatherDataRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _currentLocationName = MutableLiveData<String>()
    internal val currentLocationName: LiveData<String> = _currentLocationName

    private val _currentWeatherInfo = MutableLiveData<CurrentWeather>()
    internal val currentWeatherInfo: LiveData<CurrentWeather> = _currentWeatherInfo

    private val _dailyWeatherInfo = MutableLiveData<List<DailyWeather>>()
    internal val dailyWeatherInfo: LiveData<List<DailyWeather>> = _dailyWeatherInfo

    private val _hourlyWeatherInfo = MutableLiveData<List<HourlyWeather>>()
    internal val hourlyWeatherInfo: LiveData<List<HourlyWeather>> = _hourlyWeatherInfo

    private val weatherCache = WeatherPreferences(application)

    internal fun setWeatherInfo() {
        if (cacheIsEmpty()) {
            getWeatherInfoByLocation()
        } else {
            loadWeatherCache()
        }
    }

    internal fun searchLocations(query: String): List<String> {
        return getLocationSuggestions(query)
    }

    internal fun setLocation(query: String) {
        setLocationFromSuggestions(query)
    }

    private fun cacheIsEmpty(): Boolean {
        return weatherCache.getLocationName() == null
    }

    private fun getWeatherInfoByLocation() {
        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    getApplication(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Request permissions!
            return
        }

        val geolocationProvider =
            GeolocationProviderFactory.getGeolocationProvider(getApplication())
        val location = geolocationProvider.getLocation()

        location?.let {
            getLocationName(location.latitude, location.longitude)
            getWeatherData(location.latitude, location.longitude)
            Log.i("MainViewModel", "Getting weather data by location from Repository!")
        }
    }

    private fun loadWeatherCache() {
        weatherCache.getLocationName()?.let { _currentLocationName.postValue(it) }
        weatherCache.getCurrentWeatherCache()?.let { current ->
            _currentWeatherInfo.postValue(current)
        }
        weatherCache.getDailyWeatherCache()?.let { daily -> _dailyWeatherInfo.postValue(daily) }
        weatherCache.getHourlyWeatherCache()?.let { hourly -> _hourlyWeatherInfo.postValue(hourly) }
    }

    private fun getWeatherData(latitude: Double, longitude: Double) {
        WeatherDataRepository.getWeatherData(latitude, longitude, "metric")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { weatherForecast ->
                    _currentWeatherInfo.postValue(weatherForecast.current)
                    _hourlyWeatherInfo.postValue(weatherForecast.hourly)
                    _dailyWeatherInfo.postValue(weatherForecast.daily)
                    weatherCache.saveWeatherData(weatherForecast)
                    Log.i("MainViewModel", "Got weather response from Repository!")
                },
                onError = { Log.i("MainViewModel", "Didn't get weather from Repository!") }
            )
    }

    private fun getLocationName(latitude: Double, longitude: Double) {
        WeatherDataRepository.getLocationName(latitude, longitude)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.isNotEmpty()) {
                        _currentLocationName.postValue(it[0].name)
                        weatherCache.saveLocationName(it[0].name)
                    }
                    Log.i("MainViewModel", "Reverse geocoded location!")
                },
                onError = {
                    _currentLocationName.postValue("Location unavailable!")
                    Log.i("MainViewModel", "Failed to reverse geocode location!")
                }
            )
    }

    private fun getLocationSuggestions(query: String): List<String> {
        val suggestedLocations = mutableListOf<String>()
        WeatherDataRepository.searchLocation(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { locations ->
                    for (l in locations) {
                        suggestedLocations.add(l.name)
                    }
                    Log.i("MainViewModel", "Got suggested locations from Repository!")
                },
                onError = {
                    Log.i("MainViewModel", "Didn't get suggested locations from Repository!")
                }
            )
        return suggestedLocations
    }

    private fun setLocationFromSuggestions(query: String) {
        WeatherDataRepository.searchLocation(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { locations ->
                    if (locations.isNotEmpty()) {
                        locations[0].name.let {
                            _currentLocationName.postValue(it)
                            weatherCache.saveLocationName(it)
                        }
                        getWeatherData(locations[0].lat, locations[0].lon)
                    }
                    Log.i("MainViewModel", "Got direct geocoding response from Repository!")
                },
                onError = {
                    Log.i("MainViewModel", "Didn't get geocoding response from Repository!")
                }
            )
    }
}
