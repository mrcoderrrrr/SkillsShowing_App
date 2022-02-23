package com.example.demoapps.fragment

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.activity.MainActivity
import com.example.demoapps.databinding.FragmentGoogleMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*


class GoogleMapFragment : Fragment() {
    private lateinit var dataBinding: FragmentGoogleMapBinding
    internal var mFusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var mcurrentLocations: Location
    private lateinit var mMap: GoogleMap
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val callback = OnMapReadyCallback() { googleMap ->
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        currentLocation(googleMap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()


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
            mMap = googleMap
            mMap.isMyLocationEnabled = true
            mFusedLocationClient!!.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    mcurrentLocations = location
                    val latLng = LatLng(location.latitude, location.longitude)
                    placeMarker(latLng)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))
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
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        mMap.addMarker(markerOptions)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_google_map, container, false)
        val view = dataBinding.root
        Places.initialize(requireContext(), getString(R.string.map_api_key))
        var placesClient:PlacesClient = Places.createClient(requireContext())
        val backPressedCallback=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent=Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
        setClick()
        return view
    }

    private fun setClick() {
        dataBinding.btnSearch.setOnClickListener {
            startAutocompleteactivity()
        }

    }


    private fun startAutocompleteactivity() {
        val intent=Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
        Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG))
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .setCountries(Arrays.asList("IN"))
            .build(requireContext())
        startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    val markerOptions = MarkerOptions().position(place.latLng).title("I am here!")
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(place.latLng))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 20f))
                    mMap.addMarker(markerOptions)
                }
            }
            AutocompleteActivity.RESULT_ERROR -> {
                data?.let {
                    val status = Autocomplete.getStatusFromIntent(data)
                    Log.i(TAG, status.statusMessage.toString())
                }
            }
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.fm_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
}