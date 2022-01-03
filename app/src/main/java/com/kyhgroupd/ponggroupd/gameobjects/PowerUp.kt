package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import com.kyhgroupd.ponggroupd.GameManager

class PowerUp(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0

    init {
        radius = GameManager.referenceBrick!!.height
        width = radius*2
        height = radius*2
    }

    override fun draw(canvas: Canvas?) {
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(radius)).toFloat(),
            (posY+(radius)).toFloat(), GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
    }

    override fun update() {
        this.posY += GameManager.powerUpFallSpeed
    }


}