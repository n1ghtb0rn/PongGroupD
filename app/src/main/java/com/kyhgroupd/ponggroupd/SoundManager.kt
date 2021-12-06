package com.kyhgroupd.ponggroupd

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity

object SoundManager {

    var context: AppCompatActivity? = null

    fun playDestroyBrickSFX(){
        if(context == null){
            return
        }
        val mediaPlayer = MediaPlayer.create(context, R.raw.destroy_brick)
        mediaPlayer!!.start()
    }

}