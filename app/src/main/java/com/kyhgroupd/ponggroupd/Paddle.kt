package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.view.MotionEvent

class Paddle(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var width: Int = 0
    var height: Int = 0


    init {
        width = DataManager.screenSizeX/5
        height = width/3
    }

    override fun draw(canvas: Canvas?){
        canvas?.drawRect(posX.toFloat(), posY.toFloat(),posX + width.toFloat(), posY + height.toFloat(), this.paint)
    }

    override fun update() {
        if (DataManager.event != null) {
            val paddleY = DataManager.paddle?.posY?.plus(300)
            if (paddleY != null) {

                val touchY = DataManager.event!!.y.toInt()
                val offset = 200

                //Check if touch is close to paddle
                if(touchY > paddleY - offset && touchY < paddleY + offset){
                    posX = DataManager.event!!.x.toInt() - (width / 2)
                }
                //Check if paddle is out of bounds
                if(posX < 0){
                    posX = 0
                }
                if(posX > DataManager.screenSizeX - width){
                    posX = DataManager.screenSizeX - width
                }
            }
            DataManager.event = null
        }
    }
}