package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kyhgroupd.ponggroupd.DataManager
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.PlayerScore
import com.kyhgroupd.ponggroupd.R
import com.kyhgroupd.ponggroupd.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set UI text
        when(GameManager.gameMode){
            "golf" -> {
                if(GameManager.win){ // If user has finished all levels
                    binding.tvGameOver.text = getString(R.string.win)
                } else {
                    binding.tvGameOver.text = getString(R.string.game_over)
                }
                binding.tvScore.text = getString(R.string.your_score, GameManager.score)
            }
            "breakout" -> {
                binding.tvGameOver.text = getString(R.string.game_over)
                binding.tvScore.text = getString(R.string.your_score, GameManager.score)
            }
            "pong" -> {
                if(GameManager.score > GameManager.player2Score){
                    binding.tvGameOver.text = getString(R.string.player1_win)
                } else {
                    binding.tvGameOver.text = getString(R.string.player2_win)
                }
                binding.tvScore.text = getString(R.string.player1_score, GameManager.score)
                binding.tvScorePlayer2.text = getString(R.string.player2_score, GameManager.player2Score)
                binding.tvScorePlayer2.visibility = View.VISIBLE

                if(GameManager.pongPlayerMode == 2){ // If pong 2 player mode, user can't save score
                    binding.saveGroup.visibility = View.INVISIBLE
                } else {
                    binding.tvGamesWon.text = getString(R.string.games_won,GameManager.gamesWon)
                    binding.tvGamesWon.visibility = View.VISIBLE // If pong single player mode, show matches won
                }
            }
        }

        binding.btnSave.setOnClickListener{
            val playerName = binding.etUsername.text.toString()
            if(playerName.trim().isNotEmpty()){
                GameManager.playerName = playerName
                val playerScore = if(GameManager.gameMode == "pong"){
                    PlayerScore(playerName, GameManager.gamesWon)
                } else {
                    PlayerScore(playerName, GameManager.score)
                }
                DataManager.saveScore(playerScore, GameManager.gameMode)
                // Disable EditText & save button
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
        binding.etUsername.setText(GameManager.playerName) // Username is saved from previous input
        GameManager.context = null
    }

    //Override built-in back button to return to main activity
    override fun onBackPressed() {
        Intent(this, MainActivity::class.java)
            .apply { startActivity(this) }
    }
}
