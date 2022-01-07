package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.databinding.ActivityPongModeChooserBinding

class PongModeChooserActivity : AppCompatActivity() {

    private lateinit var binder : ActivityPongModeChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityPongModeChooserBinding.inflate(layoutInflater)
        setContentView(binder.root)

        var modeSelected = false
        var difficultySelected = false

        binder.btnSinglePlayer.setOnClickListener{
            GameManager.pongPlayerMode = 1
            modeSelected = true
            }

        binder.btnTwoPlayer.setOnClickListener{
            GameManager.pongPlayerMode = 2
            modeSelected = true
            }

        binder.btnEasy.setOnClickListener {
            GameManager.pongDifficultyLevel = "easy"
            difficultySelected = true
        }

        binder.btnMedium.setOnClickListener {
            GameManager.pongDifficultyLevel = "medium"
            difficultySelected = true
        }

        binder.btnHard.setOnClickListener {
            GameManager.pongDifficultyLevel = "hard"
            difficultySelected = true
        }

        binder.btnStartGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            if (modeSelected && difficultySelected) {
                GameManager.shouldReset = true
                startActivity(intent)
            } else {
                Toast.makeText(this,"select one!",Toast.LENGTH_LONG).show()
            }

        }

    }
}