package com.dozmaden.weatherapp.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.dozmaden.weatherapp.dto.CurrentWeather
import com.dozmaden.weatherapp.dto.DailyWeather
import com.dozmaden.weatherapp.dto.HourlyWeather
import com.dozmaden.weatherapp.dto.WeatherData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class WeatherPreferences(context: Context) {

    companion object {
        const val SELECTED_PREFS_NAME = "weather_prefs"
        const val KEY_CURRENT_WEATHER = "key_weather_current"
        const val KEY_DAILY_WEATHER = "key_weather_daily"
        const val KEY_HOURLY_WEATHER = "key_weather_hourly"
        const val KEY_LOCATION = "key_location_name"
    }

    private val gson = Gson()

    private val preferences =
        context.getSharedPreferences(SELECTED_PREFS_NAME, Context.MODE_PRIVATE)

    private val preferencesEditor: SharedPreferences.Editor = preferences.edit()

    fun saveWeatherData(weatherData: WeatherData) {
        Log.i("WeatherPreference", "Caching weather data!")
        preferencesEditor.putString(KEY_CURRENT_WEATHER, gson.toJson(weatherData.current))
        preferencesEditor.putString(KEY_DAILY_WEATHER, gson.toJson(weatherData.daily))
        preferencesEditor.putString(KEY_HOURLY_WEATHER, gson.toJson(weatherData.hourly))
        preferencesEditor.commit()
    }

    fun saveLocationName(location: String) {
        Log.i("WeatherPreference", "Caching location name!")
        preferencesEditor.putString(KEY_LOCATION, location)
        preferencesEditor.commit()
    }

    fun getCurrentWeatherCache(): CurrentWeather? {
        val json: String? = preferences.getString(KEY_CURRENT_WEATHER, null)
        val type: Type = object : TypeToken<CurrentWeather>() {}.type
        return gson.fromJson(json, type)
    }

    fun getDailyWeatherCache(): List<DailyWeather>? {
        val json: String? = preferences.getString(KEY_DAILY_WEATHER, null)
        val type: Type = object : TypeToken<List<DailyWeather>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getHourlyWeatherCache(): List<HourlyWeather>? {
        val json: String? = preferences.getString(KEY_HOURLY_WEATHER, null)
        val type: Type = object : TypeToken<List<HourlyWeather>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getLocationName(): String? {
        return preferences.getString(KEY_LOCATION, null)
    }
}
