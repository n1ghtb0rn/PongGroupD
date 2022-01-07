package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.Color
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
        if(GameManager.useColors){
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
        else{
            this.grayPaint.shader = LinearGradient(
                posX.toFloat(),
                posY.toFloat(),
                (posX + (height / 2)).toFloat(),
                (posY + (height / 2)).toFloat(),
                GameManager.gradientColor,
                this.grayPaint.color,
                Shader.TileMode.CLAMP
            )

            canvas?.drawRect(
                posX.toFloat(),
                posY.toFloat(),
                posX + width.toFloat(),
                posY + height.toFloat(),
                this.grayPaint
            )
        }
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

        if (GameManager.event != null){
            touchX = GameManager.event!!.x.toInt()
            touchY = GameManager.event!!.y.toInt()
        }
        val offsetY = GameManager.paddleTouchOffsetY

        if (this == GameManager.paddle){
            if (touchY > (posY+(height/2)) - offsetY
                && touchY < (posY+(height/2)) + offsetY) {
                posX = touchX - (width/2)
            }
        }

        var AiPaddleSpeed = when (GameManager.pongDifficultyLevel) {
            "easy" -> (GameManager.ballSpeed*0.22).toInt()
            "medium" -> (GameManager.ballSpeed*0.45).toInt()
            "hard" -> (GameManager.ballSpeed*0.6).toInt()
            else -> GameManager.ballSpeed
        }
        if (GameManager.ball!!.posX > GameManager.paddle2!!.posX + width/2) {
            GameManager.paddle2!!.posX += (AiPaddleSpeed)
        } else if (GameManager.ball!!.posX < GameManager.paddle2!!.posX + width/2) {
            GameManager.paddle2!!.posX -= (AiPaddleSpeed)
        }
    }
}


