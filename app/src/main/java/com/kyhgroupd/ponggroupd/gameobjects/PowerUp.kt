package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.PowerUpManager
import com.kyhgroupd.ponggroupd.R
import com.kyhgroupd.ponggroupd.UIManager

class PowerUp(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0

    //Power Up info
    var collidedWithPaddle = false
    var powerUpType = "POWER_BALL"
    val labelPaint = Paint()

    init {
        radius = GameManager.referenceBrick!!.height
        width = radius*2
        height = radius*2

        this.powerUpType = PowerUpManager.generatePowerUpType()
        this.labelPaint.color = PowerUpManager.labelColor
        this.labelPaint.textSize = (this.radius*1.5).toFloat()
        this.paint.textAlign = Paint.Align.CENTER
        this.labelPaint.typeface = ResourcesCompat.getFont(
            GameManager.context as AppCompatActivity,
            R.font.aldrich
        )
    }

    override fun draw(canvas: Canvas?) {
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(radius)).toFloat(),
            (posY+(radius)).toFloat(), GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
        //Label draw
        canvas?.drawText(powerUpType.first().toString(), ((posX+(this.radius*0.4)).toFloat()), ((posY+(this.radius*1.4)).toFloat()), this.labelPaint)
    }

    override fun update() {
        this.posY += PowerUpManager.powerUpFallSpeed
        if(this.posX < (GameManager.paddle!!.posX+GameManager.paddle!!.width) && (this.posX+this.width > GameManager.paddle!!.posX)){
            if(this.posY < (GameManager.paddle!!.posY+GameManager.paddle!!.height) && (this.posY+this.height > GameManager.paddle!!.posY)){
                this.collidedWithPaddle = true
                PowerUpManager.activatePowerUp(this.powerUpType)
            }
        }
    }


}