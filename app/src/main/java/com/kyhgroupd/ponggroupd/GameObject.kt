package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

abstract class GameObject(var startX: Int, var startY: Int, color: Int ) {

    var posX: Int = 0
    var posY: Int = 0
    var paint = Paint()

    init {
        this.posX = startX
        this.posY = startY
        this.paint.color = color
    }

    abstract fun draw(canvas: Canvas?)

    abstract fun update()
}