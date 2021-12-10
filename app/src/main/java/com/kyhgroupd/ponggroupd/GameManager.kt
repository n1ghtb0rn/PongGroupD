package com.kyhgroupd.ponggroupd

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale

object GameManager {
  
    //Context
    var context: BreakoutActivity? = null
  
    //FPS
    val targetFPS: Int = 60

    //Paused?
    var isPaused: Boolean = false

    //Continue?
    var shouldReset: Boolean = true

    //Background
    var background1: Bitmap? = null
    val uiPaint = Paint()

    //Game+UI border size
    var screenSizeX = 0 //Is set in GameView-class
    var screenSizeY = 0 //Is set in GameView-class
    var uiHeight: Int = 0   //Is set in resetGame()-method
    var uiBorderWidth = 3f
    val uiHeightFactor = 12

    //UI Text
    var scoreText: GameText? = null
    var highScoreText: GameText? = null
    var livesText: GameText? = null
    var levelText: GameText? = null
    var comboText: ComboText? = null
    var textSize: Float = 0f    //Is set in GameView
    val textSizeFactor: Int = 25

    //GameObjects
    val uiObjects = mutableListOf<GameObject>()
    val gameObjects = mutableListOf<GameObject>()
    val pieceObjects = mutableListOf<BrickPiece>()

    //Color data
    val ballColor = Color.DKGRAY
    val paddleColor = Color.DKGRAY
    val gradientColor = Color.WHITE
    val gameTextColor = Color.LTGRAY
    val brickColors = mutableListOf<Int>()

    //Ball
    var ball: Ball? = null
    var ballStartX: Int = 0 //Is set in resetGame()-method
    var ballStartY: Int = 0 //Is set in resetGame()-method
    var ballRadiusFactor: Int = 50
    var ballSpeed: Int = 0  //Is set in resetGame()-method
    val ballSpeedFactor = 0.015

    //Paddle
    var paddle: Paddle? = null
    var event: MotionEvent? = null
    var topBarHeight: Int = 170
    val paddleTouchOffsetY: Int = 200
    var paddleWidthFactor: Int = 5
    var paddleHeightFactor: Int = 5
    val paddleWidthPctPerLevel = 0.8

    //Bricks
    val brickRows: Int = 8
    val bricksPerColumn: Int = 8
    val brickWidthRatio = 3
    val borderStrokeWidthFactor = 5

    //Pieces
    val pieceSpeed = 25
    val pieceLifetime = 10
    val pieceWidthFactor = 2.5

    //Player data
    var playerName: String = ""
    var score: Int = 0
    var highScore: Int = 0
    var lives: Int = 3
    var level = 1

    //Score data
    var scorePerBrick = 100
    var bonusScorePerLevel = 25
    val comboBonusScore = 25
    var currentCombo = 0

    //Player Settings
    val numberOfSettings = 3    //Change this when adding new settings
    var useSFX: Boolean = true
    var useMusic: Boolean = true
    var useColors: Boolean = true

    @RequiresApi(Build.VERSION_CODES.O)
    fun resetGame(){
        //Background
        background1 = BitmapFactory.decodeResource(context?.resources, R.drawable.background1b).scale(
            Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels, true)

        //UI
        uiHeight = screenSizeY/uiHeightFactor
        textSize = (screenSizeX / textSizeFactor).toFloat()
        uiPaint.style = Paint.Style.STROKE
        uiPaint.color = Color.WHITE
        uiPaint.strokeWidth = uiBorderWidth

        //Clear GameObject-lists
        gameObjects.clear()
        uiObjects.clear()

        //Ball
        ballStartX = screenSizeX - (screenSizeX/3)
        ballStartY = screenSizeY/3
        ballSpeed = (screenSizeX * ballSpeedFactor).toInt()
        ball = Ball(ballStartX, ballStartY, ballColor)

        //Bricks
        addBrickColors()
        addBricks()

        //Paddle
        val paddle = Paddle(screenSizeX/2,screenSizeY - screenSizeY/7, paddleColor)
        GameManager.paddle = paddle
        gameObjects.add(paddle)

        //UI objects
        addUiText()

        //UI Data
        score = 0
        highScore = DataManager.loadHighScore()
        lives = 3
        level = 1

        //Music
        SoundManager.playMusic()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addUiText(){
        val scoreText = GameText(screenSizeX/20, (uiHeight/2.5).toInt(), gameTextColor)
        GameManager.scoreText = scoreText
        uiObjects.add(scoreText)
        val highScoreText = GameText(screenSizeX/2, (uiHeight/2.5).toInt(), gameTextColor)
        GameManager.highScoreText = highScoreText
        uiObjects.add(highScoreText)
        val livesText = GameText(screenSizeX/20, (uiHeight/1.25).toInt(), gameTextColor)
        GameManager.livesText = livesText
        uiObjects.add(livesText)
        val levelText = GameText(screenSizeX/2, (uiHeight/1.25).toInt(), gameTextColor)
        GameManager.levelText = levelText
        uiObjects.add(levelText)
    }

    private fun addBricks(){
        var colorIndex: Int = 0
        val referenceBrick = Brick(200, 2500, Color.WHITE)
        for (y in referenceBrick.height+uiHeight..uiHeight+(referenceBrick.height*brickRows) step referenceBrick.height) {
            for (x in 0..screenSizeX-1 step referenceBrick.width) {
                val brick = Brick(x, y, brickColors[colorIndex])
                gameObjects.add(brick)
            }
            colorIndex++
            //Reset color index
            if(colorIndex >= brickColors.size){
                colorIndex = 0
            }
        }
        //gameObjects.add(referenceBrick) //Use this line for testing only!
    }

    fun bricksCleared(): Boolean{
        for (obj in gameObjects){
            if(obj is Brick){
                return false
            }
        }
        return true
    }

    fun nextLevel(){
        addBricks()
        if(paddle != null){
            var newPaddleWidth: Int = (paddle!!.width * paddleWidthPctPerLevel).toInt()
            if(newPaddleWidth < paddle!!.height){
                newPaddleWidth = paddle!!.height
            }
            paddle!!.width = newPaddleWidth
        }
        ball?.resetPos()
        level++
    }

    fun addBrickColors(){
        brickColors.clear()
        brickColors.add(Color.rgb(150, 0, 0))
        brickColors.add(Color.rgb(150, 0, 75))
        brickColors.add(Color.rgb(150, 0, 150))
        brickColors.add(Color.rgb(0, 0, 200))
        brickColors.add(Color.rgb(0, 150, 150))
        brickColors.add(Color.rgb(0, 150, 0))
        brickColors.add(Color.rgb(150, 150, 0))
        brickColors.add(Color.rgb(150, 75, 0))
    }

}