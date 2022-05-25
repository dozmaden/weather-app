package com.dozmaden.weatherapp.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dozmaden.weatherapp.R
import com.dozmaden.weatherapp.dto.HourlyWeather
import com.dozmaden.weatherapp.utils.WeatherIconLoadingUtility
import java.text.SimpleDateFormat

class HourlyWeatherAdapter(private val mList: List<HourlyWeather>) :
    RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hourly_weather, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.hourlyTimeTextView.text =
            SimpleDateFormat("HH aa").format(mList[position].dt.toLong() * 1000)

        holder.hourlyTemperatureTextView.text =
            holder.hourlyTemperatureTextView.resources
                .getString(R.string.celcius_temperature)
                .format(mList[position].temp.toString().substringBefore("."))

        holder.hourlyDescriptionTextView.text = mList[position].weather[0].main

        WeatherIconLoadingUtility.loadImage(
            holder.hourlyWeatherImageView.context,
            mList[position].weather[0].icon,
            holder.hourlyWeatherImageView
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val hourlyTimeTextView: TextView = itemView.findViewById(R.id.hourly_time)
        val hourlyTemperatureTextView: TextView = itemView.findViewById(R.id.hourly_temperature)
        val hourlyDescriptionTextView: TextView =
            itemView.findViewById(R.id.hourly_main_description)
        val hourlyWeatherImageView: ImageView = itemView.findViewById(R.id.hourly_weather_image)
    }
}
