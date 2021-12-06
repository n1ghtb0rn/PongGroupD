package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

open class Brick(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    val borderPaint = Paint()

    init {
        width = DataManager.screenSizeX/DataManager.bricksPerColumn
        height = width/DataManager.brickWidthRatio

        borderPaint.color = Color.GRAY
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = (height/5).toFloat()
    }

    override fun draw(canvas: Canvas?){
        //Base
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
        //Border
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), borderPaint)
    }

    override fun update(){

    }

    //Shatter this brick into small pieces when destroyed
    fun destroy(){
        val piece1 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.borderPaint.color)
        piece1.speedX = 0
        DataManager.pieceObjects.add(piece1)
        val piece2 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.borderPaint.color)
        piece2.speedX = -DataManager.pieceSpeed
        DataManager.pieceObjects.add(piece2)
        val piece3 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.borderPaint.color)
        piece3.speedY = 0
        piece3.speedX = -DataManager.pieceSpeed
        DataManager.pieceObjects.add(piece3)
        val piece4 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.borderPaint.color)
        piece4.speedY = -DataManager.pieceSpeed
        piece4.speedX = -DataManager.pieceSpeed
        DataManager.pieceObjects.add(piece4)
        val piece5 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.borderPaint.color)
        piece5.speedY = -DataManager.pieceSpeed
        piece5.speedX = 0
        DataManager.pieceObjects.add(piece5)
        val piece6 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.borderPaint.color)
        piece6.speedY = -DataManager.pieceSpeed
        piece6.speedX = DataManager.pieceSpeed
        DataManager.pieceObjects.add(piece6)
        val piece7 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.borderPaint.color)
        piece7.speedY = 0
        piece7.speedX = DataManager.pieceSpeed
        DataManager.pieceObjects.add(piece7)
        val piece8 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.borderPaint.color)
        DataManager.pieceObjects.add(piece8)
    }

}