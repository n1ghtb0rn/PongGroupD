package com.kyhgroupd.ponggroupd

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity

object SoundManager {

    var context: AppCompatActivity? = null

    var musicPlayer: MediaPlayer? = null

    var destroyBrickPlayer: MediaPlayer? = null
    var ballBouncePlayer: MediaPlayer? = null

    fun init(context: AppCompatActivity){
        this.context = context
        this.musicPlayer = MediaPlayer.create(context, R.raw.music)

        this.destroyBrickPlayer = MediaPlayer.create(SoundManager.context, R.raw.destroy_brick)
        this.ballBouncePlayer = MediaPlayer.create(SoundManager.context, R.raw.ball_bounce)
    }

    fun playDestroyBrickSFX(){
        this.destroyBrickPlayer!!.start()
    }

    fun playBallBounceSFX(){
        this.ballBouncePlayer!!.start()
    }

    fun playMusic(){
        musicPlayer?.start()
        musicPlayer?.isLooping = true
    }

}