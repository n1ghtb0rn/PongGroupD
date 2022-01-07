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
import com.kyhgroupd.ponggroupd.activitys.GameActivity
import com.kyhgroupd.ponggroupd.gameobjects.*

object GameManager {

    //Game mode
    var gameMode: String = ""
    var pongPlayerMode: Int = 0

    //Context
    var context: GameActivity? = null

    //FPS
    val targetFPS: Int = 60

    //Paused?
    var isPaused: Boolean = false

    //Continue?
    var shouldReset: Boolean = true

    //Background
    var background1: Bitmap? = null

    var screenSizeX = 0 //Is set in GameView class
    var screenSizeY = 0 //Is set in GameView class

    //GameObjects
    val gameObjects = mutableListOf<GameObject>()
    val pieceObjects = mutableListOf<BrickPiece>()
    val trailObjects = mutableListOf<BallTrail>()
    val powerUpObjects = mutableListOf<PowerUp>()

    //Color data
    var ballColor = Color.rgb(150, 0, 0)
    val paddleColor = Color.rgb(0, 150, 0)
    val gradientColor = Color.WHITE
    val gameTextColor = Color.LTGRAY
    val brickColors = mutableListOf<Int>()
    val goalColor = Color.WHITE

    //Ball
    var ball: Ball? = null
    var ballStartX: Int = 0 //Is set in resetGame() method
    var ballStartY: Int = 0 //Is set in resetGame() method
    var ballRadiusFactor: Int = 50
    var ballSpeed: Int = 0  //Is set in resetGame() method
    val ballSpeedFactor = 0.012

    //Paddle
    var paddle: Paddle? = null
    var paddle2: Paddle? = null
    var event: MotionEvent? = null
    var topBarHeight: Int = 170
    val paddleTouchOffsetY: Int = 300
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
    var gamesWon: Int = 0

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
        //Score reset
        score = 0
        player2Score = 0

        //Background
        background1 = BitmapFactory.decodeResource(context?.resources, R.drawable.background1b).scale(
            Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels, true)

        //Clear GameObject-lists
        gameObjects.clear()

        //Reference brick
        referenceBrick = Brick(200, 2500, Color.WHITE)

        //Paddle
        val player1 = 1
        if(gameMode == "pong"){
            val paddle = Paddle(screenSizeX / 2, screenSizeY - (screenSizeY / 6), Color.WHITE, player1)
            GameManager.paddle = paddle
            gameObjects.add(paddle)
            val player2 = 2
            val paddle2 = Paddle(screenSizeX/2, screenSizeY/12, Color.WHITE, player2)
            GameManager.paddle2 = paddle2
            gameObjects.add(paddle2)
            ballColor = Color.WHITE
        } else {
            val paddle = Paddle(screenSizeX / 2, screenSizeY - (screenSizeY / 6), paddleColor, player1)
            GameManager.paddle = paddle
            gameObjects.add(paddle)
            ballColor = Color.rgb(150, 0, 0)
        }


        //Ball
        ballStartX = screenSizeX/2
        when(gameMode){
            "pong" -> ballStartY = screenSizeY/2
            else -> ballStartY = paddle!!.posY-(paddle!!.height*2)
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

            }
            "golf" -> {
                lives = 0
                addBricksGolf()
            }
        }

        //Music
        SoundManager.playMusic()
    }

    private fun addBricks(){
        if(referenceBrick == null){
            return
        }

        var colorIndex: Int = 0
        val brickRowStart = ((UIManager.uiHeight + (UIManager.uiBorderWidth*2)) + referenceBrick!!.height).toInt()
        val brickRowEnd = ((UIManager.uiHeight + (UIManager.uiBorderWidth*2)) + (referenceBrick!!.height*brickRows)).toInt()
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
    }

    private fun addBricksGolf(){
        if(referenceBrick == null){
            return
        }

        val golfLevels = GolfLevels().levels
        if(golfLevels.size < level){
            //return if level doesn't exist
            return
        }
        val level = golfLevels[level-1]
        val rows = level.split(',')
        for(row in rows){
            if(row.length > 11){
                //return if a brick-row count is more than 11
                return
            }
        }

        Log.d("Danne", "Rows = " + rows.size) // = 5
        Log.d("Danne", "Columns = " + rows[0].length) // = 11

        for(y in 0..rows.size-1){
            for(x in 0..rows[0].length-1){
                val objectType: Char = rows[y][x]
                val startX = x * referenceBrick!!.width
                val startY = ((UIManager.uiHeight + (UIManager.uiBorderWidth*2)) + (y * referenceBrick!!.height)).toInt()

                var gameObject: GameObject? = null
                if(objectType == 'O'){
                    gameObject = Goal(startX, startY, goalColor)
                }
                if(objectType == 'L'){
                    gameObject = Brick(startX, startY, Color.WHITE, 1)
                }
                if(objectType == 'M'){
                    gameObject = Brick(startX, startY, Color.WHITE, 2)
                }
                if(objectType == 'S'){
                    gameObject = Brick(startX, startY, Color.WHITE, 3)
                }
                if(objectType == 'U'){
                    gameObject = Brick(startX, startY, Color.DKGRAY, true)
                }

                if(gameObject != null){
                    if(gameObject is Brick){
                        gameObject.changeColor()
                    }
                    gameObjects.add(gameObject)
                }
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
        //Increment current level
        level++

        if(gameMode == "breakout"){
            addBricks()
            if(paddle != null){
                var newPaddleWidth: Int = (paddle!!.width * paddleWidthPctPerLevel).toInt()
                if(newPaddleWidth < paddle!!.height){
                    newPaddleWidth = paddle!!.height
                }
                paddle!!.width = newPaddleWidth
            }


        } else if(gameMode == "golf"){
            val iterator = gameObjects.iterator()
            for (obj in iterator){
                if(obj is Brick){
                    iterator.remove()
                }
                if(obj is Goal){
                    iterator.remove()
                }
            }
            addBricksGolf()
            val golfLevels = GolfLevels().levels
            if(level > golfLevels.size){
                level = golfLevels.size
            }
        }

        //Finally
        ball?.resetPos()
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