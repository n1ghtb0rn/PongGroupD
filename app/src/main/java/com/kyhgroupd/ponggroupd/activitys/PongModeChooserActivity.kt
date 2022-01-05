package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.databinding.ActivityPongModeChooserBinding

class PongModeChooserActivity : AppCompatActivity() {

    private lateinit var binder : ActivityPongModeChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityPongModeChooserBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.btnSinglePlayer.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            GameManager.shouldReset = true
            GameManager.pongPlayerMode = 1
            startActivity(intent)
        }

        binder.btnTwoPlayer.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            GameManager.shouldReset = true
            GameManager.pongPlayerMode = 2
            startActivity(intent)
        }
    }
}