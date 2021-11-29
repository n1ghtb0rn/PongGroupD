package com.kyhgroupd.ponggroupd

import android.graphics.Canvas

open class Brick(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    val width: Int = 20
    val height: Int = 10

    fun destroy(){
        //TODO...
        //Sound effect?
    }

    override fun draw(canvas: Canvas?){
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), this.width.toFloat(), this.height.toFloat(), this.paint)
    }

}