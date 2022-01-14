package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kyhgroupd.ponggroupd.DataManager
import com.kyhgroupd.ponggroupd.GameManager
import com.kyhgroupd.ponggroupd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binder : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        // Play breakout
        binder.btnPlayBreakout.setOnClickListener {
            GameManager.gameMode = "breakout"
            val intent = Intent(this, GameActivity::class.java)
            GameManager.shouldReset = true
            startActivity(intent)
        }

        // Play pong
        binder.btnPlayPong.setOnClickListener{
            GameManager.gameMode = "pong"
            val intent = Intent(this, PongModeChooserActivity::class.java)
            startActivity(intent)
        }

        // Play golf
        binder.btnPlayGolf.setOnClickListener{
            GameManager.gameMode = "golf"
            val intent = Intent(this, GameActivity::class.java)
            GameManager.shouldReset = true
            startActivity(intent)
        }

        // Continue game, not usable when no game is active
        binder.btnContinue.setOnClickListener{
            if(GameManager.context != null){
                val intent = Intent(this, GameActivity::class.java)
                GameManager.shouldReset = false
                startActivity(intent)
            }
        }

        // Settings
        binder.btnSettings.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Highscore
        binder.btnHighScore.setOnClickListener{
            val intent = Intent(this, HighScoreActivity::class.java)
            startActivity(intent)
        }

        //Initiate DataManager
        DataManager.initiate(filesDir.toString())
    }

    override fun onResume() {
        super.onResume()

        this.loadSettings()

        // Disable continue button if no game is active
        if(GameManager.context == null){
            binder.btnContinue.alpha = 0.5f
            binder.btnContinue.isClickable = false
            return
        }
        binder.btnContinue.alpha = 1f
        binder.btnContinue.isClickable = true
    }

    private fun loadSettings(){
        val settingsList = DataManager.loadSettings()
        if(settingsList.size < GameManager.numberOfSettings){
            return
        }
        GameManager.useSFX = settingsList[0]
        GameManager.useMusic = settingsList[1]
        GameManager.useColors = settingsList[2]
    }

}