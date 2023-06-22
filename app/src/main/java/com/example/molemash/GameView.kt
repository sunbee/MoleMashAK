package com.example.molemash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

class GameView(context: Context) : View(context) {
    private val mole: Mole = Mole(context, this)
    private val numberMoles: Int = 15
    private val molesArray: Array<Mole> = Array(numberMoles) { Mole(context, this)}
    private val canvasBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mole_hole)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the background image
        val srcRect = Rect(0, 0, canvasBitmap.width, canvasBitmap.height)
        val dstRect = Rect(0, 0, canvas.width, canvas.height)
        canvas.drawBitmap(canvasBitmap, srcRect, dstRect, null)
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
