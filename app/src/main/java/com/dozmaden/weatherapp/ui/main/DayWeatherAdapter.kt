package com.dozmaden.weatherapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dozmaden.weatherapp.R
import com.dozmaden.weatherapp.dto.DayWeather

class DayWeatherAdapter(private val mList: List<DayWeather>) :
    RecyclerView.Adapter<DayWeatherAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.dayTemperatureTextView.text = mList[position].temp.day.toString()
        holder.dayDescriptionTextView.text = mList[position].weather[0].main
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val dayTemperatureTextView: TextView = itemView.findViewById(R.id.day_temperature)
        val dayDescriptionTextView: TextView = itemView.findViewById(R.id.day_main_description)
    }
}
