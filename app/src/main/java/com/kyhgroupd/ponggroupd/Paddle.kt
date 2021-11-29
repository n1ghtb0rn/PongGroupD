package com.kyhgroupd.ponggroupd

import android.graphics.Canvas

class Paddle(startX: Int,startY: Int, color: Int) : GameObject(startX, startY, color) {

    val width: Int = 20
    val hight: Int = 5

    fun draw(canvas: Canvas?){
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), this.width.toFloat(), this.hight.toFloat(), this.paint)
    }

}