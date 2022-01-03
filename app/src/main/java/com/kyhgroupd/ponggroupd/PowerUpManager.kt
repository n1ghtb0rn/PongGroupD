package com.kyhgroupd.ponggroupd

import android.graphics.Color

object PowerUpManager {

    //Power Up Data
    val powerUpFallSpeed = 15
    val powerUpChance = 50 //1-100
    val powerUpColor = Color.WHITE

    //"Power Ball" (ball does not bounce on bricks)
    var powerBallActive = false
    val powerBallDuration = 300 //60 = 1 second
    var powerBallTimer = 0

    fun activatePowerUp(powerUpType: String){
        if(powerUpType == "POWER_BALL"){
            this.powerBallActive = true
            this.powerBallTimer = this.powerBallDuration
        }
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