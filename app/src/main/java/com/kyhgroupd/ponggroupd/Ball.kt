package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Paint

class Ball(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    val radius : Int = 20
    var speedX: Int = 5
    var speedY: Int = 5

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(this.posX.toFloat(), this.posY.toFloat(), this.radius.toFloat(), this.paint)
    }

    override fun update(){
        posX += speedX
        posY += speedY
    }
}