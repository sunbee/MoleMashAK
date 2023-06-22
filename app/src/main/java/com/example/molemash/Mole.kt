package com.example.molemash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.view.MotionEvent
import android.view.View

class Mole(private val context: Context, private val gameView: View, private val levelSettings: LevelSettings) {
    private val moleBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mole)
    private var x: Float = 0f
    private var y: Float = 0f
    private var isEnabled: Boolean = true

    private lateinit var gameScope: CoroutineScope
    private val TAG = "MOLE SPRITE"

    fun startAnimation() {
        gameScope = CoroutineScope(Dispatchers.Main)

        gameScope.launch {
            while (true) {
                updatePosition(gameView.width, gameView.height)
                gameView.invalidate() // Request redraw

                delay(levelSettings.getMoleSpeed(levelSettings.currentLevel).toLong()) // Delay between animations
            }
        }
    }

    fun stopAnimation() {
        gameScope.cancel()
    }

    private fun updatePosition(canvasWidth: Int, canvasHeight: Int) {
        x = (0 until canvasWidth - moleBitmap.width).random().toFloat()
        y = (0 until canvasHeight - moleBitmap.height).random().toFloat()
        isEnabled = true // Enable the mole when updating position
    }

    fun draw(canvas: Canvas) {
        if (isEnabled) {
            canvas.drawBitmap(moleBitmap, x, y, null)
        }
    }

    fun handleTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && isEnabled) {
            val touchX = event.x
            val touchY = event.y
            if (isTouched(touchX, touchY)) {
                isEnabled = false   // Disable the mole when touched
                gameScope.cancel()  // Cancel the animation coroutine
                return true // Mole was touched
            }
        }
        return false // Mole was not touched
    }

    private fun isTouched(touchX: Float, touchY: Float): Boolean {
        val moleRight = x + moleBitmap.width
        val moleBottom = y + moleBitmap.height
        return (touchX in x..moleRight) && (touchY in y..moleBottom)
    }

    fun getPosition(): String {
        return "X: ${x} Y: ${y}, W: ${moleBitmap.height}, H: ${moleBitmap.width}"
    }
}
