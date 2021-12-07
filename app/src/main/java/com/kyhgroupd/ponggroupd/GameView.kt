package com.kyhgroupd.ponggroupd

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity

class GameView(context: Context): SurfaceView(context), SurfaceHolder.Callback, Runnable {

    private var thread: Thread? = null
    private var running = false
    lateinit var canvas: Canvas
    var mHolder: SurfaceHolder? = holder

    var frames: Int = 0
    var lastFpsCheck: Long = 0
    val targetFPS: Int = 60
    var targetTime: Int = 1000000000/targetFPS

    init {
        mHolder?.addCallback(this)

        var activity: AppCompatActivity = context as AppCompatActivity
        var displayMetrics: DisplayMetrics = activity.resources.displayMetrics
        DataManager.screenSizeX = displayMetrics.widthPixels
        DataManager.screenSizeY = displayMetrics.heightPixels

        //SoundManager
        SoundManager.init(context)

        //Reset game
        DataManager.resetGame()
    }

    fun start() {
        running = true
        thread = Thread(this)
        thread?.start()
    }

    fun stop() {
        running = false
        try {
            thread?.join()
        } catch (e: InterruptedException){
            e.printStackTrace()
        }
    }

    fun update(){
        //Bricks and paddle
        for (gameObject in DataManager.gameObjects) {
            gameObject.update()
        }

        //Brick pieces
        for (pieceObject in DataManager.pieceObjects) {
            pieceObject.update()
        }
        val pieceObjects = DataManager.pieceObjects.toMutableList()
        for (pieceObject in pieceObjects) {
            if(pieceObject.lifetime <= 0){
                DataManager.pieceObjects.remove(pieceObject)
            }
        }

        //Ball
        DataManager.ball?.update()

        //UI
        DataManager.highScoreText?.textString = "HIGH SCORE: "+DataManager.highScore.toString()
        DataManager.scoreText?.textString = "SCORE: "+DataManager.score.toString()
        DataManager.livesText?.textString = "LIVES: "+DataManager.lives.toString()
    }

    private fun draw(){
        try{
            canvas = mHolder!!.lockCanvas()
            canvas.drawColor(Color.BLACK)
            val uiPaint = Paint()
            uiPaint.style = Paint.Style.STROKE
            uiPaint.color = Color.WHITE
            uiPaint.strokeWidth = 3f
            canvas.drawRect(0f, 0f, DataManager.screenSizeX.toFloat(), DataManager.uiHeight.toFloat(), uiPaint)

            for (gameObject in DataManager.gameObjects) {
                gameObject.draw(canvas)
            }
            for (pieceObject in DataManager.pieceObjects) {
                pieceObject.draw(canvas)
            }
            for (gameObject in DataManager.uiObjects) {
                gameObject.draw(canvas)
            }
            DataManager.ball?.draw(canvas)

            mHolder!!.unlockCanvasAndPost(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()
    }

    override fun run() {
        while (running){
            val startTime = System.nanoTime()

            //FPSCounter
//            frames++
//            if(System.nanoTime() > lastFpsCheck + 1000000000){
//                lastFpsCheck = System.nanoTime()
//                println(frames)
//                frames = 0
//            }

            update()
            draw()

            val totalTime = System.nanoTime() - startTime
            if(totalTime < targetTime){
                Thread.sleep((targetTime - totalTime)/1000000)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event != null){
            DataManager.event = event
        }
        return true
    }
}