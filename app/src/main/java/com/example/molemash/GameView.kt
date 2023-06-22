package com.example.molemash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.media.MediaPlayer
import android.util.Log
import android.view.MotionEvent
import android.view.View

class GameView(context: Context, levelSettings: LevelSettings) : View(context) {
    private var numberMoles: Int = levelSettings.getMolesCount(levelSettings.currentLevel);
    private var molesArray: Array<Mole> = Array(numberMoles) { Mole(context, this, levelSettings)}
    private val canvasBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mole_hole)
    private val TAG: String = "CANVAS VIEW"
    private var score: Int = 0
    private val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.mole_squashed)

    interface ScoreListener {
        /*
        * Pass the score between GameView and Main Activity.
        * STEP 1 / 5 OBSERVER-PUBLISHER: Define the interface
        * Main Activity must register as subscriber using
        * 'setScoreListener' and override the 'onScoreUpdated'.
        * */
        fun onScoreUpdated(score: Int)
    }
    /*
    * STEP 2 / 5 OBSERVER-PUBLISHER: Placeholder for registered subscriber.
    * The subscriber must have the 'onScoreUpdated' implementation
    * as callback that handles the published score.
    * The subscriber must invoke 'setScoreListener' to register.
    *  */
    private var scoreListener: ScoreListener? = null


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
                // Use Mole's built-in handler
                score += 15
                mediaPlayer.start()
                scoreListener?.onScoreUpdated(score)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun startMoleAnimation() {
        score = 0
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
        // Recreate the array of Moles to start over
        // Cal when Start is pressed
        numberMoles = levelSettings.getMolesCount(levelSettings.currentLevel)
        molesArray = Array(numberMoles) { Mole(context, this, levelSettings)}
    }

    fun setScoreListener(listener: ScoreListener) {
        /*
        * STEP 3 / 5 OBSERVER-PUBLISHER: Subscribe for updated score
        * Main Activity must subscribe once gameView is instantiated
        * invoking this method on the newly minted GameView instance.
        * */
        scoreListener = listener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mediaPlayer.release()
    }
}
