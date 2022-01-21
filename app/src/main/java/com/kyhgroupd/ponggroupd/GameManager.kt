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

/**
 * Singleton class for handling game data and setting up the game.
 */
object GameManager {

    //Game mode
    var gameMode: String = ""
    var pongPlayerMode: Int = 0
    var pongDifficultyLevel: String = ""

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
    val multiBallObjects = mutableListOf<Ball>()

    //Color data
    var ballColor = Color.rgb(250, 231, 181)
    val paddleColor = Color.rgb(204,255,255)
    val gradientColor = Color.WHITE
    val gameTextColor = Color.LTGRAY
    val brickColors = mutableListOf<Int>()
    val goalColor = Color.WHITE

    //Ball
    var ball: Ball? = null
    var ballStartX: Int = 0 //Is set in resetGame() method
    var ballStartY: Int = 0 //Is set in resetGame() method
    var ballSpeed: Int = 0  //Is set in resetGame() method
    val ballSpeedFactor = 0.012

    //Paddle
    var paddle: Paddle? = null
    var paddle2: Paddle? = null
    var event: MotionEvent? = null
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
    var player2Score: Int = 0
    var player2Lives: Int = 3
    var gamesWon: Int = 0
    var win: Boolean = false

    //Score data
    var scorePerBrick = 100
    var bonusScorePerLevel = 25
    val comboBonusScore = 25
    var currentCombo = 0
    val pongMaxLives = 11

    //Player Settings
    val numberOfSettings = 3    //Change this when adding new settings
    var useSFX: Boolean = true
    var useMusic: Boolean = true
    var useColors: Boolean = true

