package com.kyhgroupd.ponggroupd.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.databinding.ActivityGameOverBinding
import com.kyhgroupd.ponggroupd.databinding.ActivityPongWinBinding

class PongWinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPongWinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPongWinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvScorePlayer1.text = "PLAYER 1 SCORE: " + GameManager.score
        binding.tvScorePlayer2.text = "PLAYER 2 SCORE: " + GameManager.player2Score

    }
}