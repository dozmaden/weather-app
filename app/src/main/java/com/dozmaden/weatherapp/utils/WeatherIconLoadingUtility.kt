package com.dozmaden.weatherapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object WeatherIconLoadingUtility {
    fun loadImage(context: Context, icon: String, image: ImageView) {
        Glide.with(context)
            .load("https://openweathermap.org/img/wn/$icon@2x.png")
            .centerCrop()
            //                    .placeholder()
            .into(image)
    }
}
