package com.kyhgroupd.ponggroupd

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity

object SoundManager {

    var context: AppCompatActivity? = null

    var musicPlayer: MediaPlayer? = null

    var destroyBrickPlayer: MediaPlayer? = null
    var comboPlayer: MediaPlayer? = null
    var ballBouncePlayer: MediaPlayer? = null
    var loseLifePlayer: MediaPlayer? = null
    var gameOverPlayer: MediaPlayer? = null

    //Power ups
    var powerBallPlayer: MediaPlayer? = null
    var multiBallPlayer: MediaPlayer? = null

    fun init(context: AppCompatActivity){
        this.context = context

        //music player
        this.musicPlayer = MediaPlayer.create(context, R.raw.music)

        //generic sfx
        this.destroyBrickPlayer = MediaPlayer.create(SoundManager.context, R.raw.destroy_brick)
        this.comboPlayer = MediaPlayer.create(SoundManager.context, R.raw.combo)
        this.ballBouncePlayer = MediaPlayer.create(SoundManager.context, R.raw.ball_bounce)
        this.loseLifePlayer = MediaPlayer.create(SoundManager.context, R.raw.lose_life)
        this.gameOverPlayer = MediaPlayer.create(SoundManager.context, R.raw.game_over)

        //power ups sfx
        this.powerBallPlayer = MediaPlayer.create(SoundManager.context, R.raw.power_ball)
        this.multiBallPlayer = MediaPlayer.create(SoundManager.context, R.raw.multi_ball)
    }

    fun playDestroyBrickSFX(){
        this.playSFX("DESTROY_BRICK")
    }

    fun playComboSFX(){
        this.playSFX("COMBO")
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

    fun playPowerUpSFX(powerUpType: String){
        this.playSFX(powerUpType)
    }

    private fun playSFX(sfx: String){
        if(!GameManager.useSFX){
            return
        }
        when (sfx) {
            "DESTROY_BRICK" -> this.destroyBrickPlayer!!.start()
            "COMBO" -> this.comboPlayer!!.start()
            "BALL_BOUNCE" -> this.ballBouncePlayer!!.start()
            "LOSE_LIFE" -> this.loseLifePlayer!!.start()
            "GAME_OVER" -> this.gameOverPlayer!!.start()

            //power ups
            "POWER_BALL" -> this.powerBallPlayer!!.start()
            "MULTI_BALL" -> this.multiBallPlayer!!.start()
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