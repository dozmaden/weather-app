package com.dozmaden.weatherapp.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val ApiKeyOpenWeather = "e9e490db954584052230aaaf3f695002"

class OpenWeatherApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalUrl: HttpUrl = original.url
        val url = originalUrl.newBuilder().addQueryParameter("appid", ApiKeyOpenWeather).build()
        val requestBuilder: Request.Builder = original.newBuilder().url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
