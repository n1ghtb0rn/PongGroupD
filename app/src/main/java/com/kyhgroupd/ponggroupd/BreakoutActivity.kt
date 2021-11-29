package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.kyhgroupd.ponggroupd.databinding.ActivityBreakoutBinding

class BreakoutActivity : AppCompatActivity(), SurfaceHolder.Callback, View.OnTouchListener {

    private lateinit var binder : ActivityBreakoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityBreakoutBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.surfaceView.holder.addCallback(this)

        binder.surfaceView.setOnTouchListener(this)
        init()

    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        if (view !is SurfaceView) {
            return false
        }
        draw()
        return true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    private fun init() {
        var ball : Ball = Ball(100,100, Color.WHITE)
        GameManager.gameObjects.add(ball)
    }

    private fun draw(){
        val canvas: Canvas? = binder.surfaceView.holder.lockCanvas()
        val surfaceBackground = Paint()
        surfaceBackground.color = Color.RED

        canvas?.drawRect(0f, 0f, binder.surfaceView.width.toFloat(), binder.surfaceView.height.toFloat(), surfaceBackground)

        for (gameObject in GameManager.gameObjects) {
            gameObject.draw(canvas)
            println("$gameObject.posX / $gameObject.posY")
        }

        binder.surfaceView.holder.unlockCanvasAndPost(canvas)
        binder.surfaceView.setZOrderOnTop(true)
    }
}