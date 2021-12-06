package com.kyhgroupd.ponggroupd

import android.view.MotionEvent

object DataManager {

    val gameObjects = mutableListOf<GameObject>()
    val uiObjects = mutableListOf<GameObject>()
    var paddle: Paddle? = null
    var ball: Ball? = null

    var highScore: Int = 0
    var score: Int = 0

    var screenSizeX = 0
    var screenSizeY = 0

    var event: MotionEvent? = null

    val ballSpeed: Int = 15

    var uiHeight: Int = 0

    var ballStartX: Int = 0
    var ballStartY: Int = 0

}