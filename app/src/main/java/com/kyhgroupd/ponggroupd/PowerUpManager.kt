package com.kyhgroupd.ponggroupd

import android.graphics.Color

object PowerUpManager {

    //Power Up Data
    val powerUpFallSpeed = 17
    val powerUpChance = 25 //1-100
    val powerUpColor = Color.DKGRAY
    val labelColor = Color.WHITE

    //"Power Ball" (ball does not bounce on bricks)
    var powerBallActive = false
    val powerBallDuration = 300 //60 = 1 second
    var powerBallTimer = 0

    //Power up types
    val powerUpTypes = arrayOf("POWER_BALL")

    fun generatePowerUpType(): String{
        //Get a random index position from power up types array
        val index = (powerUpTypes.indices).random()
        return this.powerUpTypes[index]
    }

    fun activatePowerUp(powerUpType: String){
        if(powerUpType == "POWER_BALL"){
            this.powerBallActive = true
            this.powerBallTimer = this.powerBallDuration
        }

        //SFX
        SoundManager.playPowerUpSFX(powerUpType)
    }

    fun updatePowerBall(){
        this.powerBallTimer -= 1
        if(this.powerBallTimer <= 0){
            this.powerBallActive = false
        }
    }

    fun updatePowerUps(){
        //Power Ball
        if(this.powerBallActive){
            this.updatePowerBall()
        }

        //Power up nr 2 etc...
    }

}