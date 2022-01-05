package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kyhgroupd.ponggroupd.DataManager
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.PlayerScore
import com.kyhgroupd.ponggroupd.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when(GameManager.gameMode){
            "golf" -> {
                binding.tvGameOver.text = "WIN"
                binding.tvScore.text = "SCORE: " + GameManager.score
            }
            "breakout" -> {
                binding.tvGameOver.text = "GAME OVER"
                binding.tvScore.text = "SCORE: " + GameManager.score
            }
            "pong" -> {
                binding.tvScore.text = "Player 1 Score: " + GameManager.score
                binding.tvScorePlayer2.text = "Player 2 Score: " + GameManager.player2Score
                binding.tvScorePlayer2.visibility = View.VISIBLE
                binding.saveGroup.visibility = View.INVISIBLE
                if(GameManager.score > GameManager.player2Score){
                    binding.tvGameOver.text = "PLAYER 1 WINS"
                } else {
                    binding.tvGameOver.text = "PLAYER 2 WINS"
                }
            }
        }

        binding.btnSave.setOnClickListener{
            val playerName = binding.etUsername.text.toString()
            if(playerName.trim().length > 0){
                GameManager.playerName = playerName
                val playerScore = PlayerScore(playerName, GameManager.score)
                if(GameManager.gameMode == "breakout") {
                    DataManager.saveScore(playerScore)
                }
                binding.btnSave.isEnabled = false
                binding.etUsername.isEnabled = false
                binding.tvScoreSaved.isVisible = true
            }
        }

        binding.btnMainMenu.setOnClickListener{
            Intent(this, MainActivity :: class.java)
                .apply { startActivity(this) }
        }

        binding.btnPlayAgain.setOnClickListener{
            Intent(this, GameActivity :: class.java)
                .apply { startActivity(this) }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnSave.isEnabled = true
        binding.tvScoreSaved.isVisible = false
        binding.etUsername.setText(GameManager.playerName)
        GameManager.context = null
    }

    //Override built-in back button to return to main activity
    override fun onBackPressed() {
        Intent(this, MainActivity::class.java)
            .apply { startActivity(this) }
    }
}
