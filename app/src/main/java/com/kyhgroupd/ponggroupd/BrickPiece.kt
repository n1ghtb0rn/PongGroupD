package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

open class BrickPiece(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    var speedX: Int = DataManager.pieceSpeed
    var speedY: Int = DataManager.pieceSpeed
    var lifetime: Int = DataManager.pieceLifetime

    init {
        width = (DataManager.screenSizeX/DataManager.bricksPerColumn)/4
        height = (width/DataManager.brickWidthRatio)
    }

    override fun draw(canvas: Canvas?){
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
    }

    override fun update(){
        this.posX += this.speedX
        this.posY += this.speedY
        lifetime--
        if(lifetime <= 0){
            //TODO: Fix remove crash (urgent)
            //DataManager.pieceObjects.remove(this)
        }
    }

}