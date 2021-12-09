package com.kyhgroupd.ponggroupd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.kyhgroupd.ponggroupd.databinding.ActivityHighScoreBinding
import com.kyhgroupd.ponggroupd.databinding.ActivityMainBinding

class HighScoreActivity : AppCompatActivity() {

    private lateinit var binder : ActivityHighScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityHighScoreBinding.inflate(layoutInflater)
        setContentView(binder.root)

        val scoreList = DataManager.loadScoreList()
        val listItems = mutableListOf<Int>()
        for(playerScore in scoreList){
            listItems.add(playerScore.score)
        }

        binder.lvScoreList.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)

        binder.btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}