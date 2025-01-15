package com.example.project1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logic.GameManager
import utilities.Constants
import utilities.SignalManager
import com.bumptech.glide.Glide
import interfaces.TiltCallback
import utilities.ImageLoader
import utilities.BackgroundMusicPlayer
import utilities.SharedPreferncesManager
import utilities.SingleSoundPlayer
import utilities.TiltDetector
import kotlin.math.log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.Record
import model.RecordLeaderboard
import utilities.Location


class MainActivity : AppCompatActivity() {
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var main_IMG_character: Array<AppCompatImageView>
    private lateinit var main_IMG_sugar: Array<Array<AppCompatImageView>>
    private lateinit var main_IMG_coin: Array<Array<AppCompatImageView>>
    private lateinit var main_BTN_left: MaterialButton
    private lateinit var main_BTN_right: MaterialButton
    private lateinit var gameManager: GameManager
    private lateinit var main_LBL_score: MaterialTextView
    private lateinit var main_LBL_distance: MaterialTextView
    private var startTime: Long = 0
    private var timerOn: Boolean = true
    private var hearts: Int=0
    private lateinit var timerJob: Job
    private lateinit var tiltDetector: TiltDetector
    private var firstspeed: Boolean=true
    private var speed: Long=500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        gameManager = GameManager(main_IMG_hearts.size)
        val mode = intent.getIntExtra("mode",0)
        if (mode==1)
        {
            main_BTN_left.visibility= View.INVISIBLE
            main_BTN_right.visibility= View.INVISIBLE
            initTiltDetector()
        }
        initViews()
        refreshUI()

