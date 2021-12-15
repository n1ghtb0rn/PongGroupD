package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.PointF
import android.graphics.Shader
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.activitys.BreakoutActivity

class Paddle(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    init {
        width = GameManager.screenSizeX / GameManager.paddleWidthFactor
        height = width / GameManager.paddleHeightFactor
        posX -= width / 2
    }

    override fun draw(canvas: Canvas?) {
        this.paint.shader = LinearGradient(
            posX.toFloat(),
            posY.toFloat(),
            (posX + (height / 2)).toFloat(),
            (posY + (height / 2)).toFloat(),
            GameManager.gradientColor,
            this.paint.color,
            Shader.TileMode.CLAMP
        )
        canvas?.drawRect(
            posX.toFloat(),
            posY.toFloat(),
            posX + width.toFloat(),
            posY + height.toFloat(),
            this.paint
        )
    }

    override fun update() {
        if (GameManager.gameMode != "pong") {
            if (GameManager.event != null) {
                val paddleY = GameManager.paddle?.posY
                if (paddleY != null) {
                    var touchY = GameManager.event!!.y.toInt()
                    val offset = GameManager.paddleTouchOffsetY
                    //Subtract top bar and and half paddle height
                    touchY -= GameManager.topBarHeight + height / 2
                    //Check if touch is in range of paddle
                    if (touchY > paddleY - offset && touchY < paddleY + offset) {
                        posX = GameManager.event!!.x.toInt() - (width / 2)
                    }
                    //Check if paddle is out of bounds
                    if (posX < 0) {
                        posX = 0
                    }
                    if (posX > GameManager.screenSizeX - width) {
                        posX = GameManager.screenSizeX - width
                    }
                }
                GameManager.event = null
            }
        } else {
            var mActivePointers = mutableListOf<PointF>()
            if (GameManager.event != null) {
                var maskedAction: Int = GameManager.event!!.actionMasked
                var pointerIndex: Int = GameManager.event!!.actionIndex
                var pointerId: Int = GameManager.event!!.getPointerId(pointerIndex)
                when (maskedAction) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.i("Touch events", "Action Down")
                        var f = PointF(0f, 0f)
                        f.x = GameManager.event!!.getX(pointerIndex)
                        f.y = GameManager.event!!.getY(pointerIndex)
                        mActivePointers.add(f)
                        Log.i("Touch events", "Action Down $mActivePointers")
                    }
                    MotionEvent.ACTION_POINTER_DOWN -> {
                        var f2 = PointF(0f, 0f)
                        f2.x = GameManager.event!!.getX(pointerIndex)
                        f2.y = GameManager.event!!.getY(pointerIndex)
                        mActivePointers.add(f2)
                        Log.i("Touch events", "Action Pointer Down: index $pointerIndex, $mActivePointers")
                    }
                    MotionEvent.ACTION_MOVE -> {
                        for (point in mActivePointers) {
                            point.x = GameManager.event!!.x
                            point.y = GameManager.event!!.y
                            if (point.y > GameManager.screenSizeY / 2) {
                                GameManager.paddle?.posX = point.x.toInt()
                            } else {
                                GameManager.paddle2?.posX = point.x.toInt()
                            }
                        }
                    }
                }

                // Check if paddle is out of bounds
                if (posX < 0) {
                    posX = 0
                }
                if (posX > GameManager.screenSizeX - width) {
                    posX = GameManager.screenSizeX - width
                }
                // Check if paddle2 is out of bounds
                if (GameManager.paddle2!!.posX < 0) {
                    GameManager.paddle2!!.posX = 0
                }
                if (GameManager.paddle2!!.posX > GameManager.screenSizeX - width) {
                    GameManager.paddle2!!.posX = GameManager.screenSizeX - width
                }
            }
            GameManager.event = null
        }
    }
}


