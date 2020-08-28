package com.example.weatherapp.fragment

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        googleMap.setOnMapLongClickListener { latLng: LatLng ->
            val markerOptions = MarkerOptions()
            //set the position
            markerOptions.position(latLng)
            //add a title to marker
            markerOptions.title("Lat: ${latLng.latitude} Lon: ${latLng.longitude}")
            //clear previous markers
            googleMap.clear()
            //zoom to the marker
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9.toFloat()))

            googleMap.addMarker(markerOptions)

            googleMap.setOnMarkerClickListener { marker ->
                val cityFragment = CityFragment.newInstanceFromLatLong(latLng.latitude.toString(), latLng.longitude.toString())

                activity!!.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.content, cityFragment)
                    addToBackStack(null)
                    commit()
                }

                true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


}