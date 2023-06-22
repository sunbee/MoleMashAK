package com.example.molemash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View

class GameView(context: Context, levelSettings: LevelSettings) : View(context) {
    private var numberMoles: Int = levelSettings.getMolesCount(levelSettings.currentLevel);
    private var molesArray: Array<Mole> = Array(numberMoles) { Mole(context, this)}
    private val canvasBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mole_hole)
    private val TAG: String = "CANVAS VIEW"

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the background image
        val srcRect = Rect(0, 0, canvasBitmap.width, canvasBitmap.height)
        val dstRect = Rect(0, 0, canvas.width, canvas.height)
        canvas.drawBitmap(canvasBitmap, srcRect, dstRect, null)
        // Draw the mole
        for (mole in molesArray) {
            mole.draw(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        for (mole in molesArray) {
            if (mole.handleTouchEvent(event)) {
                // Mole was touched, handle the result
                // ...
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun startMoleAnimation() {
        for (mole in molesArray) {
            mole.startAnimation()
        }
        invalidate() // Request initial draw
    }

    fun stopMoleAnimation() {
        for (mole in molesArray) {
            mole.stopAnimation()
        }
    }
    fun reset(levelSettings: LevelSettings) {
        numberMoles = levelSettings.getMolesCount(levelSettings.currentLevel)
        molesArray = Array(numberMoles) { Mole(context, this)}
    }

}
