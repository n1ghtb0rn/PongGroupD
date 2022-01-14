package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class ComboText(startX: Int, startY: Int, color: Int, comboValue: Int): GameText(startX, startY, color) {

    var lifetime: Int = 20
    val textSizeIncrease = 2

    init{
        this.textString = "COMBO! +$comboValue"

        this.paint.color = Color.GREEN
        this.paint.textAlign = Paint.Align.CENTER

        this.grayPaint.textAlign = Paint.Align.CENTER
    }

    override fun update(){
        this.paint.textSize += textSizeIncrease
        this.grayPaint.textSize += textSizeIncrease

        this.lifetime--
    }
}