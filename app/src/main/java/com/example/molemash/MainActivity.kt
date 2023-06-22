package com.example.molemash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import com.example.molemash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GameView.ScoreListener {
    /*
    * STEP 4 / 5 OBSERVER-PUBLISHER
    * Implement the ScoreListener interface of GameView
    * to get updates to the score.
    * */
    private lateinit var binding: ActivityMainBinding
    private lateinit var gameView: GameView
    private val TAG = "MAIN ACTIVITY"
    private val levelSettings: LevelSettings = LevelSettings(this)
    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the gameCanvasContainer
        val gameViewContainer: ViewGroup = binding.gameCanvasContainer

        // Create and add the GameView instance to the gameCanvasContainer
        gameView = GameView(this, levelSettings)
        gameView.setScoreListener(this) // Step 5/5 OBSERVER-PUBLISHER - Subscribe!
        gameViewContainer.addView(gameView)

        // Get difficulty level
        val level1Button: Button = binding.level1Button
        level1Button.setOnClickListener {
            levelSettings.currentLevel = 1
            // You can perform any additional actions or updates related to level 1 here
        }

        val level2Button: Button = binding.level2Button
        level2Button.setOnClickListener {
            levelSettings.currentLevel = 2
            // You can perform any additional actions or updates related to level 2 here
        }

        val level3Button: Button = binding.level3Button
        level3Button.setOnClickListener {
            levelSettings.currentLevel = 3
            // You can perform any additional actions or updates related to level 3 here
        }

        val startButton: Button = binding.startButton
        startButton.setOnClickListener {
            if (isPlaying) {

            } else {
                isPlaying = true
                gameView.reset(levelSettings)   // Reset the array of Moles in GameView to start over
                gameView.startMoleAnimation()   // Reanimate all moles
                Log.d(TAG, "W: ${gameView.height}")
            }
        }

        val quitButton: Button = binding.quitButton
        quitButton.setOnClickListener {
            isPlaying = false
            gameView.stopMoleAnimation()
        }
    }

    override fun onScoreUpdated(score: Int) {
        /*
         * Step 4/5 OBSERVER-PUBLISHER
         * Implement the callback to handle the score
         * published by GameView after each update.
         * */
        binding.scoreTextView.text = "Score: $score"
    }
}




