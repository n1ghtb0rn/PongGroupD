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

        val player: Int = intent.getIntExtra("player",0)
        if (player == 1) {
            binding.tvPongWin.text = "PLAYER 1 WINS"
        } else if (player == 2) {
            binding.tvPongWin.text = "PLAYER 2 WINS"
        }
        binding.tvScorePlayer1.text = "PLAYER 1 SCORE: " + GameManager.score
        binding.tvScorePlayer2.text = "PLAYER 2 SCORE: " + GameManager.player2Score

    }
}