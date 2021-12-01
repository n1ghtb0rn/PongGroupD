package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Paint

class Ball(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0
    var speedX: Int = -5
    var speedY: Int = -5

    init {
        radius = DataManager.screenSizeX / 50
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(this.posX.toFloat(), this.posY.toFloat(), this.radius.toFloat(), this.paint)
    }

    override fun update(){
        posX += speedX
        posY += speedY

        //Screen boarder check
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
    }
}