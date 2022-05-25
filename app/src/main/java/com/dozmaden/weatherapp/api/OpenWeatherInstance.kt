package com.dozmaden.weatherapp.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherInstance {
    private val logInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val httpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(logInterceptor)
            .addInterceptor(OpenWeatherApiKeyInterceptor())
            .build()

    private val retrofit =
        Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    val OPEN_WEATHER_API: OpenWeatherApi = retrofit.create(OpenWeatherApi::class.java)
}
