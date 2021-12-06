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
    var highScore: Int = 0
    var score: Int = 0
    var lifes: Int = 3

}