package com.erbe.happyplaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erbe.happyplaces.model.HappyPlaceModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var mHappyPlaceDetail : HappyPlaceModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            mHappyPlaceDetail = intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS)
        }

        if (mHappyPlaceDetail != null) {
            setSupportActionBar(map_toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = mHappyPlaceDetail!!.title

            map_toolbar.setNavigationOnClickListener { onBackPressed() }

            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        val happyPlacePosition = LatLng(mHappyPlaceDetail!!.latitude, mHappyPlaceDetail!!.longitude)
        mMap = googleMap

        mMap.addMarker(MarkerOptions().position(happyPlacePosition).title("Marker in " + mHappyPlaceDetail!!.location))
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(happyPlacePosition, 15f)
        mMap.animateCamera(newLatLngZoom)
    }
}