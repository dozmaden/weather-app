package com.dozmaden.weatherapp.ui.main

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dozmaden.weatherapp.dto.CurrentWeather
import com.dozmaden.weatherapp.dto.DayWeather
import com.dozmaden.weatherapp.dto.HourWeather
import com.dozmaden.weatherapp.geolocation.GeolocationProvider
import com.dozmaden.weatherapp.geolocation.GeolocationProviderFactory
import com.dozmaden.weatherapp.preferences.WeatherPreferences
import com.dozmaden.weatherapp.repository.WeatherDataRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val geolocationProvider: GeolocationProvider =
        GeolocationProviderFactory.getGeolocationProvider(application)

    private val _currentGeolocation = MutableLiveData<Location?>()
    internal val currentGeolocation: LiveData<Location?> = _currentGeolocation

    private val _currentWeatherInfo = MutableLiveData<CurrentWeather>()
    internal val currentWeatherInfo: LiveData<CurrentWeather> = _currentWeatherInfo

    private val _dailyWeatherInfo = MutableLiveData<List<DayWeather>>()
    internal val dailyWeatherInfo: LiveData<List<DayWeather>> = _dailyWeatherInfo

    private val _hourlyWeatherInfo = MutableLiveData<List<HourWeather>>()
    internal val hourlyWeatherInfo: LiveData<List<HourWeather>> = _hourlyWeatherInfo

    private val weatherCache = WeatherPreferences(application)

    internal fun getWeatherData() {
        val lat = 55.7558
        val lon = 37.6173

        Log.i("MainViewModel", "Getting data from Repository!")
        WeatherDataRepository.getWeatherData(lat, lon, "metric")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { weathers ->
                    Log.i("MainViewModel", "Got weather response from Repository!")
                    _currentWeatherInfo.postValue(weathers.current)
                    _dailyWeatherInfo.postValue(weathers.daily)
                    _hourlyWeatherInfo.postValue(weathers.hourly)
                    weatherCache.saveWeatherData(weathers)
                },
                onError = {
                    Log.i("MainViewModel", "Didn't get weather from Repository!")
                    weatherCache.getCurrentWeatherCache()?.let { _currentWeatherInfo.postValue(it) }
                    weatherCache.getDailyWeatherCache()?.let { _dailyWeatherInfo.postValue(it) }
                    weatherCache.getHourlyWeatherCache()?.let { _hourlyWeatherInfo.postValue(it) }
                }
            )
    }
}
