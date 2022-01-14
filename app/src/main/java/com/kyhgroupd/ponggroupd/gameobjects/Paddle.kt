package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import com.kyhgroupd.ponggroupd.GameManager

/**
 *
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 */
class Paddle(startX: Int, startY: Int, color: Int, var player: Int) : GameObject(startX, startY, color) {

    init {
        width = GameManager.screenSizeX / GameManager.paddleWidthFactor
        height = width / GameManager.paddleHeightFactor
        posX -= width / 2
    }

    override fun draw(canvas: Canvas?) {
        if(GameManager.useColors || GameManager.gameMode == "pong"){
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

    private fun moveTwoPlayerPaddles() {
        //Get all touch points and check if any of them is within range
        val pointerCount = GameManager.event!!.pointerCount

        for (i in 0 until pointerCount) {
            val touchX = GameManager.event!!.getX(i).toInt()
            val touchY = GameManager.event!!.getY(i).toInt()

            if (this == GameManager.paddle && touchY > GameManager.screenSizeY/2) {
                this.posX = touchX - (width/2)
            } else if(this == GameManager.paddle2 && touchY < GameManager.screenSizeY/2){
                this.posX = touchX - (width/2)
            }
        }
    }

    private fun moveSinglePlayerPaddles() {
        var touchX = 0
        var touchY = 0

        if (GameManager.event != null){
            touchX = GameManager.event!!.x.toInt()
            touchY = GameManager.event!!.y.toInt()
        }

        if (this == GameManager.paddle && touchY > GameManager.screenSizeY/2) {
            this.posX = touchX - (width/2)
        }

        val aiPaddleSpeed = when (GameManager.pongDifficultyLevel) {
            "easy" -> (GameManager.ballSpeed*0.22).toInt()
            "medium" -> (GameManager.ballSpeed*0.45).toInt()
            "hard" -> (GameManager.ballSpeed*0.6).toInt()
            else -> GameManager.ballSpeed
        }
        if (GameManager.ball!!.posX > GameManager.paddle2!!.posX + width/2) {
            GameManager.paddle2!!.posX += (aiPaddleSpeed)
        } else if (GameManager.ball!!.posX < GameManager.paddle2!!.posX + width/2) {
            GameManager.paddle2!!.posX -= (aiPaddleSpeed)
        }
    }
}


