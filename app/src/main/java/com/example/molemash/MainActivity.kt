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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the gameCanvasContainer
        val gameViewContainer: ViewGroup = binding.gameCanvasContainer

        // Create and add the GameView instance to the gameCanvasContainer
        val gameView = GameView(this)
        gameViewContainer.addView(gameView)

        val mole = Mole(this, gameView)

        val startButton: Button = binding.startButton
        startButton.setOnClickListener {
            gameView.startMoleAnimation()
            Log.d(TAG, "W: ${gameView.height}")
        }

        val quitButton: Button = binding.quitButton
        quitButton.setOnClickListener {
            gameView.stopMoleAnimation()
        }
    }
}




