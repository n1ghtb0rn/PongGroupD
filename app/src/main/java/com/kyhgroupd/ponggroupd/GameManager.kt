package com.kyhgroupd.ponggroupd


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.core.graphics.scale
import com.kyhgroupd.ponggroupd.activitys.BreakoutActivity
import com.kyhgroupd.ponggroupd.gameobjects.*

object GameManager {

    //Game mode
    var gameMode: String = ""

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

    var screenSizeX = 0 //Is set in GameView-class
    var screenSizeY = 0 //Is set in GameView-class

    //GameObjects
    val gameObjects = mutableListOf<GameObject>()
    val pieceObjects = mutableListOf<BrickPiece>()

    //Color data
    val ballColor = Color.rgb(150, 0, 0)
    val paddleColor = Color.rgb(0, 150, 0)
    val gradientColor = Color.WHITE
    val gameTextColor = Color.LTGRAY
    val brickColors = mutableListOf<Int>()
    val goalColor = Color.WHITE

    //Ball
    var ball: Ball? = null
    var ballStartX: Int = 0 //Is set in resetGame()-method
    var ballStartY: Int = 0 //Is set in resetGame()-method
    var ballRadiusFactor: Int = 50
    var ballSpeed: Int = 0  //Is set in resetGame()-method
    val ballSpeedFactor = 0.015

    //Paddle
    var paddle: Paddle? = null
    var paddle2: Paddle? = null
    var event: MotionEvent? = null
    var topBarHeight: Int = 170
    val paddleTouchOffsetY: Int = 200
    var paddleWidthFactor: Int = 5
    var paddleHeightFactor: Int = 5
    val paddleWidthPctPerLevel = 0.8

    //Bricks
    var referenceBrick: Brick? = null
    val brickRows: Int = 8
    val bricksPerRow: Int = 11
    val brickHeightRatio = 3
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
    var player2Name: String = ""
    var player2Score: Int = 0
    var player2Lives: Int = 3

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

        //Clear GameObject-lists
        gameObjects.clear()

        //Reference brick
        referenceBrick = Brick(200, 2500, Color.WHITE)

        //Paddle
        val paddle = Paddle(screenSizeX/2,screenSizeY - (screenSizeY/6), paddleColor)
        GameManager.paddle = paddle
        gameObjects.add(paddle)

        //Ball
        ballStartX = screenSizeX/2
        when(gameMode){
            "pong" -> ballStartY = screenSizeY/2
            else -> ballStartY = paddle.posY-(paddle.height*2)
        }
        ballSpeed = (screenSizeX * ballSpeedFactor).toInt()
        ball = Ball(0, 0, ballColor)
        ball!!.resetPos()

        //Bricks
        when(gameMode){
            "breakout" -> {
                addBrickColors()
                addBricks()
            }
            "pong" -> {
                val paddle2 = Paddle(screenSizeX/2, screenSizeY/12, paddleColor)
                GameManager.paddle2 = paddle2
                gameObjects.add(paddle2)
            }
            "golf" -> {
                lives = 0
                val goal = Goal(screenSizeX/2, screenSizeY/3, goalColor)
                gameObjects.add(goal)
                addBricksGolf()
            }
        }

        //Music
        SoundManager.playMusic()
    }

  //  @RequiresApi(Build.VERSION_CODES.O)
   // private fun addPongUiText(){
     //   val pongScoreText = GameText(screenSizeX/20, (uiHeight/2.5).toInt(), gameTextColor)
     //   GameManager.scoreText = pongScoreText
      //  uiObjects.add(pongScoreText)
   // }

    private fun addBricks(){
        if(referenceBrick == null){
            return
        }

        var colorIndex: Int = 0
        val brickRowStart = referenceBrick!!.height+UIManager.uiHeight
        val brickRowEnd = UIManager.uiHeight+(referenceBrick!!.height*brickRows)
        val brickColumnStart = 0
        val brickColumnEnd = screenSizeX-(referenceBrick!!.width/2)
        for (y in brickRowStart..brickRowEnd step referenceBrick!!.height) {
            for (x in brickColumnStart..brickColumnEnd step referenceBrick!!.width) {
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

    private fun addBricksGolf(){
        if(referenceBrick == null){
            return
        }

        val brickColumnStart = 0
        val brickColumnEnd = screenSizeX-(referenceBrick!!.width/2)
        for (x in brickColumnStart..brickColumnEnd step referenceBrick!!.width) {
            val brick = Brick(x, screenSizeY/2, Color.WHITE)
            brick.health = (1..3).random()
            brick.changeColor()
            gameObjects.add(brick)
        }
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
        if(gameMode == "breakout"){
            addBricks()
            if(paddle != null){
                var newPaddleWidth: Int = (paddle!!.width * paddleWidthPctPerLevel).toInt()
                if(newPaddleWidth < paddle!!.height){
                    newPaddleWidth = paddle!!.height
                }
                paddle!!.width = newPaddleWidth
            }
            ball?.resetPos()

        } else if(gameMode == "golf"){
            val iterator = gameObjects.iterator()
            for (obj in iterator){
                if(obj is Brick){
                    iterator.remove()
                }
            }
            addBricksGolf()
            ball?.resetPos()
        }
        level++
    }

    fun addBrickColors(){
        brickColors.clear()
        brickColors.add(Color.rgb(150, 0, 0))   //red
        brickColors.add(Color.rgb(150, 0, 75))
        brickColors.add(Color.rgb(150, 0, 150))
        brickColors.add(Color.rgb(0, 0, 200))
        brickColors.add(Color.rgb(0, 150, 150))
        brickColors.add(Color.rgb(0, 150, 0))   //green
        brickColors.add(Color.rgb(150, 150, 0)) //yellow
        brickColors.add(Color.rgb(150, 75, 0))  //orange
    }
}