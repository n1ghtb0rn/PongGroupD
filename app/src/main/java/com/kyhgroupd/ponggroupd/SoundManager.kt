package com.kyhgroupd.ponggroupd

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity

object SoundManager {

    var context: AppCompatActivity? = null

    var musicPlayer: MediaPlayer? = null

    var destroyBrickPlayer: MediaPlayer? = null
    var ballBouncePlayer: MediaPlayer? = null
    var loseLifePlayer: MediaPlayer? = null
    var gameOverPlayer: MediaPlayer? = null

    fun init(context: AppCompatActivity){
        this.context = context
        this.musicPlayer = MediaPlayer.create(context, R.raw.music)

        this.destroyBrickPlayer = MediaPlayer.create(SoundManager.context, R.raw.destroy_brick)
        this.ballBouncePlayer = MediaPlayer.create(SoundManager.context, R.raw.ball_bounce)
        this.loseLifePlayer = MediaPlayer.create(SoundManager.context, R.raw.lose_life)
        this.gameOverPlayer = MediaPlayer.create(SoundManager.context, R.raw.game_over)
    }

    fun playDestroyBrickSFX(){
        this.destroyBrickPlayer!!.start()
    }

    fun playBallBounceSFX(){
        this.ballBouncePlayer!!.start()
    }

    fun playLoseLifeSFX(){
        this.loseLifePlayer!!.start()
    }

    fun playGameOverSFX(){
        this.gameOverPlayer!!.start()
    }

    fun playMusic(){
        this.musicPlayer!!.start()
        this.musicPlayer!!.isLooping = true
    }

    fun resetMusic(){
        this.musicPlayer!!.stop()
        this.musicPlayer = MediaPlayer.create(context, R.raw.music)
        this.playMusic()
    }

    fun pauseMusic(){
        this.musicPlayer!!.pause()
    }

    fun resumeMusic(){
        this.musicPlayer!!.start()
    }
}