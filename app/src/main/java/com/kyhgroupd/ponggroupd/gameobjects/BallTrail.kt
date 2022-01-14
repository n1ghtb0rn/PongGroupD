package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.PowerUpManager

/**
 *
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 */
class BallTrail(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0
    val radiusDecrease = 2
    val colorAlpha = 150

    init {
        radius = GameManager.referenceBrick!!.height/2
        width = radius*2
        height = radius*2

        this.paint.alpha = this.colorAlpha
    }

    override fun draw(canvas: Canvas?) {
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(radius)).toFloat(),
            (posY+(radius)).toFloat(), GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
    }

    override fun update() {
        this.radius -= this.radiusDecrease
    }

}