package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Inherits from GameText class which inherits from GameObject class
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 * @param comboValue Number of extra points for combo type
 */
@RequiresApi(Build.VERSION_CODES.O)
class ComboText(startX: Int, startY: Int, color: Int, comboValue: Int): GameText(startX, startY, color) {

    var lifetime: Int = 20 //
    val textSizeIncrease = 2 // Combo text becomes increasingly bigger

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