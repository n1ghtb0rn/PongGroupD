package com.kyhgroupd.ponggroupd

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
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

    private val numOfBrickRows: Int = 10


    init {
        mHolder?.addCallback(this)

        var activity: AppCompatActivity = context as AppCompatActivity
        var displayMetrics: DisplayMetrics = activity.resources.displayMetrics
        DataManager.screenSizeX = displayMetrics.widthPixels
        DataManager.screenSizeY = displayMetrics.heightPixels

        setup()
    }

    private fun setup() {
        DataManager.uiHeight = DataManager.screenSizeY/7
        DataManager.ball = Ball(475, 1000, Color.WHITE)
        var testBrick : Brick = Brick(100, 300, Color.WHITE)

        for (y in testBrick.height+DataManager.uiHeight..testBrick.height*numOfBrickRows step testBrick.height) {
            for (x in 0..DataManager.screenSizeX step testBrick.width) {
                var brick = Brick(x, y, Color.WHITE)
                DataManager.gameObjects.add(brick)
                println("x: $x y: $y")
            }
        }
        var paddle : Paddle = Paddle(DataManager.screenSizeX/2,DataManager.screenSizeY - DataManager.screenSizeY/7, Color.WHITE)

        DataManager.paddle = paddle
        DataManager.gameObjects.add(paddle)

        var highScore = GameText(50, 50, Color.WHITE, DataManager.highScore.toString())
        DataManager.uiObjects.add(highScore)
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
        for (gameObject in DataManager.gameObjects) {
            gameObject.update()
        }
        for (gameObject in DataManager.uiObjects) {
            gameObject.update()
        }
        DataManager.ball?.update()
    }

    private fun draw(){
        canvas = mHolder!!.lockCanvas()
        canvas.drawColor(Color.BLACK)

        for (gameObject in DataManager.gameObjects) {
            gameObject.draw(canvas)
        }
        for (gameObject in DataManager.uiObjects) {
            gameObject.draw(canvas)
        }
        DataManager.ball?.draw(canvas)

        mHolder!!.unlockCanvasAndPost(canvas)
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
            update()
            draw()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event != null){
            DataManager.event = event
        }
        return true
    }
}