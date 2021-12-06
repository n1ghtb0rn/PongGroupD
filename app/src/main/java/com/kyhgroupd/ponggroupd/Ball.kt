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
            this.speedY = DataManager.ballSpeed
        }
        if(this.posY+this.height > DataManager.screenSizeY){
            this.speedY = -DataManager.ballSpeed
        }
        if(this.posX < 0){
            this.speedX = DataManager.ballSpeed
        }
        if(this.posX+this.width > DataManager.screenSizeX){
            this.speedX = -DataManager.ballSpeed
        }
        var gameObject: GameObject? = this.collidingWith()
        if (gameObject != null) {
            //Brick collision
            if(gameObject is Brick){
                if (this.posY < gameObject.posY+gameObject.height-(DataManager.ballSpeed*2) &&
                    this.posY+this.height > gameObject.posY+(DataManager.ballSpeed*2)) {
                    speedX *= -1
                } else {
                    speedY *= -1
                }
                DataManager.gameObjects.remove(gameObject)
            }
            //Paddle collision
            else if(gameObject is Paddle){
                if(this.posY+this.height > gameObject.posY && this.posY + this.height < gameObject.posY + DataManager.ballSpeed){
                    speedY *= -1
                } else {
                    speedX *= -1
                }
            }
        }
    }
}