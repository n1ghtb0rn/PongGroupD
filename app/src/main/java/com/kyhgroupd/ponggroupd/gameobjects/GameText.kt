package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.R

@RequiresApi(Build.VERSION_CODES.O)
open class GameText(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {
    var textString: String = ""

    init {
        this.textString = ""
        this.paint.textSize = GameManager.textSize
        this.paint.typeface = ResourcesCompat.getFont(
            GameManager.context as AppCompatActivity,
            R.font.aldrich
        )
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawText(textString, startX.toFloat(), startY.toFloat(), this.paint)
    }

    override fun update() {

    }

}