        speed = intent.getLongExtra("speed",500L)
        startTimer()
    }

    private fun findViews() {
        main_IMG_character= arrayOf(
            findViewById(R.id.main_IMG_charachter5),
            findViewById(R.id.main_IMG_charachter4),
            findViewById(R.id.main_IMG_charachter3),
            findViewById(R.id.main_IMG_charachter2),
            findViewById(R.id.main_IMG_charachter1)

        )
        main_IMG_sugar= arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_sugar11),
                findViewById(R.id.main_IMG_sugar12),
                findViewById(R.id.main_IMG_sugar13),
                findViewById(R.id.main_IMG_sugar14),
                findViewById(R.id.main_IMG_sugar15),
                findViewById(R.id.main_IMG_sugar16),
                findViewById(R.id.main_IMG_sugar17),
                findViewById(R.id.main_IMG_sugar18)
        ), arrayOf(
                findViewById(R.id.main_IMG_sugar21),
                findViewById(R.id.main_IMG_sugar22),
                findViewById(R.id.main_IMG_sugar23),
                findViewById(R.id.main_IMG_sugar24),
                findViewById(R.id.main_IMG_sugar25),
                findViewById(R.id.main_IMG_sugar26),
                findViewById(R.id.main_IMG_sugar27),
                findViewById(R.id.main_IMG_sugar28)
        ), arrayOf(
                findViewById(R.id.main_IMG_sugar31),
                findViewById(R.id.main_IMG_sugar32),
                findViewById(R.id.main_IMG_sugar33),
                findViewById(R.id.main_IMG_sugar34),
                findViewById(R.id.main_IMG_sugar35),
                findViewById(R.id.main_IMG_sugar36),
                findViewById(R.id.main_IMG_sugar37),
                findViewById(R.id.main_IMG_sugar38)

        ),  arrayOf(
                findViewById(R.id.main_IMG_sugar41),
                findViewById(R.id.main_IMG_sugar42),
                findViewById(R.id.main_IMG_sugar43),
                findViewById(R.id.main_IMG_sugar44),
                findViewById(R.id.main_IMG_sugar45),
                findViewById(R.id.main_IMG_sugar46),
                findViewById(R.id.main_IMG_sugar47),
                findViewById(R.id.main_IMG_sugar48)
        ),  arrayOf(
                findViewById(R.id.main_IMG_sugar51),
                findViewById(R.id.main_IMG_sugar52),
                findViewById(R.id.main_IMG_sugar53),
                findViewById(R.id.main_IMG_sugar54),
                findViewById(R.id.main_IMG_sugar55),
                findViewById(R.id.main_IMG_sugar56),
                findViewById(R.id.main_IMG_sugar57),
                findViewById(R.id.main_IMG_sugar58)
        ))
        main_BTN_left=findViewById(R.id.main_BTN_left)
        main_BTN_right=findViewById(R.id.main_BTN_right)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )
        main_LBL_score = findViewById(R.id.main_LBL_score)
        main_LBL_distance=findViewById(R.id.main_LBL_distance)
        main_IMG_coin= arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_coin11),
                findViewById(R.id.main_IMG_coin12),
                findViewById(R.id.main_IMG_coin13),
                findViewById(R.id.main_IMG_coin14),
                findViewById(R.id.main_IMG_coin15),
                findViewById(R.id.main_IMG_coin16),
                findViewById(R.id.main_IMG_coin17),
                findViewById(R.id.main_IMG_coin18)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin21),
                findViewById(R.id.main_IMG_coin22),
                findViewById(R.id.main_IMG_coin23),
                findViewById(R.id.main_IMG_coin24),
                findViewById(R.id.main_IMG_coin25),
                findViewById(R.id.main_IMG_coin26),
                findViewById(R.id.main_IMG_coin27),
                findViewById(R.id.main_IMG_coin28)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin31),
                findViewById(R.id.main_IMG_coin32),
                findViewById(R.id.main_IMG_coin33),
                findViewById(R.id.main_IMG_coin34),
                findViewById(R.id.main_IMG_coin35),
                findViewById(R.id.main_IMG_coin36),
                findViewById(R.id.main_IMG_coin37),
                findViewById(R.id.main_IMG_coin38)

            ),  arrayOf(
                findViewById(R.id.main_IMG_coin41),
                findViewById(R.id.main_IMG_coin42),
                findViewById(R.id.main_IMG_coin43),
                findViewById(R.id.main_IMG_coin44),
                findViewById(R.id.main_IMG_coin45),
                findViewById(R.id.main_IMG_coin46),
                findViewById(R.id.main_IMG_coin47),
                findViewById(R.id.main_IMG_coin48)
            ),  arrayOf(
                findViewById(R.id.main_IMG_coin51),
                findViewById(R.id.main_IMG_coin52),
                findViewById(R.id.main_IMG_coin53),
                findViewById(R.id.main_IMG_coin54),
                findViewById(R.id.main_IMG_coin55),
                findViewById(R.id.main_IMG_coin56),
                findViewById(R.id.main_IMG_coin57),
                findViewById(R.id.main_IMG_coin58)
            ))
    }
    private fun initViews() {
        main_BTN_left.setOnClickListener { view: View -> answerClicked(1) }
        main_BTN_right.setOnClickListener { view: View -> answerClicked(-1) }


        main_LBL_score.text = gameManager.score.toString()
        var indexsugar=0
        for (i in main_IMG_sugar) {
            for (row in i.indices) {
                ImageLoader
                    .getInstance()
                    .loadImage(
                        "https://www.infograf.co.il/ProductsImages/D111489.jpg",
                        main_IMG_sugar[indexsugar][row]
                    )
            }
            indexsugar++
        }
        var indexcharacter=0
        for (i in main_IMG_character)
        {
            ImageLoader
                .getInstance()
                .loadImage(
                    "https://www.shmua.com/wp-content/uploads/2019/12/5921-0325.jpg",
                    main_IMG_character[indexcharacter]
                )
            indexcharacter++
        }
        var indexcoin=0
        for (i in main_IMG_coin) {
            for (row in i.indices) {
                ImageLoader
                    .getInstance()
                    .loadImage(
                        "https://cdn-icons-png.flaticon.com/512/261/261778.png",
                        main_IMG_coin[indexcoin][row]
                    )
            }
            indexcoin++
        }
        refreshUI()
    }
    override fun onResume() {
        super.onResume()
        //tiltDetector.start()
        BackgroundMusicPlayer.getInstance().playMusic()
        val mode = intent.getIntExtra("mode",0)
        if (mode==1)
            tiltDetector.start()
    }

    override fun onPause() {
        super.onPause()
        //tiltDetector.stop()
        BackgroundMusicPlayer.getInstance().pauseMusic()
        val mode = intent.getIntExtra("mode",0)
        if (mode==1)
            tiltDetector.stop()
    }
    private fun answerClicked(expected: Int) {
        gameManager.updateCharacter(expected=expected)

    }

    private fun refreshUI() {
        if (gameManager.isGameOver) { // Lost!
            Log.d("Game Status", "Game Over! " + gameManager.score)
            SignalManager.getInstance().toast("Too much sugar IM DONE!")
            Location.init(this)
            var locationManager = Location.getInstance()
            val location: Pair<Double,Double> = locationManager.getCurrentLocationForMap()
            val record:Record =Record.Builder().record(gameManager.score).lat(location.first).lon(location.second).build()

            var recordleaderboard: RecordLeaderboard =SharedPreferncesManager.getInstance().loadSharedPreferences()
            recordleaderboard.addNewScore(record)
            SharedPreferncesManager.getInstance().saveSharedPreferences(recordleaderboard)

            timerOn = false
            timerJob.cancel()
        }

        else { // Ongoing game:
           // main_LBL_score.text = gameManager.score.toString()
            var index=0
            Log.d("Speed_Check", "$speed")
            main_LBL_score.text = gameManager.score.toString()
            gameManager.addDistance()
            main_LBL_distance.text = (gameManager.distance/10).toString()+"."+(gameManager.distance%10).toString()+"km"
            for (i in gameManager.getArrayCharacters) {
                if (i == 0) {
                    main_IMG_character[index].visibility = View.INVISIBLE
                } else {
                    main_IMG_character[index].visibility = View.VISIBLE
                }
                index++
            }
            for (row in gameManager.getArraySugars.indices)
                for (line in gameManager.getArraySugars[row].indices)
                    if (gameManager.getArraySugars[row][line] == 0) {
                        main_IMG_sugar[row][line].visibility=View.INVISIBLE
                    } else {
                        main_IMG_sugar[row][line].visibility=View.VISIBLE
                    }
            for (row in gameManager.getArrayCoins.indices)
                for (line in gameManager.getArrayCoins[row].indices)
                    if (gameManager.getArrayCoins[row][line] == 0) {
                        main_IMG_coin[row][line].visibility=View.INVISIBLE
                    } else {
                        main_IMG_coin[row][line].visibility=View.VISIBLE
                    }


            gameManager.updateSugar()
            gameManager.generateSugar()

            gameManager.updateCoin()
            gameManager.generateCoin()
            if (gameManager.heartlost!=hearts)
            {
                var ssp: SingleSoundPlayer = SingleSoundPlayer(this)
                ssp.playSound(R.raw.snorting)
                hearts++
            }

            if (gameManager.heartlost != 0) {
                main_IMG_hearts[main_IMG_hearts.size - gameManager.heartlost].visibility =
                    View.INVISIBLE
            }
        }
    }
    private fun startTimer() {
            timerOn = true
            startTime = System.currentTimeMillis()
            timerJob = lifecycleScope.launch {
                while (timerOn) {
                    delay(speed)
                    refreshUI()
                }
            }
    }
    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            context = this,
            tiltCallback = object : TiltCallback {
                override fun tiltLeft() {
                    gameManager.updateCharacter(1)
                }

                override fun tiltRight() {
                    gameManager.updateCharacter(-1)
                }
                override fun tiltSlow() {

                    if (firstspeed)
                    {
                        speed = intent.getLongExtra("speed",500L)
                        firstspeed=false
                    }
                    else if(speed<=600)
                    {
                        speed+=20L
                    }
                }

                override fun tiltFast() {

                    if (firstspeed)
                    {
                        speed = intent.getLongExtra("speed",500L)
                        firstspeed=false
                    }
                    else if(speed>=300)
                    {
                        speed-=20L
                    }
                }

            }
        )
    }

}