package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import logic.GameManager
import model.RecordLeaderboard
import utilities.Constants
import utilities.Location
import utilities.SharedPreferncesManager

class MenuActivity : AppCompatActivity() {
    private lateinit var menu_BTN_slow: MaterialButton
    private lateinit var menu_BTN_fast: MaterialButton
    private lateinit var menu_BTN_sensor: MaterialButton
    private lateinit var menu_BTN_score: MaterialButton
    private lateinit var menu_LBL_tilt: MaterialTextView
    private lateinit var menu_LBL_buttons: MaterialTextView
    private lateinit var menu_LBL_score: MaterialTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Location.init(this)
        checkLocationAndPermissions()
        setContentView(R.layout.menu)
        findViews()

        initViews()
    }
    private fun findViews()
    {
        menu_BTN_slow=findViewById(R.id.menu_BTN_slow)
        menu_BTN_fast=findViewById(R.id.menu_BTN_fast)
        menu_BTN_sensor=findViewById(R.id.menu_BTN_sensor)
        menu_BTN_score=findViewById(R.id.menu_BTN_score)
        menu_LBL_tilt = findViewById(R.id.menu_LBL_tilt)
        menu_LBL_buttons=findViewById(R.id.menu_LBL_buttons)
        menu_LBL_score=findViewById(R.id.menu_LBL_score)
    }
    private fun initViews()
    {
        menu_BTN_slow.setOnClickListener { view: View -> slowButtonMode() }
        menu_BTN_fast.setOnClickListener { view: View -> fastButtonMode() }
        menu_BTN_sensor.setOnClickListener { view: View -> sensorButtonMode() }
        menu_BTN_score.setOnClickListener { view: View -> menuButtonMode() }
    }
    private fun slowButtonMode()
    {
        val intent= Intent(this,MainActivity::class.java)
        intent.putExtra("speed",500L)
        startActivity(intent)
    }
    private fun fastButtonMode()
    {
        val intent= Intent(this,MainActivity::class.java)
        intent.putExtra("speed",300L)
        startActivity(intent)
    }
    private fun sensorButtonMode()
    {
        val intent= Intent(this,MainActivity::class.java)
        intent.putExtra("mode",1)
        startActivity(intent)
    }
    private fun menuButtonMode()
    {
        val intent= Intent(this,RecordActivity::class.java)
        val recordleaderboard:RecordLeaderboard=SharedPreferncesManager.getInstance().loadSharedPreferences()
        val records: IntArray = recordleaderboard.topRecord.map { it.record }.toIntArray()
        val bundle = Bundle()
        bundle.putIntArray("records",records)
        intent.putExtras(bundle)
        startActivity(intent)
    }
    private fun checkLocationAndPermissions() {
        if (!Location.getInstance().isLocationPermissionGranted()) {
            // Request permission if not granted
            Location.getInstance().requestLocationPermission(this,Constants.LOCATION.LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // Check if location is enabled
            Location.getInstance().requestEnableLocation(this)
        }
    }

}