package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.databinding.ActivityPongModeChooserBinding

class PongModeChooserActivity : AppCompatActivity() {

    private lateinit var binder: ActivityPongModeChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityPongModeChooserBinding.inflate(layoutInflater)
        setContentView(binder.root)

        GameManager.pongPlayerMode = 1
        GameManager.pongDifficultyLevel = "medium"

        binder.btnSinglePlayer.setOnClickListener {
            GameManager.pongPlayerMode = 1
        }

        binder.btnTwoPlayer.setOnClickListener {
            GameManager.pongPlayerMode = 2
        }

        binder.btnEasy.setOnClickListener {
            GameManager.pongDifficultyLevel = "easy"
        }

        binder.btnMedium.setOnClickListener {
            GameManager.pongDifficultyLevel = "medium"
        }

        binder.btnHard.setOnClickListener {
            GameManager.pongDifficultyLevel = "hard"
        }

        binder.btnStartGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            GameManager.shouldReset = true
            startActivity(intent)
        }
    }
}