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
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.Collections.emptyList

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

        checkPermissions()

        dailyRecyclerView = binding.dailyWeatherRecyclerView
        dailyRecyclerView.adapter = DayWeatherAdapter(emptyList())

        hourlyRecyclerView = binding.hourlyWeatherRecyclerView
        hourlyRecyclerView.adapter = HourlyWeatherAdapter(emptyList())

        setLocationObserver()
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

    private fun setLocationObserver() {
        viewModel.currentGeolocationName.observe(viewLifecycleOwner) {
            binding.locationName.text = it
        }
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
                // remove first day, because it's a repeat of current
                val adapter = DayWeatherAdapter(it.subList(1, it.size))
                dailyRecyclerView.adapter = adapter
            }
        }
    }

    private fun setHourlyWeatherObserver() {
        viewModel.hourlyWeatherInfo.observe(viewLifecycleOwner) {
            it?.let {
                // display only 24 hours ahead, not 48 (might confuse user otherwise)
                val adapter = HourlyWeatherAdapter(it.subList(0, 23))
                hourlyRecyclerView.adapter = adapter
            }
        }
    }

    private fun checkPermissions() {
        if (!GeolocationPermissionsUtility.hasLocationPermissions(requireContext())) {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "This app requires location permissions!",
                0,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app requires location permissions!",
                0,
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
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        Log.i("MainFragment", "On Resume!")
        checkPermissions()
        viewModel.getWeatherData()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
