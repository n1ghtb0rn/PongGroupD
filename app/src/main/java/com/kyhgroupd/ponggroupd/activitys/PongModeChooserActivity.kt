package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
        buttonColorChange()

        binder.btnSinglePlayer.setOnClickListener {
            GameManager.pongPlayerMode = 1
            buttonColorChange()
        }

        binder.btnTwoPlayer.setOnClickListener {
            GameManager.pongPlayerMode = 2
            buttonColorChange()
        }

        binder.btnEasy.setOnClickListener {
            GameManager.pongDifficultyLevel = "easy"
            buttonColorChange()
        }

        binder.btnMedium.setOnClickListener {
            GameManager.pongDifficultyLevel = "medium"
            buttonColorChange()
        }

        binder.btnHard.setOnClickListener {
            GameManager.pongDifficultyLevel = "hard"
            buttonColorChange()
        }

        binder.btnStartGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            GameManager.shouldReset = true
            startActivity(intent)
        }
    }

    private fun buttonColorChange() {
        val pressedColor = Color.LTGRAY
        val unpressedColor = Color.WHITE
        when (GameManager.pongPlayerMode) {
            1 -> {
                binder.btnSinglePlayer.setBackgroundColor(pressedColor)
                binder.btnTwoPlayer.setBackgroundColor(unpressedColor)
            }
            2 -> {
                binder.btnSinglePlayer.setBackgroundColor(unpressedColor)
                binder.btnTwoPlayer.setBackgroundColor(pressedColor)
            }
        }
        when (GameManager.pongDifficultyLevel) {
            "easy" -> {
                binder.btnEasy.setBackgroundColor(pressedColor)
                binder.btnMedium.setBackgroundColor(unpressedColor)
                binder.btnHard.setBackgroundColor(unpressedColor)
            }
            "medium" -> {
                binder.btnEasy.setBackgroundColor(unpressedColor)
                binder.btnMedium.setBackgroundColor(pressedColor)
                binder.btnHard.setBackgroundColor(unpressedColor)
            }
            "hard" -> {
                binder.btnEasy.setBackgroundColor(unpressedColor)
                binder.btnMedium.setBackgroundColor(unpressedColor)
                binder.btnHard.setBackgroundColor(pressedColor)
            }
        }
    }
}