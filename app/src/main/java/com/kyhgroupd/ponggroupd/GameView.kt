package com.kyhgroupd.ponggroupd

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kyhgroupd.ponggroupd.activitys.BreakoutActivity
import com.kyhgroupd.ponggroupd.activitys.GolfActivity
import com.kyhgroupd.ponggroupd.activitys.PongActivity
import com.kyhgroupd.ponggroupd.gameobjects.ComboText

@RequiresApi(Build.VERSION_CODES.O)
class GameView(context: Context): SurfaceView(context), SurfaceHolder.Callback, Runnable {

    private var thread: Thread? = null
    private var running = false
    lateinit var canvas: Canvas
    var mHolder: SurfaceHolder? = holder

    var frames: Int = 0
    var lastFpsCheck: Long = 0
    var timeToUpdate  = System.currentTimeMillis()

    init {
        mHolder?.addCallback(this)

        val activity: AppCompatActivity = context as AppCompatActivity

        //SoundManager
        SoundManager.init(context)

        //Reset game
        when(GameManager.gameMode){
            "breakout" -> GameManager.context = context as BreakoutActivity
            "pong" -> GameManager.context = context as PongActivity
            "golf" -> GameManager.context = context as GolfActivity
        }

        val displayMetrics: DisplayMetrics = activity.resources.displayMetrics
        GameManager.screenSizeX = displayMetrics.widthPixels
        GameManager.screenSizeY = displayMetrics.heightPixels

        //Reset game (or continue from "paused" game)?
        if(GameManager.shouldReset){
            GameManager.resetGame()
        }
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
        for (gameObject in GameManager.gameObjects) {
            gameObject.update()
        }

        //Brick pieces
        for (pieceObject in GameManager.pieceObjects) {
            pieceObject.update()
        }
        val pieceObjects = GameManager.pieceObjects.toMutableList()
        for (pieceObject in pieceObjects) {
            if(pieceObject.lifetime <= 0){
                GameManager.pieceObjects.remove(pieceObject)
            }
        }

        //UI objects
        for (uiObject in GameManager.uiObjects) {
            uiObject.update()
        }
        val uiObjects = GameManager.uiObjects.toMutableList()
        for (uiObject in uiObjects) {
            if(uiObject is ComboText){
                if(uiObject.lifetime <= 0){
                    GameManager.uiObjects.remove(uiObject)
                }
            }
        }

        //Ball
        GameManager.ball?.update()

        //UI
        GameManager.highScoreText?.textString = "HIGH SCORE: "+GameManager.highScore.toString()
        GameManager.scoreText?.textString = "SCORE: "+GameManager.score.toString()
        GameManager.livesText?.textString = "LIVES: "+GameManager.lives.toString()
        GameManager.levelText?.textString = "LEVEL: "+GameManager.level.toString()
        GameManager.comboText?.update()
    }

    private fun draw(){
        try{
            canvas = mHolder!!.lockCanvas()

            GameManager.background1?.let { canvas.drawBitmap(it, matrix, null) }
            //canvas.drawColor(Color.BLACK) //Draws a black background

            canvas.drawRect(0f, 0f, GameManager.screenSizeX.toFloat(), GameManager.uiHeight.toFloat(), GameManager.uiPaint)

            for (gameObject in GameManager.gameObjects) {
                gameObject.draw(canvas)
            }
            for (pieceObject in GameManager.pieceObjects) {
                pieceObject.draw(canvas)
            }
            for (uiObject in GameManager.uiObjects) {
                uiObject.draw(canvas)
            }
            GameManager.comboText?.draw(canvas)
            GameManager.ball?.draw(canvas)

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
            if(GameManager.isPaused){
                return
            }

            if(System.currentTimeMillis() >= timeToUpdate) {
                timeToUpdate += 1000/GameManager.targetFPS

                //FPSCounter
//                frames++
//                if (System.currentTimeMillis() > lastFpsCheck + 1000) {
//                    lastFpsCheck = System.currentTimeMillis()
//                    println(frames)
//                    frames = 0
//                }

                update()
                draw()

                when(GameManager.gameMode){
                    "breakout" -> if (GameManager.bricksCleared()) {
                        GameManager.nextLevel()
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event != null){
            GameManager.event = event
        }
        return true
    }
}