    /**
     * A method for resetting the game. This includes the ball's position/speed, bricks,
     * player score and number of lives.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun resetGame(){
        //Score reset
        score = 0
        player2Score = 0

        //Background
        background1 = BitmapFactory.decodeResource(context?.resources, R.drawable.background1b).scale(
            Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels, true)

        //Clear GameObject lists
        gameObjects.clear()
        pieceObjects.clear()
        trailObjects.clear()
        powerUpObjects.clear()
        multiBallObjects.clear()
        PowerUpManager.clearActivePowerUps()

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
        } else {
            val paddle = Paddle(screenSizeX / 2, screenSizeY - (screenSizeY / 6), paddleColor, player1)
            GameManager.paddle = paddle
            gameObjects.add(paddle)
        }


        //Ball
        ballStartX = screenSizeX/2
        ballStartY = when(gameMode) {
            "pong" -> screenSizeY/2
            else -> paddle!!.posY-(paddle!!.height*2)
        }

        ballSpeed = (screenSizeX * ballSpeedFactor).toInt()

        if (gameMode == "pong") {
            ball = Ball(0, 0, Color.WHITE)
            ballSpeed = when (pongDifficultyLevel) {
                "medium" -> (ballSpeed*1.2).toInt()
                "hard" -> (ballSpeed*1.5).toInt()
                else -> ballSpeed
            }
        } else {
            ball = Ball(0, 0, ballColor)
        }

        ball!!.resetPos()

        //Bricks
        when(gameMode){
            "breakout" -> {
                addBrickColors()
                addBricks()
            }
            "golf" -> {
                addBricksGolf()
            }
        }

        //Music
        SoundManager.playMusic()
    }

    /**
     * A method for creating all brick-objects in Breakout game mode.
     */
    private fun addBricks(){
        //"Reference brick" is never used in-game, only acts as a tool for creating other bricks.
        if(referenceBrick == null){
            return
        }

        var colorIndex: Int = 0
        //Determine the starting/ending positions of all bricks positions
        val brickRowStart = ((UIManager.uiHeight + (UIManager.uiBorderWidth*2)) + referenceBrick!!.height).toInt()
        val brickRowEnd = ((UIManager.uiHeight + (UIManager.uiBorderWidth*2)) + (referenceBrick!!.height*brickRows)).toInt()
        val brickColumnStart = 0
        val brickColumnEnd = screenSizeX-(referenceBrick!!.width/2)
        for (y in brickRowStart..brickRowEnd step referenceBrick!!.height) {
            for (x in brickColumnStart..brickColumnEnd step referenceBrick!!.width) {
                //Create a new brick object at given position and add it to game objects list.
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

    /**
     * A method for generating bricks in golf game mode. The blueprint for each level
     * is stored in strings (in GolfLevels class). Every char represents a different game object.
     */
    private fun addBricksGolf(){
        if(referenceBrick == null){
            return
        }

        val golfLevels = GolfLevels().levels
        if(golfLevels.size < level){
            //return if level doesn't exist
            return
        }
        //Load the current level (as a char sequence/string)
        val level = golfLevels[level-1]
        //Split the string into multiple rows
        val rows = level.split(',')
        for(row in rows){
            if(row.length > bricksPerRow){
                //return if a brick row count is more than 11
                return
            }
        }

        //Loop through every char and create a corresponding game object at the given position.
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
                        //Set the color of the brick
                        gameObject.changeColor()
                    }
                    //Add the game object to the game objects list.
                    gameObjects.add(gameObject)
                }
            }
        }
    }

    /**
     * A method for checking if all bricks have been cleared by the player.
     *
     * @return Boolean Returns true if all bricks are cleared.
     */
    fun bricksCleared(): Boolean{
        for (obj in gameObjects){
            if(obj is Brick){
                return false
            }
        }
        return true
    }

    /**
     * A method for advancing the current level in breakout/golf.
     */
    fun nextLevel(){
        //Increment current level
        level++

        //Breakout: Reset the level and make the paddle shorter
        if(gameMode == "breakout"){
            PowerUpManager.clearActivePowerUps()
            powerUpObjects.clear()
            addBricks()
            if(paddle != null){
                var newPaddleWidth: Int = (paddle!!.width * paddleWidthPctPerLevel).toInt()
                if(newPaddleWidth < paddle!!.height){
                    newPaddleWidth = paddle!!.height
                }
                paddle!!.width = newPaddleWidth
            }

        }
        //Golf: Remove remaining bricks and call the method to generate new ones.
        else if(gameMode == "golf"){
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

        //Finally reset the ball's position
        ball?.resetPos()
    }

    /**
     * A method for adding different colors to brick colors list.
     * These colors are used in breakout game mode.
     */
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

    /**
     * A method for adding score when the player breaks a brick.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun addScore(){
        //Add score based on level
        score += scorePerBrick + ((level - 1) * bonusScorePerLevel)
        //Add score based on combo
        if (currentCombo > 0) {
            val comboValue = currentCombo * comboBonusScore
            score += comboValue
            val comboText = ComboText(screenSizeX / 2, screenSizeY / 2, gameTextColor, comboValue)
            UIManager.comboText = comboText
            SoundManager.playComboSFX()
        }
        currentCombo++
    }

    /**
     * A method for adding score in pong game mode.
     *
     * @params player Int The player that the score will be added to.
     */
    fun scorePointPong(player: Int){
        currentCombo = 0
        UIManager.comboText = null

        //Increment score
        when(player) {
            1 -> score++
            2 -> player2Score++
        }
        if(score >= pongMaxLives) {
            if(pongPlayerMode == 1) {
                gamesWon++
            }
            SoundManager.playGameOverSFX()
            context?.gameOver()
        } else if (player2Score >= pongMaxLives) {
            SoundManager.playGameOverSFX()
            context?.gameOver()
        } else {
            SoundManager.playLoseLifeSFX()
        }
    }

    /**
     * A method for losing a life if the ball goes outside the screen.
     */
    fun loseLife(){
        currentCombo = 0
        UIManager.comboText = null

        //Decrement number of lives
        lives--

        //Clear all power ups in breakout
        if(gameMode == "breakout"){
            PowerUpManager.clearActivePowerUps()
            powerUpObjects.clear()
        }

        //Check if game over
        if(lives <= 0 || player2Lives <= 0){
            SoundManager.playGameOverSFX()
            context?.gameOver()
        }
        else{
            SoundManager.playLoseLifeSFX()
        }
    }
}