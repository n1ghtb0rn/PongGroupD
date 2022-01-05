package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import com.kyhgroupd.ponggroupd.GameManager

class Paddle(startX: Int, startY: Int, color: Int, var player: Int) : GameObject(startX, startY, color) {

    init {
        width = GameManager.screenSizeX / GameManager.paddleWidthFactor
        height = width / GameManager.paddleHeightFactor
        posX -= width / 2
    }

    override fun draw(canvas: Canvas?) {
        this.paint.shader = LinearGradient(
            posX.toFloat(),
            posY.toFloat(),
            (posX + (height / 2)).toFloat(),
            (posY + (height / 2)).toFloat(),
            GameManager.gradientColor,
            this.paint.color,
            Shader.TileMode.CLAMP
        )
        canvas?.drawRect(
            posX.toFloat(),
            posY.toFloat(),
            posX + width.toFloat(),
            posY + height.toFloat(),
            this.paint
        )
    }

    override fun update() {
        if (GameManager.pongPlayerMode == 1) {
            moveSinglePlayerPaddles()
        }

        if (GameManager.gameMode == "breakout" || GameManager.gameMode == "golf"
            || GameManager.pongPlayerMode == 2) {
            if (GameManager.event == null) {
                return
            }
            moveTwoPlayerPaddles()
        }

        //Check if paddle is out of bounds
        if (posX < 0) {
            posX = 0
        }
        if (posX > GameManager.screenSizeX - width) {
            posX = GameManager.screenSizeX - width
        }
    }

    fun moveTwoPlayerPaddles() {
        //Get all touch points and check if any of them is within range
        val pointerCount = GameManager.event!!.pointerCount

        for (i in 0 until pointerCount) {
            val touchX = GameManager.event!!.getX(i).toInt()
            val touchY = GameManager.event!!.getY(i).toInt()
            val offsetY = GameManager.paddleTouchOffsetY

            if (touchY > (posY+(height/2)) - offsetY && touchY < (posY+(height/2)) + offsetY) {
                posX = touchX - (width/2)
            }
        }
    }

    fun moveSinglePlayerPaddles() {
        var touchX = 0
        var touchY = 0

        if(GameManager.event != null){
            touchX = GameManager.event!!.x.toInt()
            touchY = GameManager.event!!.y.toInt()
        }
        val offsetY = GameManager.paddleTouchOffsetY

        if (touchY > (GameManager.paddle!!.posY+(height/2)) - offsetY
            && touchY < (GameManager.paddle!!.posY+(height/2)) + offsetY) {
            posX = touchX - (width/2)
        }
        GameManager.paddle2!!.posX = GameManager.ball!!.posX
    }
}


