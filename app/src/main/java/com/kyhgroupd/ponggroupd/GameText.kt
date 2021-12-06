package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Typeface

class GameText(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {
    var textString: String = ""

    init {
        this.textString = ""
        this.paint.textSize = DataManager.textSize
        this.paint.setTypeface(Typeface.MONOSPACE)
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawText(textString, startX.toFloat(), startY.toFloat(), this.paint)
    }

    override fun update() {

    }

}