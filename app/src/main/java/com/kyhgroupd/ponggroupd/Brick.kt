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
        canvas?.drawRect(this.posX.toFloat()+1, this.posY.toFloat()+1, posX + this.width.toFloat()-2, posY + this.height.toFloat()-2, this.paint)
    }

    override fun update(){

    }

}