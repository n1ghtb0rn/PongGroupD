package com.kyhgroupd.ponggroupd

import android.graphics.Color
import android.graphics.Paint

class GameObject {

    var posX: Int = 0
    var posY: Int = 0
    var paint = Paint()

    init {
        this.paint.color = Color.RED
    }

    fun draw(){
        //Override this!
    }

}