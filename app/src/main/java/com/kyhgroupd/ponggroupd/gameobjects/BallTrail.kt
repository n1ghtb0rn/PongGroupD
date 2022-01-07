package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import com.kyhgroupd.ponggroupd.GameManager

class BallTrail(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0

    init {
        radius = GameManager.referenceBrick!!.height/2
        width = radius*2
        height = radius*2

        if(GameManager.useColors){
            this.paint.color = GameManager.ball!!.paint.color
        }
        else{
            this.paint.color = GameManager.ball!!.grayPaint.color
        }
        this.paint.alpha = 150
    }

    override fun draw(canvas: Canvas?) {
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(radius)).toFloat(),
            (posY+(radius)).toFloat(), GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
    }

    override fun update() {
        this.radius -= 2
    }

}