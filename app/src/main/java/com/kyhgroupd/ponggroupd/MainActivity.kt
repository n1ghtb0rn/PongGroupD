package com.kyhgroupd.ponggroupd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kyhgroupd.ponggroupd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binder : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.btnPlayBreakout.setOnClickListener {
            val intent = Intent(this, BreakoutActivity::class.java)
            startActivity(intent)
        }

        binder.btnHighScore.setOnClickListener{
            val intent = Intent(this, HighScoreActivity::class.java)
            startActivity(intent)
        }
    }
}