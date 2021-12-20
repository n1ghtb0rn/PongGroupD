package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.GameView
import com.kyhgroupd.ponggroupd.SoundManager

class GameActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GameView(this))
    }

    fun gameOver() {
        Intent(this, GameOverActivity :: class.java )
            .apply { startActivity(this) }
    }

    fun pongWin() {
        Intent(this, PongWinActivity :: class.java )
            .apply { startActivity(this) }
    }

    override fun onPause() {
        super.onPause()
        GameManager.isPaused = true
        SoundManager.pauseMusic()
    }

    override fun onResume() {
        super.onResume()
        GameManager.isPaused = false
        SoundManager.resumeMusic()
    }

    //Function to update XML-views (call from SurfaceView/Draw-thread)
    //Not used at the moment because UI is drawn from canvas
    fun updateUI(){
        // "this@BreakoutActivity" only needed when called from Fragment
        this@GameActivity.runOnUiThread(Runnable {

            //Example:
            //myTextView.text = "Hello, World!"

        })
    }
}