package com.kyhgroupd.ponggroupd

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import com.kyhgroupd.ponggroupd.gameobjects.ComboText
import com.kyhgroupd.ponggroupd.gameobjects.GameObject
import com.kyhgroupd.ponggroupd.gameobjects.GameText

object UIManager {

    var uiHeight: Int = 0
    var uiBorderWidth = 3f
    val uiHeightFactor = 12

    //UI border paint
    val uiPaint = Paint()

    //UI objects
    val uiObjects = mutableListOf<GameObject>()

    //UI Text
    var scoreText: GameText? = null
    var scoreTextPlayer1: GameText? = null
    var scoreTextPlayer2: GameText? = null
    var highScoreText: GameText? = null
    var livesText: GameText? = null
    var levelText: GameText? = null
    var comboText: ComboText? = null
    var textSize: Float = 0f
    val textSizeFactor: Int = 25

    @RequiresApi(Build.VERSION_CODES.O)
    fun resetUI(){

        //UI
        uiHeight = GameManager.screenSizeY /uiHeightFactor
        textSize = (GameManager.screenSizeX / textSizeFactor).toFloat()
        uiPaint.style = Paint.Style.STROKE
        uiPaint.color = Color.WHITE
        uiPaint.strokeWidth = uiBorderWidth

        //Reset UI objects
        uiObjects.clear()

        //UI objects
        if (GameManager.gameMode != "pong") {
            this.addUiText()
        } else  {
         addPongUiText()
        }

        //UI Data
        GameManager.score = 0
        GameManager.player2Score = 0
        GameManager.highScore = DataManager.loadHighScore()
        GameManager.lives = 3
        GameManager.player2Lives = 3
        GameManager.level = 1
        GameManager.currentCombo = 0
        comboText = null

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addUiText(){
        val scoreText = GameText(
            GameManager.screenSizeX /20, (uiHeight/2.5).toInt(),
            GameManager.gameTextColor
        )
        this.scoreText = scoreText
        uiObjects.add(scoreText)
        val highScoreText = GameText(
            GameManager.screenSizeX /2, (uiHeight/2.5).toInt(),
            GameManager.gameTextColor
        )
        this.highScoreText = highScoreText
        uiObjects.add(highScoreText)
        val livesText = GameText(
            GameManager.screenSizeX /20, (uiHeight/1.25).toInt(),
            GameManager.gameTextColor
        )
        this.livesText = livesText
        uiObjects.add(livesText)
        val levelText = GameText(
            GameManager.screenSizeX /2, (uiHeight/1.25).toInt(),
            GameManager.gameTextColor
        )
        this.levelText = levelText
        uiObjects.add(levelText)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addPongUiText(){
        val pongScoreText = GameText((GameManager.screenSizeX*0.75).toInt(),GameManager.screenSizeY/4,
            GameManager.gameTextColor)
        val pongScoreText2 = GameText((GameManager.screenSizeX*0.25).toInt(),GameManager.screenSizeY/4,
            GameManager.gameTextColor)
        this.scoreTextPlayer1 = pongScoreText
        this.scoreTextPlayer2 = pongScoreText2
        uiObjects.add(pongScoreText)
        uiObjects.add(pongScoreText2)
    }
}