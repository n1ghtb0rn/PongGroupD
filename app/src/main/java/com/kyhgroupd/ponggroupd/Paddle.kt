package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.Shader
import android.view.MotionEvent

class Paddle(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    init {
        width = DataManager.screenSizeX/5
        height = width/5
        posX -= width/2
    }

    override fun draw(canvas: Canvas?){
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(), DataManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawRect(posX.toFloat(), posY.toFloat(),posX + width.toFloat(), posY + height.toFloat(), this.paint)
    }

    override fun update() {
        if (DataManager.event != null) {
            val paddleY = DataManager.paddle?.posY
            if (paddleY != null) {

                var touchY = DataManager.event!!.y.toInt()
                val offset = 200
                //Subtract top bar and and half paddle height
                touchY -= 170 + height/2
                //Check if touch is in range of paddle
                if(touchY > paddleY - offset && touchY < paddleY + offset){
                    posX = DataManager.event!!.x.toInt() - (width / 2)
                }
                //Check if paddle is out of bounds
                if(posX < 0){
                    posX = 0
                }
                if(posX > DataManager.screenSizeX - width){
                    posX = DataManager.screenSizeX - width
                }
            }
            DataManager.event = null
        }
    }
}