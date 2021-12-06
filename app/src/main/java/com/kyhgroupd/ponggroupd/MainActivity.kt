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
            onButtonClicked()
        }
    }

    fun onButtonClicked() {
        intent = Intent(this, BreakoutActivity::class.java)
        startActivity(intent)
    }

    //Function to update XML-views (call from SurfaceView/Draw-thread)
    //Not used at the moment because UI is drawn from canvas
    fun updateUI(){
        this@MainActivity.runOnUiThread(Runnable {

            //Example
            //myTextView.text = "HEJ!"

        })
    }
}