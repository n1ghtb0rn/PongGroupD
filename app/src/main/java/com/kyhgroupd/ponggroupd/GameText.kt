package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Typeface

class GameText(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {
    var textString: String = ""

    init {
        this.textString = ""
        this.paint.textSize = GameManager.textSize
        this.paint.setTypeface(Typeface.create("aldrich",Typeface.NORMAL))
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawText(textString, startX.toFloat(), startY.toFloat(), this.paint)
    }

    override fun update() {

    }

}