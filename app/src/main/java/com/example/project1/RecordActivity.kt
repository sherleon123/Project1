package com.example.project1
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.fragments.HighScoreFragment
import com.example.project1.fragments.MapFragment
import interfaces.HighScoreItemClickedCallback

class RecordActivity : AppCompatActivity() {
    private lateinit var main_FRAME_list: FrameLayout

    private lateinit var main_FRAME_map: FrameLayout

    private lateinit var mapFragment: MapFragment

    private lateinit var highScoreFragment: HighScoreFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_leaderboard)

        findViews()
        initViews()
    }

    private fun findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list)
        main_FRAME_map = findViewById(R.id.main_FRAME_map)
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras
        val records= bundle?.getIntArray("records")
        mapFragment = MapFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_FRAME_map, mapFragment)
            .commit()

        highScoreFragment = HighScoreFragment()


        highScoreFragment.arguments=bundle
        highScoreFragment.highScoreItemClicked = object : HighScoreItemClickedCallback{
            override fun highScoreItemClicked(lat: Double, lon: Double) {
                mapFragment.zoom(lat,lon)
            }
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_list ,highScoreFragment )
            .commit()

    }
}