package com.example.molemash

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View

class GameView(context: Context) : View(context) {
    private val mole: Mole = Mole(context, this)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the background image

        // Draw the mole
        mole.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mole.handleTouchEvent(event)) {
            // Mole was touched, handle the result
            // ...
            return true
        }
        return super.onTouchEvent(event)
    }

    fun startMoleAnimation() {
        mole.startAnimation()
        invalidate() // Request initial draw
    }

    fun stopMoleAnimation() {
        mole.stopAnimation()
    }
}
