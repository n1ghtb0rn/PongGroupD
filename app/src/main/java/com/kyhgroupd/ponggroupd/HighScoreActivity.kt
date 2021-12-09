package com.kyhgroupd.ponggroupd

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyhgroupd.ponggroupd.databinding.ActivityHighScoreBinding
import com.kyhgroupd.ponggroupd.databinding.ActivityMainBinding

class HighScoreActivity : AppCompatActivity() {

    private lateinit var binder : ActivityHighScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityHighScoreBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        this.updateRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(){
        val scoreList = DataManager.loadScoreList()

        this.binder.rvScoreList.layoutManager = LinearLayoutManager(applicationContext)
        this.binder.rvScoreList.itemAnimator = DefaultItemAnimator()

        val adapter = CustomAdapter(scoreList)
        this.binder.rvScoreList.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}