package com.example.project1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import com.example.project1.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import interfaces.HighScoreItemClickedCallback
import model.Record
import utilities.SharedPreferncesManager

class HighScoreFragment : Fragment() {
    private lateinit var highScore_Buttons: Array<MaterialButton>

    var highScoreItemClicked: HighScoreItemClickedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_high_score, container, false)

        findViews(v)
        initViews(v)
        val myValue = arguments?.getIntArray("records")
        var index : Int=0
        if (myValue != null) {
            for (i in myValue)
            {

                highScore_Buttons[index].text="score "+(index+1)+": "+i
                index++
            }
        }
        index=0
        for (i in highScore_Buttons)
        {

            if (i.text.equals("score "+(index+1)+": "))
            {
                highScore_Buttons[index].visibility=INVISIBLE
            }
            index++
        }

        return v
    }


    private fun initViews(v: View) {
        var records: MutableList<Record> = SharedPreferncesManager.getInstance().loadSharedPreferences().topRecord
        var lat=0.0
        var lon=0.0
        highScore_Buttons[0].setOnClickListener{ v: View ->
            lat=records[0].lat
            lon=records[0].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[1].setOnClickListener{ v: View ->
            lat=records[1].lat
            lon=records[1].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[2].setOnClickListener{ v: View ->
            lat=records[2].lat
            lon=records[2].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[3].setOnClickListener{ v: View ->
            lat=records[3].lat
            lon=records[3].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[4].setOnClickListener{ v: View ->
            lat=records[4].lat
            lon=records[4].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[5].setOnClickListener{ v: View ->
            lat=records[5].lat
            lon=records[5].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[6].setOnClickListener{ v: View ->
            lat=records[6].lat
            lon=records[6].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[7].setOnClickListener{ v: View ->
            lat=records[7].lat
            lon=records[7].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[8].setOnClickListener{ v: View ->
            lat=records[8].lat
            lon=records[8].lon
            itemClicked(lat, lon)
        }
        highScore_Buttons[9].setOnClickListener{ v: View ->
            lat=records[9].lat
            lon=records[9].lon
            itemClicked(lat, lon)
        }


    }

    private fun itemClicked(lat: Double, lon: Double) {
        highScoreItemClicked?.highScoreItemClicked(lat,lon)
    }

    private fun findViews(v: View) {
        highScore_Buttons= arrayOf(
            v.findViewById(R.id.highScore_BTN_score1),
            v.findViewById(R.id.highScore_BTN_score2),
            v.findViewById(R.id.highScore_BTN_score3),
            v.findViewById(R.id.highScore_BTN_score4),
            v.findViewById(R.id.highScore_BTN_score5),
            v.findViewById(R.id.highScore_BTN_score6),
            v.findViewById(R.id.highScore_BTN_score7),
            v.findViewById(R.id.highScore_BTN_score8),
            v.findViewById(R.id.highScore_BTN_score9),
            v.findViewById(R.id.highScore_BTN_score10)
        )

    }
}