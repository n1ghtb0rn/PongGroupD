package com.kyhgroupd.ponggroupd

import android.graphics.*

open class BrickPiece(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    var speedX: Int = DataManager.pieceSpeed
    var speedY: Int = DataManager.pieceSpeed
    var lifetime: Int = DataManager.pieceLifetime

    init {
        width = ((DataManager.screenSizeX/DataManager.bricksPerColumn)/2.5).toInt()
        height = (width/DataManager.brickWidthRatio)
    }

    override fun draw(canvas: Canvas?){
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(), DataManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
    }

    override fun update(){
        this.posX += this.speedX
        this.posY += this.speedY
        lifetime--
    }

}