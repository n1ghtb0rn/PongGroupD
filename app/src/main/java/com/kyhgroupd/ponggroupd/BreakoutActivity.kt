package com.kyhgroupd.ponggroupd

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi

class BreakoutActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GameView(this))
    }

    public fun gameOver() {
        Intent(this, GameOverActivity :: class.java)
            .apply { startActivity(this) }
    }

    override fun onPause() {
        super.onPause()
        SoundManager.pauseMusic()
    }

    override fun onResume() {
        super.onResume()
        SoundManager.resumeMusic()
    }
}