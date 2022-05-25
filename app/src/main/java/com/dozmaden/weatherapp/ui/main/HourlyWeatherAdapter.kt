package com.dozmaden.weatherapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dozmaden.weatherapp.R
import com.dozmaden.weatherapp.dto.HourWeather
import java.text.SimpleDateFormat

class HourlyWeatherAdapter(private val mList: List<HourWeather>) :
    RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hourly_weather, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class

        //        val simpleDate = SimpleDateFormat("dd/MM/YYYY hh:mm:ss")
        val simpleDate = SimpleDateFormat("HH aa")
        val currentDate = simpleDate.format(mList[position].dt * 1000)

        holder.hourlyTimeTextView.text = currentDate

        holder.hourlyTemperatureTextView.text =
            holder.hourlyTemperatureTextView.resources
                .getString(R.string.celcius_temperature)
                .format(mList[position].temp.toString())

        holder.hourlyDescriptionTextView.text = mList[position].weather[0].main
        Glide.with(holder.hourlyWeatherImageView.context)
            .load(
                "https://openweathermap.org/img/wn/" + mList[position].weather[0].icon + "@2x.png"
            )
            .centerCrop()
            //                    .placeholder()
            .into(holder.hourlyWeatherImageView)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //        val imageView: ImageView = i/temView.findViewById(R.id.imageview)
        val hourlyTimeTextView: TextView = itemView.findViewById(R.id.hourly_time)
        val hourlyTemperatureTextView: TextView = itemView.findViewById(R.id.hourly_temperature)
        val hourlyDescriptionTextView: TextView =
            itemView.findViewById(R.id.hourly_main_description)
        val hourlyWeatherImageView: ImageView =
            itemView.findViewById<ImageView?>(R.id.hourly_weather_image)
    }
}
