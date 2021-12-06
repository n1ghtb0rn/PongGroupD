package com.kyhgroupd.ponggroupd

import android.view.MotionEvent

object DataManager {

    //GameObjects
    val gameObjects = mutableListOf<GameObject>()
    val uiObjects = mutableListOf<GameObject>()

    //Game+UI border size
    var screenSizeX = 0
    var screenSizeY = 0
    var uiHeight: Int = 0

    //Ball
    var ball: Ball? = null
    var ballStartX: Int = 0
    var ballStartY: Int = 0
    val ballSpeed: Int = 15

    //Paddle
    var paddle: Paddle? = null
    var event: MotionEvent? = null

    //Player data
    var score: Int = 0
    var highScore: Int = 0
    var lives: Int = 3

    //UI Text
    var scoreText: GameText? = null
    var highScoreText: GameText? = null
    var livesText: GameText? = null

}