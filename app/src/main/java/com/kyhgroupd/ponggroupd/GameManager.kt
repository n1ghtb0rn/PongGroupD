package com.kyhgroupd.ponggroupd

import android.graphics.Color
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

object GameManager {
  
    //Context
    var context: BreakoutActivity? = null
  
    //FPS
    val targetFPS: Int = 60

    //Game+UI border size
    var screenSizeX = 0 //Is set in GameView-class
    var screenSizeY = 0 //Is set in GameView-class
    var uiHeight: Int = 0   //Is set in resetGame()-method
    var uiBorderWidth = 3f

    //UI Text
    var scoreText: GameText? = null
    var highScoreText: GameText? = null
    var livesText: GameText? = null
    var textSize: Float = 0f    //Is set in GameView

    //GameObjects
    val gameObjects = mutableListOf<GameObject>()
    val uiObjects = mutableListOf<GameObject>()
    val pieceObjects = mutableListOf<BrickPiece>()

    //Color data
    val gameObjectColor = Color.GRAY
    val gradientColor = Color.WHITE
    val gameTextColor = Color.WHITE
    val brickColors = mutableListOf<Int>()

    //Ball
    var ball: Ball? = null
    var ballStartX: Int = 0 //Is set in resetGame()-method
    var ballStartY: Int = 0 //Is set in resetGame()-method
    var ballRadiusFactor: Int = 50
    var ballSpeed: Int = 0  //Is set in resetGame()-method

    //Paddle
    var paddle: Paddle? = null
    var event: MotionEvent? = null
    var topBarHeight: Int = 170
    val paddleTouchOffsetY: Int = 200
    var paddleWidthFactor: Int = 5
    var paddleHeightFactor: Int = 5

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
    var score: Int = 0
    var highScore: Int = 0
    var lives: Int = 3
    var scorePerBrick = 100

    fun resetGame(){
        //UI
        uiHeight = screenSizeY/12
        textSize = (screenSizeX / 25).toFloat()

        //Clear GameObject-lists
        gameObjects.clear()
        uiObjects.clear()

        //Ball
        ballStartX = screenSizeX - (screenSizeX/3)
        ballStartY = screenSizeY/3
        ballSpeed = (screenSizeX * 0.015).toInt()
        ball = Ball(ballStartX, ballStartY, gameObjectColor)

        //Bricks
        addBrickColors()
        addBricks()

        //Paddle
        val paddle = Paddle(screenSizeX/2,screenSizeY - screenSizeY/7, gameObjectColor)
        GameManager.paddle = paddle
        gameObjects.add(paddle)

        //UI objects
        val scoreText = GameText(screenSizeX/20, (uiHeight/2.5).toInt(), gameTextColor)
        GameManager.scoreText = scoreText
        uiObjects.add(scoreText)
        val highScoreText = GameText(screenSizeX/2, (uiHeight/2.5).toInt(), gameTextColor)
        GameManager.highScoreText = highScoreText
        uiObjects.add(highScoreText)
        val livesText = GameText(screenSizeX/20, (uiHeight/1.25).toInt(), gameTextColor)
        GameManager.livesText = livesText
        uiObjects.add(livesText)

        //UI Data
        score = 0
        lives = 3

        //Music
        SoundManager.playMusic()
    }

    fun addBricks(){
        var colorIndex: Int = 0
        val referenceBrick = Brick(100, 300, Color.WHITE)
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
        ballSpeed  = (ballSpeed * 1.2).toInt()
        ball?.resetPos()
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