package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Ball(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0
    var speedX: Int = 0
    var speedY: Int = 0

    init {
        radius = DataManager.screenSizeX / 50
        width = radius*2
        height = radius*2
        speedX = DataManager.ballSpeed
        speedY = DataManager.ballSpeed
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
    }

    fun collidingWith(): GameObject? {
        for (gameObject in DataManager.gameObjects) {
            if (this.posX < gameObject.posX+gameObject.width && this.posX+this.width > gameObject.posX) {
                if (this.posY < gameObject.posY+gameObject.height && this.posY+this.height > gameObject.posY) {
                    return gameObject
                }
            }
        }
        return null
    }

    override fun update(){
        posX += speedX
        posY += speedY

        //Screen border check
        if(this.posY < 0){
            this.speedY *= -1
        }
        if(this.posY+this.height > DataManager.screenSizeY){
            this.speedY *= -1
        }
        if(this.posX < 0){
            this.speedX *= -1
        }
        if(this.posX+this.width > DataManager.screenSizeX){
            this.speedX *= -1
        }
        var gameObject: GameObject? = this.collidingWith()
        if (gameObject != null) {
            speedY *= -1
            if(gameObject is Brick){
                DataManager.gameObjects.remove(gameObject)
            }
        }
    }
}