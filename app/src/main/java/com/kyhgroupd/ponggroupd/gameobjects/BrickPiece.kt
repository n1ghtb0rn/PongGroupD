package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.*
import com.kyhgroupd.ponggroupd.GameManager

/**
 * BrickPiece class inherits from GameObject class
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 */
open class BrickPiece(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    var speedX: Int = GameManager.pieceSpeed
    var speedY: Int = GameManager.pieceSpeed
    var lifetime: Int = GameManager.pieceLifetime

    init {
        width = ((GameManager.screenSizeX / GameManager.bricksPerRow)/ GameManager.pieceWidthFactor).toInt()
        height = (width / GameManager.brickHeightRatio)
    }

    /**
     * A method for drawing this object on the canvas every frame.
     *
     * @param canvas Canvas object
     */
    override fun draw(canvas: Canvas?){
        if(GameManager.useColors){
            canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
        }
        else{
            canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.grayPaint)
        }
    }

    /**
     * Update method for updating position (etc) of this object every frame.
     */
    override fun update(){
        this.posX += this.speedX
        this.posY += this.speedY
        this.lifetime--
    }

}