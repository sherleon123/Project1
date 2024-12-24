package com.example.project1

import android.os.Bundle
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
import utilities.ImageLoader


class MainActivity : AppCompatActivity() {
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var main_IMG_character: Array<AppCompatImageView>
    private lateinit var main_IMG_sugar: Array<Array<AppCompatImageView>>
    private lateinit var main_BTN_left: MaterialButton
    private lateinit var main_BTN_right: MaterialButton
    private lateinit var gameManager: GameManager
    private lateinit var main_LBL_score: MaterialTextView
    private var startTime: Long = 0
    private var timerOn: Boolean = true
    private lateinit var timerJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        gameManager = GameManager(main_IMG_hearts.size)
        initViews()
        refreshUI()
        startTimer()
    }

    private fun findViews() {
        main_IMG_character= arrayOf(
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
                findViewById(R.id.main_IMG_sugar15)
        ), arrayOf(
                findViewById(R.id.main_IMG_sugar21),
                findViewById(R.id.main_IMG_sugar22),
                findViewById(R.id.main_IMG_sugar23),
                findViewById(R.id.main_IMG_sugar24),
                findViewById(R.id.main_IMG_sugar25)
        ), arrayOf(
                findViewById(R.id.main_IMG_sugar31),
                findViewById(R.id.main_IMG_sugar32),
                findViewById(R.id.main_IMG_sugar33),
                findViewById(R.id.main_IMG_sugar34),
                findViewById(R.id.main_IMG_sugar35)
        ))
        main_BTN_left=findViewById(R.id.main_BTN_left)
        main_BTN_right=findViewById(R.id.main_BTN_right)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )
        main_LBL_score = findViewById(R.id.main_LBL_score)
    }
    private fun initViews() {
        main_BTN_left.setOnClickListener { view: View -> answerClicked(1) }
        main_BTN_right.setOnClickListener { view: View -> answerClicked(-1) }
        main_LBL_score.text = gameManager.score.toString()
        var index=0
        for (i in main_IMG_sugar) {
            for (row in i.indices) {
                ImageLoader
                    .getInstance()
                    .loadImage(
                        "https://media.istockphoto.com/id/1371245517/photo/granulated-white-sugar-in-wooden-bowl-isolated-on-white-background-with-clipping-path.jpg?s=612x612&w=0&k=20&c=fdfohHyZbusyq_67gMgIRbubn5hMZEw4KyhX3_dsh6E=",
                        main_IMG_sugar[index][row]
                    )
            }
            index++
        }
        index=0
        for (i in main_IMG_character)
        {
            ImageLoader
                .getInstance()
                .loadImage(
                    "https://www.shmua.com/wp-content/uploads/2019/12/5921-0325.jpg",
                    main_IMG_character[index]
                )
            index++
        }

        refreshUI()
    }
    private fun answerClicked(expected: Int) {
        gameManager.updateCharacter(expected=expected)

    }

    private fun refreshUI() {
        if (gameManager.isGameOver) { // Lost!
            Log.d("Game Status", "Game Over! " + gameManager.score)
            SignalManager.getInstance().toast("Too much sugar IM DONE!")
            timerOn = false
            timerJob.cancel()
        }

        else { // Ongoing game:
           // main_LBL_score.text = gameManager.score.toString()
            var index=0
            main_LBL_score.text = gameManager.score.toString()
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


            gameManager.updateSugar()
            gameManager.generateSugar()

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
                    delay(Constants.Timer.DELAY)
                    refreshUI()
                }
            }
    }
}