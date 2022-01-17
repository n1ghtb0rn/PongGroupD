package com.kyhgroupd.ponggroupd

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kyhgroupd.ponggroupd.activitys.GameActivity
import com.kyhgroupd.ponggroupd.gameobjects.ComboText

/**
 * GameView-class that extends SurfaceView and implements SurfaceHolder.Callback and Runnable.
 * This class is the foundation of the game-engine that is responsible for updating the graphics
 * and running the game-loop. It loops through all Game Objects and calls their respective
 * update() and draw() methods.
 */
@RequiresApi(Build.VERSION_CODES.O)
class GameView(context: Context): SurfaceView(context), SurfaceHolder.Callback, Runnable {

    private var thread: Thread? = null  //Game thread
    private var running = false
    lateinit var canvas: Canvas     //The Canvas-object where the game is drawn
    var mHolder: SurfaceHolder? = holder

    var frames: Int = 0
    //var lastFpsCheck: Long = 0
    var timeToUpdate  = System.currentTimeMillis()

    init {
        mHolder?.addCallback(this)

        val activity: AppCompatActivity = context as AppCompatActivity

        //SoundManager
        SoundManager.init(context)

        //Reset game
        GameManager.context = context as GameActivity

        //Get the height and width of the display
        val displayMetrics: DisplayMetrics = activity.resources.displayMetrics
        GameManager.screenSizeX = displayMetrics.widthPixels
        GameManager.screenSizeY = displayMetrics.heightPixels

        //Reset game (or continue from "paused" game)?
        if(GameManager.shouldReset){
            UIManager.resetUI()
            GameManager.resetGame()
        }
    }

    /**
     * The start()-method override to start the game-thread.
     */
    fun start() {
        running = true
        thread = Thread(this)
        thread?.start()
    }

    /**
     * The stop()-method override to stop the game-thread
     */
    fun stop() {
        running = false
        try {
            thread?.join()
        } catch (e: InterruptedException){
            e.printStackTrace()
        }
    }

    /**
     * The main update-method that is the base for updating the game (eg: moving objects).
     * It calls all active Game Objects update() methods every game frame.
     */
    fun update(){
        //Bricks and paddle
        for (gameObject in GameManager.gameObjects) {
            gameObject.update()
        }

        //Brick pieces
        val pieceObjects = GameManager.pieceObjects.toMutableList()
        for (pieceObject in pieceObjects) {
            pieceObject.update()
            if(pieceObject.lifetime <= 0){
                GameManager.pieceObjects.remove(pieceObject)
            }
        }

        //Trail objects
        val trailObjects = GameManager.trailObjects.toMutableList()
        for(trailObject in trailObjects){
            trailObject.update()
            if(trailObject.radius <= 0){
                GameManager.trailObjects.remove(trailObject)
            }
        }

        //Power Up objects
        for(powerUpObject in GameManager.powerUpObjects){
            powerUpObject.update()
        }
        val powerUpObjects = GameManager.powerUpObjects.toMutableList()
        for(powerUpObject in powerUpObjects){
            if(powerUpObject.posY > GameManager.screenSizeY || powerUpObject.collidedWithPaddle){
                GameManager.powerUpObjects.remove(powerUpObject)
            }
        }

        if(GameManager.gameMode == "breakout"){
            PowerUpManager.updatePowerUps()
        }

        //Multi Ball objects
        val multiBallObjects = GameManager.multiBallObjects.toMutableList()
        for(multiBall in multiBallObjects){
            multiBall.update()
            if(multiBall.shouldDeleteThis){
                GameManager.multiBallObjects.remove(multiBall)
            }
        }

        //Ball
        GameManager.ball?.update()

        //UI objects
        for (uiObject in UIManager.uiObjects) {
            uiObject.update()
        }
        val uiObjects = UIManager.uiObjects.toMutableList()
        for (uiObject in uiObjects) {
            if(uiObject is ComboText){
                if(uiObject.lifetime <= 0){
                    UIManager.uiObjects.remove(uiObject)
                }
            }
        }

        //UI
        UIManager.highScoreText?.textString = "HIGH SCORE: "+GameManager.highScore.toString()
        UIManager.scoreText?.textString = "SCORE: "+GameManager.score.toString()
        if(GameManager.gameMode == "breakout" || GameManager.gameMode == "golf"){
            UIManager.livesText?.textString = "LIVES: "+GameManager.lives.toString()
        } else if(GameManager.gameMode == "pong"){
            UIManager.scoreTextPlayer1?.textString = GameManager.score.toString()
            UIManager.scoreTextPlayer2?.textString = GameManager.player2Score.toString()
        }
        UIManager.levelText?.textString = "LEVEL: "+GameManager.level.toString()
        UIManager.comboText?.update()

        //Reset the touch event at the end of every game-loop
        GameManager.event = null

        //Check level clear in breakout-mode
        when(GameManager.gameMode){
            "breakout" -> if (GameManager.bricksCleared()) {
                GameManager.nextLevel()
            }
        }
    }

