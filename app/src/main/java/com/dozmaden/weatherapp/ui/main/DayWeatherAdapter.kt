package com.dozmaden.weatherapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dozmaden.weatherapp.R
import com.dozmaden.weatherapp.dto.DayWeather
import java.text.SimpleDateFormat

class DayWeatherAdapter(private val mList: List<DayWeather>) :
    RecyclerView.Adapter<DayWeatherAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_weather, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.dayTemperatureTextView.text =
            holder.dayTemperatureTextView.resources
                .getString(R.string.celcius_temperature)
                .format(mList[position].temp.min.toString())

        holder.dayDescriptionTextView.text = mList[position].weather[0].main

        //        val simpleDate = SimpleDateFormat("dd/MM/YYYY hh:mm:ss")
        val simpleDate = SimpleDateFormat("EEE, MMM d")
        val currentDate = simpleDate.format(mList[position].dt.toLong() * 1000)

        holder.dayTimeTextView.text = currentDate

        Glide.with(holder.dayWeatherImageView.context)
            .load(
                "https://openweathermap.org/img/wn/" + mList[position].weather[0].icon + "@2x.png"
            )
            .centerCrop()
            //                    .placeholder()
            .into(holder.dayWeatherImageView)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val dayTemperatureTextView: TextView = itemView.findViewById(R.id.day_temperature)
        val dayTimeTextView: TextView = itemView.findViewById(R.id.day_time)
        val dayDescriptionTextView: TextView = itemView.findViewById(R.id.day_main_description)
        val dayWeatherImageView: ImageView =
            itemView.findViewById<ImageView?>(R.id.daily_weather_image)
    }
}
