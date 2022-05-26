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

    private val _currentGeolocationName = MutableLiveData<String>()
    internal val currentGeolocationName: LiveData<String> = _currentGeolocationName

    private val _currentWeatherInfo = MutableLiveData<CurrentWeather>()
    internal val currentWeatherInfo: LiveData<CurrentWeather> = _currentWeatherInfo

    private val _dailyWeatherInfo = MutableLiveData<List<DailyWeather>>()
    internal val dailyWeatherInfo: LiveData<List<DailyWeather>> = _dailyWeatherInfo

    private val _hourlyWeatherInfo = MutableLiveData<List<HourlyWeather>>()
    internal val hourlyWeatherInfo: LiveData<List<HourlyWeather>> = _hourlyWeatherInfo

    private val weatherCache = WeatherPreferences(application)

    internal fun getWeatherData() {

        loadWeatherCache()

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
            Log.i("MainViewModel", "Getting data from Repository!")
            WeatherDataRepository.reverseGeocoding(location.latitude, location.longitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Log.i("MainViewModel", "Reverse geocoded location!")
                        _currentGeolocationName.postValue(it[0].name)
                        weatherCache.saveLocationName(it[0].name)
                    },
                    onError = {
                        _currentGeolocationName.postValue("Geolocation unavailable!")
                        Log.i("MainViewModel", "Failed to reverse geocode location!")
                    }
                )
            WeatherDataRepository.getWeatherData(location.latitude, location.longitude, "metric")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { weathers ->
                        Log.i("MainViewModel", "Got weather response from Repository!")
                        _currentWeatherInfo.postValue(weathers.current)
                        _dailyWeatherInfo.postValue(weathers.daily)
                        _hourlyWeatherInfo.postValue(weathers.hourly)
                        weatherCache.saveWeatherData(weathers)
                    },
                    onError = { Log.i("MainViewModel", "Didn't get weather from Repository!") }
                )
        }
    }

    private fun loadWeatherCache() {
        weatherCache.getLocationName()?.let { _currentGeolocationName.postValue(it) }
        weatherCache.getCurrentWeatherCache()?.let { current ->
            _currentWeatherInfo.postValue(current)
        }
        weatherCache.getDailyWeatherCache()?.let { daily -> _dailyWeatherInfo.postValue(daily) }
        weatherCache.getHourlyWeatherCache()?.let { hourly -> _hourlyWeatherInfo.postValue(hourly) }
    }
}
