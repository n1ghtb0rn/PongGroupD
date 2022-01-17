package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import com.kyhgroupd.ponggroupd.GameManager

/**
 * Paddle class inherits from GameObject class
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 * @param player User number
 */
class Paddle(startX: Int, startY: Int, color: Int, var player: Int) : GameObject(startX, startY, color) {

    // Base
    init {
        width = GameManager.screenSizeX / GameManager.paddleWidthFactor
        height = width / GameManager.paddleHeightFactor
        posX -= width / 2
    }

    override fun draw(canvas: Canvas?) {
        // Colors
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
        // Grayscale
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

    /**
     * Update method for updating position (etc) of this object every frame.
     */
    override fun update() {
        if (GameManager.pongPlayerMode == 1) {
            movePaddlesSinglePlayerPong()
        }

        if (GameManager.gameMode == "breakout" || GameManager.gameMode == "golf"
            || GameManager.pongPlayerMode == 2) {
            if (GameManager.event == null) {
                return
            }
            movePaddle()
        }

        //Check if paddle is out of bounds
        if (posX < 0) {
            posX = 0
        }
        if (posX > GameManager.screenSizeX - width) {
            posX = GameManager.screenSizeX - width
        }
    }

    /**
     * Move user paddle in golf mode and breakout mode. Move both user paddles in two player pong.
     */
    private fun movePaddle() {
        val pointerCount = GameManager.event!!.pointerCount

        for (i in 0 until pointerCount) {
            val touchX = GameManager.event!!.getX(i).toInt()
            val touchY = GameManager.event!!.getY(i).toInt()

            // Move Paddle 1 if touch event on bottom half of screen
            // Move Paddle 2 if touch event on top half of screen
            if (this == GameManager.paddle && touchY > GameManager.screenSizeY/2) {
                this.posX = touchX - (width/2)
            } else if(this == GameManager.paddle2 && touchY < GameManager.screenSizeY/2){
                this.posX = touchX - (width/2)
            }
        }
    }


    /**
     * Move user paddle and AI paddle in single player pong.
     */
    private fun movePaddlesSinglePlayerPong() {
        var touchX = 0
        var touchY = 0

        if (GameManager.event != null){
            touchX = GameManager.event!!.x.toInt()
            touchY = GameManager.event!!.y.toInt()
        }

        // Move user paddle
        if (this == GameManager.paddle && touchY > GameManager.screenSizeY/2) {
            this.posX = touchX - (width/2)
        }

        // Change speed of AI paddle
        val aiPaddleSpeed = when (GameManager.pongDifficultyLevel) {
            "easy" -> (GameManager.ballSpeed*0.22).toInt()
            "medium" -> (GameManager.ballSpeed*0.45).toInt()
            "hard" -> (GameManager.ballSpeed*0.6).toInt()
            else -> GameManager.ballSpeed
        }

        // Move AI paddle towards ball
        if (GameManager.ball!!.posX > GameManager.paddle2!!.posX + width/2) {
            GameManager.paddle2!!.posX += (aiPaddleSpeed)
        } else if (GameManager.ball!!.posX < GameManager.paddle2!!.posX + width/2) {
            GameManager.paddle2!!.posX -= (aiPaddleSpeed)
        }
    }
}


