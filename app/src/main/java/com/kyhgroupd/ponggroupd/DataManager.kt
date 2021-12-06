package com.kyhgroupd.ponggroupd

import android.graphics.Color
import android.view.MotionEvent

object DataManager {

    //GameObjects
    val gameObjects = mutableListOf<GameObject>()
    val uiObjects = mutableListOf<GameObject>()
    val pieceObjects = mutableListOf<BrickPiece>()

    val gameObjectColor = Color.GRAY
    val gradientColor = Color.WHITE
    val gameTextColor = Color.GREEN

    //Ball
    var ball: Ball? = null
    var ballStartX: Int = 0
    var ballStartY: Int = 0
    val ballSpeed: Int = 15

    //Paddle
    var paddle: Paddle? = null
    var event: MotionEvent? = null

    //Bricks
    val brickRows: Int = 8
    val bricksPerColumn: Int = 8
    val brickWidthRatio = 3

    //Pieces
    val piecesPerBrick = 8
    val pieceSpeed = 25
    val pieceLifetime = 10

    //Player data
    var score: Int = 0
    var highScore: Int = 0
    var lives: Int = 3
    var scorePerBrick = 100

    //Game+UI border size
    var screenSizeX = 0
    var screenSizeY = 0
    var uiHeight: Int = 0

    //UI Text
    var scoreText: GameText? = null
    var highScoreText: GameText? = null
    var livesText: GameText? = null
    var textSize: Float = 0f

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
        ball = Ball(ballStartX, ballStartY, gameObjectColor)

        //Bricks
        val referenceBrick = Brick(100, 300, Color.WHITE)
        for (y in referenceBrick.height+uiHeight..uiHeight+(referenceBrick.height*brickRows) step referenceBrick.height) {
            for (x in 0..screenSizeX-1 step referenceBrick.width) {
                val brick = Brick(x, y, gameObjectColor)
                gameObjects.add(brick)
            }
        }

        //Paddle
        var paddle = Paddle(screenSizeX/2,screenSizeY - screenSizeY/7, gameObjectColor)
        DataManager.paddle = paddle
        gameObjects.add(paddle)

        //UI objects
        var scoreText = GameText(screenSizeX/20, (uiHeight/2.5).toInt(), gameTextColor)
        DataManager.scoreText = scoreText
        uiObjects.add(scoreText)
        var highScoreText = GameText(screenSizeX/2, (uiHeight/2.5).toInt(), gameTextColor)
        DataManager.highScoreText = highScoreText
        uiObjects.add(highScoreText)
        var livesText = GameText(screenSizeX/20, (uiHeight/1.25).toInt(), gameTextColor)
        DataManager.livesText = livesText
        uiObjects.add(livesText)

        //UI Data
        score = 0
        lives = 3

        //Music
        SoundManager.playMusic()
    }

}