package ie.wit.birdapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import ie.wit.birdapp.R
import ie.wit.birdapp.helpers.getAllBirds
import ie.wit.birdapp.helpers.setMapMarker
import ie.wit.birdapp.helpers.trackLocation
import ie.wit.birdapp.main.BirdApp


/**
 * A simple [Fragment] subclass.
 * Use the [MapsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapsFragment : SupportMapFragment(), OnMapReadyCallback {

    lateinit var app: BirdApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BirdApp
        getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        app.mMap = googleMap
        app.mMap.isMyLocationEnabled = true
        app.mMap.uiSettings.isZoomControlsEnabled = true
        app.mMap.uiSettings.setAllGesturesEnabled(true)
        app.mMap.clear()
        trackLocation(app)
        setMapMarker(app)
        getAllBirds(app)
    }
}