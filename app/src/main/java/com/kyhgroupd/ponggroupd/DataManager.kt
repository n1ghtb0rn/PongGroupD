package com.kyhgroupd.ponggroupd

import android.view.MotionEvent

object DataManager {

    val gameObjects = mutableListOf<GameObject>()
    var paddle: Paddle? = null

    var highScore: Int = 0

    var screenSizeX = 0
    var screenSizeY = 0

    var event: MotionEvent? = null

}