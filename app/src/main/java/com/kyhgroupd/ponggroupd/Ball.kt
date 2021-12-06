package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Ball(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0
    var speedX: Int = -5
    var speedY: Int = -5

    init {
        radius = DataManager.screenSizeX / 50
        rect = Rect(startX, startY, startX+(radius*2), startY+(radius*2))
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
    }

    fun collidingWith(): GameObject? {
        print("12345")
        for (gameObject in DataManager.gameObjects) {
            if (this.rect.intersect(gameObject.rect)) {
                print(gameObject)
                return gameObject
            }
        }
        return null
    }

    override fun update(){
        posX += speedX
        posY += speedY

        //Screen border check
        if(this.posY < 0+this.radius){
            this.speedY = 5
        }
        if(this.posY > DataManager.screenSizeY-this.radius){
            this.speedY = -5
        }
        if(this.posX < 0+this.radius){
            this.speedX = 5
        }
        if(this.posX > DataManager.screenSizeX-this.radius){
            this.speedX = -5
        }
        var gameObject: GameObject? = this.collidingWith()
        if (gameObject != null) {
            speedY *= -1
        }
    }
}