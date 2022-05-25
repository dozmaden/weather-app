package com.dozmaden.weatherapp.ui.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dozmaden.weatherapp.R
import com.dozmaden.weatherapp.databinding.FragmentMainBinding
import com.dozmaden.weatherapp.utils.GeolocationPermissionsUtility
import java.util.Collections.emptyList
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var dailyRecyclerView: RecyclerView
    private lateinit var hourlyRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        dailyRecyclerView = binding.dailyWeatherRecyclerView
        dailyRecyclerView.adapter = DayWeatherAdapter(emptyList())

        hourlyRecyclerView = binding.hourlyWeatherRecyclerView
        hourlyRecyclerView.adapter = HourlyWeatherAdapter(emptyList())

        if (!GeolocationPermissionsUtility.hasLocationPermissions(requireContext())) {
            requestPermissions()
        }

        setCurrentWeatherObserver()
        setDailyWeatherObserver()
        setHourlyWeatherObserver()

        viewModel.getWeatherData()

        dailyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val horizontalLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        hourlyRecyclerView.layoutManager = horizontalLayoutManager

        return binding.root
    }

    private fun setCurrentWeatherObserver() {
        viewModel.currentWeatherInfo.observe(viewLifecycleOwner) {
            it.let {
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + it.weather[0].icon + "@2x.png")
                    .centerCrop()
                    //                    .placeholder()
                    .into(binding.currentWeatherImage)

                binding.currentTemperature.text =
                    resources.getString(R.string.celcius_temperature).format(it.temp.toString())

                binding.currentFeelslike.text =
                    resources
                        .getString(R.string.feels_like_celcius_temperature)
                        .format(it.feels_like.toString())

                binding.currentMainDescription.text = it.weather[0].main
                binding.currentAdditionalDescription.text = it.weather[0].description

                binding.currentClouds.text =
                    resources.getString(R.string.cloudiness).format(it.clouds.toString())

                binding.currentHumidity.text =
                    resources.getString(R.string.humidity).format(it.humidity.toString())

                binding.currentVisibility.text =
                    resources.getString(R.string.visibility).format(it.visibility.toString())

                binding.currentWindSpeed.text =
                    resources.getString(R.string.windspeed).format(it.wind_speed.toString())
            }
        }
    }

    private fun setDailyWeatherObserver() {
        viewModel.dailyWeatherInfo.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = DayWeatherAdapter(it)
                dailyRecyclerView.adapter = adapter
            }
        }
    }

    private fun setHourlyWeatherObserver() {
        viewModel.hourlyWeatherInfo.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = HourlyWeatherAdapter(it)
                hourlyRecyclerView.adapter = adapter
            }
        }
    }

    //    override fun onPause() {
    //        Log.i("MainFragment", "On Pause!")
    //        viewModel.getCurrentWeather()
    //        super.onPause()
    //    }

    override fun onResume() {
        Log.i("MainFragment", "On Resume!")
        viewModel.getWeatherData()
        super.onResume()
    }

    //        override fun onStart() {
    //            Log.i("MainFragment", "On Start!")
    //            viewModel.getCurrentWeather()
    //            super.onStart()
    //        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val REQUEST_CODE_LOCATION_PERMISSION = 0

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions!",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions!",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
