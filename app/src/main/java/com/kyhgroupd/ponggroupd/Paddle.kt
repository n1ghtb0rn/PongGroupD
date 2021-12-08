package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader

class Paddle(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    init {
        width = GameManager.screenSizeX/5
        height = width/5
        posX -= width/2
    }

    override fun draw(canvas: Canvas?){
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(), GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawRect(posX.toFloat(), posY.toFloat(),posX + width.toFloat(), posY + height.toFloat(), this.paint)
    }

    override fun update() {
        if (GameManager.event != null) {
            val paddleY = GameManager.paddle?.posY
            if (paddleY != null) {

                var touchY = GameManager.event!!.y.toInt()
                val offset = GameManager.paddleTouchOffsetY
                //Subtract top bar and and half paddle height
                touchY -= GameManager.topBarHeight + height/2
                //Check if touch is in range of paddle
                if(touchY > paddleY - offset && touchY < paddleY + offset){
                    posX = GameManager.event!!.x.toInt() - (width / 2)
                }
                //Check if paddle is out of bounds
                if(posX < 0){
                    posX = 0
                }
                if(posX > GameManager.screenSizeX - width){
                    posX = GameManager.screenSizeX - width
                }
            }
            GameManager.event = null
        }
    }
}