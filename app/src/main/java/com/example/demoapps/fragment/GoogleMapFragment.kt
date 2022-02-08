package com.example.demoapps.fragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*


class GoogleMapFragment : Fragment() {
    internal var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mcurrentLocations:List<Address>?=null
    private var mMap: GoogleMap? = null
    private val callback = OnMapReadyCallback { _ ->
       mFusedLocationClient=LocationServices.getFusedLocationProviderClient(requireContext())
        val latLng = LatLng(mcurrentLocations!!.get(0).latitude, mcurrentLocations!!.get(0).longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        mMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        mMap?.addMarker(markerOptions)
        setClick()
    }

    private fun setClick() {
        currentLocation()
    }

    private fun currentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mFusedLocationClient!!.getLastLocation()
                .addOnCompleteListener( { task ->
                    val currentLocation = task.result
                    if (currentLocation != null) {
                        try {
                            val geocoder = Geocoder(requireContext(), Locale.getDefault())
                            mcurrentLocations = geocoder.getFromLocation(
                                currentLocation.latitude,
                                currentLocation.longitude,
                                1
                            )
                            markLocation()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                })
        } else {
            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    private fun markLocation() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_google_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
}