    /**
     * The main draw() method that calls all Game Objects draw()-methods.
     */
    private fun draw(){
        //A try-catch to prevent the game from crashing if the user exits the app.
        try{
            canvas = mHolder!!.lockCanvas()

            if(GameManager.useColors){
                GameManager.background1?.let { canvas.drawBitmap(it, matrix, null) }
            }
            else{
                canvas.drawColor(Color.BLACK)
            }

            if (GameManager.gameMode == "pong") {
                canvas.drawColor(Color.BLACK) //Draws a black background

                if (GameManager.pongPlayerMode == 2) {
                    canvas.save()
                    canvas.rotate(270F,(GameManager.screenSizeX/2).toFloat(),(GameManager.screenSizeY/2).toFloat())
                    for (uiObject in UIManager.uiObjects) {
                        uiObject.draw(canvas)
                    }
                    canvas.restore()
                    canvas.save()
                } else if (GameManager.pongPlayerMode == 1) {
                    for (uiObject in UIManager.uiObjects) {
                        uiObject.draw(canvas)
                    }
                }
            }

            if(GameManager.gameMode == "breakout" || GameManager.gameMode == "golf") {
                canvas.drawRect(0f, 0f, GameManager.screenSizeX.toFloat(),
                    UIManager.uiHeight.toFloat(), UIManager.uiPaint)
                for (uiObject in UIManager.uiObjects) {
                    uiObject.draw(canvas)
                }
            }

            for (gameObject in GameManager.gameObjects) {
                gameObject.draw(canvas)
            }
            for (pieceObject in GameManager.pieceObjects) {
                pieceObject.draw(canvas)
            }

            UIManager.comboText?.draw(canvas)

            for(powerUpObject in GameManager.powerUpObjects){
                powerUpObject.draw(canvas)
            }

            for(trailObject in GameManager.trailObjects){
                trailObject.draw(canvas)
            }
            for(multiBall in GameManager.multiBallObjects){
                multiBall.draw(canvas)
            }
            GameManager.ball?.draw(canvas)

            mHolder!!.unlockCanvasAndPost(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * The surfaceCreated() override method. Called when the GameView (SurfaceView) is created,
     * and starts the game-thread.
     */
    override fun surfaceCreated(p0: SurfaceHolder) {
        start() //Start the game thread
    }

    /**
     * The surfaceChanged() override method. Currently unused.
     */
    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        //Unused
    }

    /**
     * The surfaceDestroyed() override method. Called when the GameView (SurfaceView) is destroyed,
     * and stops the game-thread.
     */
    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()  //Stop the game thread
    }

    /**
     * The run() override method. This method runs the base game-loop that keeps the game updated.
     * The game loops in a given interval and calls the update() and draw() methods.
     */
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

                //Calls the update-method
                update()

                //Calls the draw-method
                draw()
            }
        }
    }

    /**
     * The onTouchEvent() override method. Is called when the user touches the screen.
     * It stores the touch event in as a property in GameManager-class.
     * (The paddle-class later checks if this property if null or not to update its position)
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event != null){
            GameManager.event = event
        }
        return true
    }
}