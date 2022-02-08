package com.example.demoapps.fragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
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


class GoogleMapFragment : Fragment() {
    internal var mFusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var mcurrentLocations:Location
    private lateinit var mMap:GoogleMap
    private val callback = OnMapReadyCallback() { googleMap ->
       mFusedLocationClient=LocationServices.getFusedLocationProviderClient(requireContext())
        setClick(googleMap)
    }

    private fun setClick(googleMap: GoogleMap) {
        currentLocation(googleMap)
    }

    private fun currentLocation(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap=googleMap
          mMap!!.isMyLocationEnabled = true
            mFusedLocationClient!!.lastLocation.addOnSuccessListener(requireActivity()) {  location  ->
                if (location != null){
                    mcurrentLocations= location
                    val latLng =LatLng(location.latitude,location.longitude)
                    placeMarker(latLng)
                    mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20f))
                }
            }
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

    private fun placeMarker(latLng: LatLng) {
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        mMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        mMap?.addMarker(markerOptions)
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