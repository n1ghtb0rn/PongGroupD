package com.kyhgroupd.ponggroupd

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity

/**
 * Singleton class to manage sound effects and music.
 */
object SoundManager {

    var context: AppCompatActivity? = null

    // Media players
    var musicPlayer: MediaPlayer? = null

    var destroyBrickPlayer: MediaPlayer? = null
    var comboPlayer: MediaPlayer? = null
    var ballBouncePlayer: MediaPlayer? = null
    var loseLifePlayer: MediaPlayer? = null
    var gameOverPlayer: MediaPlayer? = null

    // Power ups
    var powerBallPlayer: MediaPlayer? = null
    var multiBallPlayer: MediaPlayer? = null

    fun init(context: AppCompatActivity){
        this.context = context

        // Music player
        this.musicPlayer = MediaPlayer.create(context, R.raw.music)

        // Generic sfx
        this.destroyBrickPlayer = MediaPlayer.create(SoundManager.context, R.raw.destroy_brick)
        this.comboPlayer = MediaPlayer.create(SoundManager.context, R.raw.combo)
        this.ballBouncePlayer = MediaPlayer.create(SoundManager.context, R.raw.ball_bounce)
        this.loseLifePlayer = MediaPlayer.create(SoundManager.context, R.raw.lose_life)
        this.gameOverPlayer = MediaPlayer.create(SoundManager.context, R.raw.game_over)

        // Power ups sfx
        this.powerBallPlayer = MediaPlayer.create(SoundManager.context, R.raw.power_ball)
        this.multiBallPlayer = MediaPlayer.create(SoundManager.context, R.raw.multi_ball)
    }

    /**
     * Plays 'destroy brick' sound effect via playSFX function
     */
    fun playDestroyBrickSFX(){
        this.playSFX("DESTROY_BRICK")
    }

    /**
     * Plays 'combo' sound effect via playSFX function
     */
    fun playComboSFX(){
        this.playSFX("COMBO")
    }

    /**
     * Plays 'ball bounce' sound effect via playSFX function
     */
    fun playBallBounceSFX(){
        this.playSFX("BALL_BOUNCE")
    }

    /**
     *
     * Plays 'lose life' sound effect via playSFX function
     */
    fun playLoseLifeSFX(){
        this.playSFX("LOSE_LIFE")
    }

    /**
     * Plays 'game over' sound effect via playSFX function
     */
    fun playGameOverSFX(){
        this.playSFX("GAME_OVER")
    }

    /**
     * Plays power up sound effects for each power up type by calling playSFX function
     */
    fun playPowerUpSFX(powerUpType: String){
        this.playSFX(powerUpType)
    }

    /**
     * Plays sound effects if the sound effect setting is switched on by the user.
     *
     * @param sfx String specifying which sound effect is played
     */
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

            // Power ups
            "POWER_BALL" -> this.powerBallPlayer!!.start()
            "MULTI_BALL" -> this.multiBallPlayer!!.start()
        }
    }

    /**
     * Plays game music if the music setting is switched on by the user.
     */
    fun playMusic(){
        if(!GameManager.useMusic){
            return
        }
        this.musicPlayer!!.start()
        this.musicPlayer!!.isLooping = true
    }

    /**
     * Pauses game music
     */
    fun pauseMusic(){
        this.musicPlayer!!.pause()
    }

    /**
     * Plays game music on resume
     */
    fun resumeMusic(){
        if(!GameManager.useMusic){
            return
        }
        this.musicPlayer!!.start()
    }
}