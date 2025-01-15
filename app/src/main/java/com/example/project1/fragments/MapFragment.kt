package com.example.project1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project1.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textview.MaterialTextView
import model.Record
import model.RecordLeaderboard
import utilities.SharedPreferncesManager


class MapFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        findViews(v)
        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun findViews(v: View) {
    }

    fun zoom(lat: Double, lon: Double) {
        val location=LatLng(lat,lon)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
    }
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        var leaderboard: RecordLeaderboard =SharedPreferncesManager.getInstance().loadSharedPreferences()
        var topRecords: MutableList<Record> =leaderboard.topRecord
        topRecords.forEach { scoreData ->
            val position = LatLng(scoreData.lat, scoreData.lon)
            val marker = map.addMarker(
                MarkerOptions()
                    .position(position)
                    .title("Score: ${scoreData.record}"))
        }

        topRecords.firstOrNull()?.let { firstScore ->
            val position = LatLng(firstScore.lat, firstScore.lon)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12f))
        }
    }

}