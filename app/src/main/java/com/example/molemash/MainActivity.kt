package com.example.molemash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import com.example.molemash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var gameView: GameView
    private val TAG = "MAIN ACTIVITY"
    private val levelSettings: LevelSettings = LevelSettings(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the gameCanvasContainer
        val gameViewContainer: ViewGroup = binding.gameCanvasContainer

        // Create and add the GameView instance to the gameCanvasContainer
        gameView = GameView(this, levelSettings)
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
            gameView.reset(levelSettings)
            gameView.startMoleAnimation()
            Log.d(TAG, "W: ${gameView.height}")
        }

        val quitButton: Button = binding.quitButton
        quitButton.setOnClickListener {
            gameView.stopMoleAnimation()
        }
    }
}




