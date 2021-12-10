package com.kyhgroupd.ponggroupd

import android.graphics.*

open class BrickPiece(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    var speedX: Int = GameManager.pieceSpeed
    var speedY: Int = GameManager.pieceSpeed
    var lifetime: Int = GameManager.pieceLifetime

    init {
        width = ((GameManager.screenSizeX/GameManager.bricksPerColumn)/GameManager.pieceWidthFactor).toInt()
        height = (width/GameManager.brickWidthRatio)
    }

    override fun draw(canvas: Canvas?){
        if(GameManager.useColors){
            canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
        }
        else{
            val grayPaint = Paint()
            grayPaint.color = GameManager.paddleColor
            grayPaint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(), GameManager.gradientColor, grayPaint.color, Shader.TileMode.CLAMP)
            canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), grayPaint)
        }
    }

    override fun update(){
        this.posX += this.speedX
        this.posY += this.speedY
        this.lifetime--
    }

}