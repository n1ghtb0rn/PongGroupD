package com.kyhgroupd.ponggroupd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BreakoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GameView(this))
    }
}