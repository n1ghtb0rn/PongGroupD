package com.kyhgroupd.ponggroupd

import android.graphics.Canvas

class Paddle(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    val width: Int = 200
    val height: Int = 50

    override fun draw(canvas: Canvas?){
        canvas?.drawRect(posX.toFloat(), posY.toFloat(),posX + width.toFloat(), posY + height.toFloat(), this.paint)
    }

}