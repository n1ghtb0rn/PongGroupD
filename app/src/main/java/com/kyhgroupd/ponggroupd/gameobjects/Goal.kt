package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.kyhgroupd.ponggroupd.GameManager

class Goal(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    val borderPaint = Paint()

    init {
        width = GameManager.screenSizeX / GameManager.bricksPerRow
        height = (width/GameManager.brickHeightRatio) * 2
        posX -= width/2

        borderPaint.color = Color.BLACK
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = (height/ 10).toFloat()
    }


    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), paint)
        //Border
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), borderPaint)
    }

    override fun update() {

    }
}