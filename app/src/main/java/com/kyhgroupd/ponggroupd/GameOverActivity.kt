package com.kyhgroupd.ponggroupd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kyhgroupd.ponggroupd.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvScore.text = "SCORE: " + GameManager.score

        binding.btnSave.setOnClickListener{
            val playerName = binding.etUsername.text.toString()
            if(playerName.trim().length > 0){
                GameManager.playerName = playerName
                val playerScore = PlayerScore(playerName, GameManager.score)
                DataManager.saveScore(playerScore)
                binding.btnSave.isEnabled = false
                binding.etUsername.isEnabled = false
            }
        }

        binding.btnMainMenu.setOnClickListener{
            Intent(this, MainActivity :: class.java)
                .apply { startActivity(this) }
        }

        binding.btnPlayAgain.setOnClickListener{
            Intent(this, BreakoutActivity :: class.java)
                .apply { startActivity(this) }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnSave.isEnabled = true
        binding.etUsername.setText(GameManager.playerName)
        GameManager.context = null
    }

    //Override built-in back button to return to main activity
    override fun onBackPressed() {
        Intent(this, MainActivity::class.java)
            .apply { startActivity(this) }
    }
}
