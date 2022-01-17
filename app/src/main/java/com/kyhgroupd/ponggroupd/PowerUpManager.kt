package com.kyhgroupd.ponggroupd

import android.graphics.Color
import com.kyhgroupd.ponggroupd.gameobjects.Ball

/**
 * Singleton class.
 * Generates and activates power ups.
 */
object PowerUpManager {

    //Power Up Data
    const val powerUpFallSpeed = 17
    const val powerUpChance = 25 //1-100
    const val powerUpColor = Color.DKGRAY

    //"Power Ball" (ball does not bounce on bricks)
    var powerBallActive = false
    private const val powerBallDuration = 300 //60 = 1 second
    private var powerBallTimer = 0
    const val powerBallTrailColor = Color.RED
    const val powerBallTrailGrayColor = Color.DKGRAY
    const val powerBallPowerUpChanceModifier = -15

    //"Multi Ball" (creates two extra balls)
    private val multiBallColor = Color.rgb(137,209,254)
    private const val multiBallGrayColor = Color.DKGRAY

    //Power up types
    private val powerUpTypes = arrayOf("POWER_BALL", "MULTI_BALL")

    /* Power Up methods */

    /**
     * Generates a random power up.
     * @return String of random PowerUp type.
     */
    fun generatePowerUpType(): String{
        //Get a random index position from power up types array
        val index: Int = (powerUpTypes.indices).random()

        return this.powerUpTypes[index]
    }

    /**
     * Activates Power Up.
     * @param powerUpType String of Power Up type to activate.
     */
    fun activatePowerUp(powerUpType: String){
        if(powerUpType == "POWER_BALL"){
            this.activatePowerBall()
        }
        else if(powerUpType == "MULTI_BALL"){
            this.activateMultiBall()
        }
        //SFX
        SoundManager.playPowerUpSFX(powerUpType)
    }

    /**
     * Run each in-game "tick". Updates Power Ups.
     */
    fun updatePowerUps(){
        //Power Ball
        if(this.powerBallActive){
            this.updatePowerBall()
        }

        //Multi Ball
        //This power up does not need an update-method

        //Power up nr 3 etc...
    }

    /**
     * Removes all active Power Ups.
     */
    fun clearActivePowerUps(){
        this.powerBallActive = false
        GameManager.multiBallObjects.clear()
    }

    /* "Power Ball" methods*/
    /**
     * Activates "Power Ball" power up.
     */
    private fun activatePowerBall(){
        this.powerBallActive = true
        this.powerBallTimer = this.powerBallDuration
    }

    /**
     * Run each in-game "tick". Decrease "Power Ball" timer.
     */
    private fun updatePowerBall(){
        this.powerBallTimer -= 1
        if(this.powerBallTimer <= 0){
            this.powerBallActive = false
        }
    }

    /* "Multi Ball" methods */

    /**
     * Creates two more Balls at position of main Ball.
     */
    private fun activateMultiBall(){

        val multiBall1 = Ball(GameManager.ball!!.posX, GameManager.ball!!.posY, multiBallColor)
        multiBall1.grayPaint.color = multiBallGrayColor
        multiBall1.mainBall = false
        setMultiBallSpeed(multiBall1)

        val multiBall2 = Ball(GameManager.ball!!.posX, GameManager.ball!!.posY, multiBallColor)
        multiBall2.grayPaint.color = multiBallGrayColor
        multiBall2.mainBall = false
        setMultiBallSpeed(multiBall2)

        GameManager.multiBallObjects.add(multiBall1)
        GameManager.multiBallObjects.add(multiBall2)
    }

    /**
     * Sets a speed and random angle of MultiBall.
     *
     * @param multiBall Ball object to set speed of.
     */
    private fun setMultiBallSpeed(multiBall: Ball){

        val totalBallSpeed = kotlin.math.abs(GameManager.ball!!.speedX) + kotlin.math.abs(GameManager.ball!!.speedY)
        while(multiBall.speedY != GameManager.ball!!.speedY && multiBall.speedX != GameManager.ball!!.speedX){
            val randomAngle: Int = (0..5).random()
            when(randomAngle){
                0 -> {
                    multiBall.speedY = (totalBallSpeed * 0.3).toInt()
                    multiBall.speedX = -(totalBallSpeed * 0.7).toInt()
                }
                1 -> {
                    multiBall.speedY = (totalBallSpeed * 0.5).toInt()
                    multiBall.speedX = -(totalBallSpeed * 0.5).toInt()
                }
                2 -> {
                    multiBall.speedY = (totalBallSpeed * 0.7).toInt()
                    multiBall.speedX = -(totalBallSpeed * 0.3).toInt()
                }
                3 -> {
                    multiBall.speedY = (totalBallSpeed * 0.7).toInt()
                    multiBall.speedX = (totalBallSpeed * 0.3).toInt()
                }
                4 -> {
                    multiBall.speedY = (totalBallSpeed * 0.5).toInt()
                    multiBall.speedX = (totalBallSpeed * 0.5).toInt()
                }
                5 -> {
                    multiBall.speedY = (totalBallSpeed * 0.3).toInt()
                    multiBall.speedX = (totalBallSpeed * 0.7).toInt()
                }
            }
            val randomY: Int = (0..1).random()
            if(randomY > 0){
                multiBall.speedY *= -1
            }
        }
    }
}