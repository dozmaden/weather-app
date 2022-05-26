package com.dozmaden.weatherapp.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dozmaden.weatherapp.R
import com.dozmaden.weatherapp.dto.DailyWeather
import com.dozmaden.weatherapp.utils.WeatherIconLoadingUtility
import java.text.SimpleDateFormat

class DayWeatherAdapter(private val mList: List<DailyWeather>) :
    RecyclerView.Adapter<DayWeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_weather, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.dayTemperatureTextView.text =
            holder.dayTemperatureTextView.resources
                .getString(R.string.celcius_temperature)
                .format(mList[position].temp.min.toString().substringBefore("."))

        holder.dayDescriptionTextView.text = mList[position].weather[0].main

        holder.dayTimeTextView.text =
            SimpleDateFormat("EEE, MMM d").format(mList[position].dt.toLong() * 1000)

        WeatherIconLoadingUtility.loadImage(
            holder.dayWeatherImageView.context,
            mList[position].weather[0].icon,
            holder.dayWeatherImageView
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dayTemperatureTextView: TextView = itemView.findViewById(R.id.day_temperature)
        val dayTimeTextView: TextView = itemView.findViewById(R.id.day_time)
        val dayDescriptionTextView: TextView = itemView.findViewById(R.id.day_main_description)
        val dayWeatherImageView: ImageView =
            itemView.findViewById(R.id.daily_weather_image)
    }
}
