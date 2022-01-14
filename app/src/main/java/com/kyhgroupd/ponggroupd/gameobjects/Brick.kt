package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.*
import com.kyhgroupd.ponggroupd.GameManager

/**
 * Inherits from GameObject class
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 * @param health Number of hits required to destroy brick
 * @param unbreakable Brick that cannot be destroyed
 */
open class Brick(startX: Int, startY: Int, color: Int, health: Int, unbreakable: Boolean ): GameObject(startX, startY, color) {

    // Breakout brick
    constructor(startX: Int, startY: Int, color: Int) : this(startX, startY, color, 1, false)

    // Golf-brick
    constructor(startX: Int, startY: Int, color: Int, health: Int) : this(startX, startY, color, health, false)

    // Unbreakable brick
    constructor(startX: Int, startY: Int, color: Int, unbreakable: Boolean) : this(startX, startY, color, 1, unbreakable)

    val borderPaint = Paint() // Border around each brick
    var health: Int = 1 // Default brick value
    var unbreakable = false // Default brick value

    // Base
    init {
        width = GameManager.screenSizeX / GameManager.bricksPerRow
        height = width / GameManager.brickHeightRatio
    }

    // Colors
    init {
        // Shader/LinearGradient creates a retro 3D feeling by combining the main color with the color white.
        // It creates a little triangle in the top left corner of the object.
        paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(),
            GameManager.gradientColor, paint.color, Shader.TileMode.CLAMP)

        // Grayscale mode
        grayPaint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(),
            GameManager.gradientColor, grayPaint.color, Shader.TileMode.CLAMP)

        borderPaint.color = Color.BLACK
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = (height / GameManager.borderStrokeWidthFactor).toFloat()
    }

    // Extra
    init {
        this.health = health
        this.unbreakable = unbreakable
    }

    /**
     * A method for drawing this object on the canvas every frame.
     *
     * @param canvas Canvas object
     */
    override fun draw(canvas: Canvas?){
        // Base
        if(GameManager.useColors || this.unbreakable){
            canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
        }
        else{ // Grayscale mode
            canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.grayPaint)
        }

        // Border
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), borderPaint)
    }

    /**
     * Update-method for updating position (etc) of this object every frame.
     */
    override fun update(){

    }

    /**
     * Shatters this brick into small pieces when destroyed.
     * Creates 8 small pieces that move in different directions.
     */
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

    /**
     * Changes color of bricks according to health (number of hits remaining to destroy brick).
     * Used only in golf game mode.
     */
    fun changeColor(){
        when {
            this.unbreakable -> {
                this.paint.color = Color.DKGRAY
                this.grayPaint.color = Color.rgb(25, 25, 25)
            }
            this.health >= 3 -> {
                this.paint.color = Color.rgb(150, 0, 0)     //red
                this.grayPaint.color = Color.rgb(100, 100, 100)
            }
            this.health == 2 -> {
                this.paint.color = Color.rgb(150, 150, 0)   //yellow
                this.grayPaint.color = Color.rgb(140, 140, 140)
            }
            this.health <= 1 -> {
                this.paint.color = Color.rgb(0, 150, 0)     //green
                this.grayPaint.color = Color.rgb(180, 180, 180)
            }
        }
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(),
            GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        this.grayPaint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(),
            GameManager.gradientColor, grayPaint.color, Shader.TileMode.CLAMP)
    }

}