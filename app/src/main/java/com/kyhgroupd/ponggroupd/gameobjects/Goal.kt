package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import com.kyhgroupd.ponggroupd.GameManager

class Goal(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    init {
        width = GameManager.screenSizeX/10
        height = width
        posX -= width/2
    }


    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), paint)
    }

    override fun update() {

    }
}