package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.*
import com.kyhgroupd.ponggroupd.GameManager

open class Brick(startX: Int, startY: Int, color: Int, health: Int, unbreakable: Boolean ): GameObject(startX, startY, color) {

    //Breakout brick
    constructor(startX: Int, startY: Int, color: Int) : this(startX, startY, color, 1, false)

    //Golf-brick
    constructor(startX: Int, startY: Int, color: Int, health: Int) : this(startX, startY, color, health, false)

    //Unbreakable-brick
    constructor(startX: Int, startY: Int, color: Int, unbreakable: Boolean) : this(startX, startY, color, 1, unbreakable)

    val borderPaint = Paint()
    var health: Int = 1 //Default brick value
    var unbreakable = false //Default brick value

    //Base
    init {
        width = GameManager.screenSizeX / GameManager.bricksPerRow
        height = width/ GameManager.brickHeightRatio
    }

    //Colors
    init {
        paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(),
            GameManager.gradientColor, paint.color, Shader.TileMode.CLAMP)

        grayPaint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(),
            GameManager.gradientColor, grayPaint.color, Shader.TileMode.CLAMP)

        borderPaint.color = Color.BLACK
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = (height/ GameManager.borderStrokeWidthFactor).toFloat()
    }

    //Extra
    init {
        this.health = health
        this.unbreakable = unbreakable
    }

    override fun draw(canvas: Canvas?){
        //Base
        if(GameManager.useColors || this.unbreakable){
            canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
        }
        else{
            canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.grayPaint)
        }

        //Border
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), borderPaint)
    }

    override fun update(){

    }

    //Shatter this brick into small pieces when destroyed
    fun destroy(){
        val piece1 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.paint.color)
        piece1.speedX = 0
        GameManager.pieceObjects.add(piece1)
        val piece2 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.paint.color)
        piece2.speedX = -GameManager.pieceSpeed
        GameManager.pieceObjects.add(piece2)
        val piece3 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.paint.color)
        piece3.speedY = 0
        piece3.speedX = -GameManager.pieceSpeed
        GameManager.pieceObjects.add(piece3)
        val piece4 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.paint.color)
        piece4.speedY = -GameManager.pieceSpeed
        piece4.speedX = -GameManager.pieceSpeed
        GameManager.pieceObjects.add(piece4)
        val piece5 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.paint.color)
        piece5.speedY = -GameManager.pieceSpeed
        piece5.speedX = 0
        GameManager.pieceObjects.add(piece5)
        val piece6 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.paint.color)
        piece6.speedY = -GameManager.pieceSpeed
        piece6.speedX = GameManager.pieceSpeed
        GameManager.pieceObjects.add(piece6)
        val piece7 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.paint.color)
        piece7.speedY = 0
        piece7.speedX = GameManager.pieceSpeed
        GameManager.pieceObjects.add(piece7)
        val piece8 = BrickPiece(this.posX+(this.width/2), this.posY+(this.height/2), this.paint.color)
        GameManager.pieceObjects.add(piece8)
    }

    fun changeColor(){
        when {
            this.unbreakable -> {
                this.paint.color = Color.DKGRAY
            }
            this.health >= 3 -> {
                this.paint.color = Color.rgb(150, 0, 0)     //red
            }
            this.health == 2 -> {
                this.paint.color = Color.rgb(150, 150, 0)   //yellow
            }
            this.health <= 1 -> {
                this.paint.color = Color.rgb(0, 150, 0)     //green
            }
        }
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(),
            GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
    }

}