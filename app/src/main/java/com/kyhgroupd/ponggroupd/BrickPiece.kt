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
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(), GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
    }

    override fun update(){
        this.posX += this.speedX
        this.posY += this.speedY
        lifetime--
    }

}