package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import com.kyhgroupd.ponggroupd.GameManager

/**
 * Inherits from GameObject class
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 */
class BallTrail(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0
    val radiusDecrease = 2
    val colorAlpha = 150 // Makes ball trail slightly transparent

    init {
        radius = GameManager.referenceBrick!!.height/2 // Trail gets same radius as ball
        width = radius*2 // and same width as ball
        height = radius*2 // and same height as ball

        this.paint.alpha = this.colorAlpha
    }

    override fun draw(canvas: Canvas?) {
        // Shader/LinearGradient creates a retro 3D feeling by combining the main color with the color white.
        // It creates a little triangle in the top left corner of the object.
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(radius)).toFloat(),
            (posY+(radius)).toFloat(), GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
    }

    override fun update() {
        this.radius -= this.radiusDecrease // Decrease radius every frame
    }

}