package com.kyhgroupd.ponggroupd

import android.graphics.Canvas

open class Brick(startX: Int, startY: Int, color: Int ): GameObject(startX, startY, color) {

    var width: Int = 0
    var height: Int = 0

    init {
        width = DataManager.screenSizeX/15
        height = width/3
    }

    fun destroy(){
        //TODO...
        //Sound effect?
    }

    override fun draw(canvas: Canvas?){
        canvas?.drawRect(this.posX.toFloat(), this.posY.toFloat(), posX + this.width.toFloat(), posY + this.height.toFloat(), this.paint)
    }

    override fun update(){

    }

}