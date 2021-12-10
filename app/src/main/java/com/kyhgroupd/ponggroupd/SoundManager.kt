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
        this.playSFX("DESTROY_BRICK")
    }

    fun playBallBounceSFX(){
        this.playSFX("BALL_BOUNCE")
    }

    fun playLoseLifeSFX(){
        this.playSFX("LOSE_LIFE")
    }

    fun playGameOverSFX(){
        this.playSFX("GAME_OVER")
    }

    private fun playSFX(sfx: String){
        if(!GameManager.useSFX){
            return
        }
        when (sfx) {
            "DESTROY_BRICK" -> this.destroyBrickPlayer!!.start()
            "BALL_BOUNCE" -> this.ballBouncePlayer!!.start()
            "LOSE_LIFE" -> this.loseLifePlayer!!.start()
            "GAME_OVER" -> this.gameOverPlayer!!.start()
        }
    }

    fun playMusic(){
        if(!GameManager.useMusic){
            return
        }
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
        if(!GameManager.useMusic){
            return
        }
        this.musicPlayer!!.start()
    }
}