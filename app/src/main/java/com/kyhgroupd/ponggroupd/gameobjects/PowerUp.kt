package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.*
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.PowerUpManager

/**
 * PowerUp class inherits from GameObject class
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 */
class PowerUp(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    private var radius : Int = 0

    //Power Up info
    var collidedWithPaddle = false

    private var powerUpType = "POWER_BALL" //default power up type

    init {
        radius = GameManager.referenceBrick!!.height
        width = radius*2
        height = radius*2

        this.powerUpType = PowerUpManager.generatePowerUpType()

        if (GameManager.useColors) { // Color
            if (this.powerUpType != "POWER_BALL") {
                this.paint.color = Color.BLUE
            } else {
                this.paint.color = Color.RED
            }
        } else { // Grayscale
            this.paint.color = Color.LTGRAY
        }
    }

    override fun draw(canvas: Canvas?) {
        if (powerUpType != "POWER_BALL") {
            canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
                this.radius.toFloat(), this.paint)
        } else {
            drawRhombus(canvas, this.paint, this.posX, this.posY, this.width)
        }

    }

    /**
     * Update method for updating position (etc) of this object every frame.
     */
    override fun update() {
        this.posY += PowerUpManager.powerUpFallSpeed
        // Check collision with paddle
        if(this.posX < (GameManager.paddle!!.posX+GameManager.paddle!!.width) && (this.posX+this.width > GameManager.paddle!!.posX)){
            if(this.posY < (GameManager.paddle!!.posY+GameManager.paddle!!.height) && (this.posY+this.height > GameManager.paddle!!.posY)){
                this.collidedWithPaddle = true
                PowerUpManager.activatePowerUp(this.powerUpType)
            }
        }
    }

    /**
     * Draws a rhombus/diamond on canvas
     */
    private fun drawRhombus(canvas: Canvas?, paint: Paint?, x: Int, y: Int, width: Int) {
        val halfWidth = width / 2
        val path = Path()
        path.moveTo(x.toFloat(), (y + halfWidth).toFloat()) // Top
        path.lineTo((x - halfWidth).toFloat(), y.toFloat()) // Left
        path.lineTo(x.toFloat(), (y - halfWidth).toFloat()) // Bottom
        path.lineTo((x + halfWidth).toFloat(), y.toFloat()) // Right
        path.lineTo(x.toFloat(), (y + halfWidth).toFloat()) // Back to Top
        path.close()
        canvas?.drawPath(path, paint!!)
    }
}