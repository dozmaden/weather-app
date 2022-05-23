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
import com.dozmaden.weatherapp.databinding.FragmentMainBinding
import com.dozmaden.weatherapp.utils.GeolocationPermissionsUtility
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        if (!GeolocationPermissionsUtility.hasLocationPermissions(requireContext())) {
            requestPermissions()
        }

        setGeolocationObserver()
        setWeatherObserver()

        //        getCurrentWeather()
        //        getFutureWeather()

        viewModel.getCurrentWeather()

        return binding.root
    }

    private fun setGeolocationObserver() {
        viewModel.currentGeolocation.observe(viewLifecycleOwner) {
            it?.let {
                Log.i("MainFragment", "Geolocation update!")
                binding.weather.text = it.latitude.toString()
            }
        }
    }

    private fun setWeatherObserver() {
        viewModel.currentWeatherInfo.observe(viewLifecycleOwner) {
            it?.let { binding.weather.text = it.current.weather[0].description }
        }
    }

    //    override fun onPause() {
    //        Log.i("MainFragment", "On Pause!")
    //        viewModel.getCurrentWeather()
    //        super.onPause()
    //    }

    override fun onResume() {
        Log.i("MainFragment", "On Resume!")
        viewModel.getCurrentWeather()
        super.onResume()
    }
    //

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
