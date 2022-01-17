package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.*

/**
 * Abstract class, all in-game objects inherit from this class
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 */
abstract class GameObject(var startX: Int, var startY: Int, color: Int ) {

    var posX: Int = 0
    var posY: Int = 0
    var width: Int = 0
    var height: Int = 0
    var paint = Paint()
    var grayPaint = Paint()

    init {
        this.posX = startX
        this.posY = startY

        //Default color
        this.paint.color = color
        //Grayscale color (if colors setting is turned off by the user)
        this.grayPaint.color = Color.GRAY
    }

    abstract fun draw(canvas: Canvas?)

    abstract fun update()
}