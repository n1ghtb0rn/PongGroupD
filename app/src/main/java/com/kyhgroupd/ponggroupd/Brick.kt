package com.kyhgroupd.ponggroupd

import android.graphics.Canvas

class Brick(startX: Int, startY: Int, color: Int): GameObject(startX, startY, color) {

    val width: Int = 20
    val height: Int = 10

    fun destroy(){
        //1. Sound effect?

        //2. Animation effect? (optional)

        //3. Destroy this brick! (final)
    }

    fun draw(canvas: Canvas?){
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), this.height.toFloat(), this.width.toFloat(), this.paint)
    }

}