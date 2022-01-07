package com.kyhgroupd.ponggroupd

import android.graphics.Color
import android.util.Log
import com.kyhgroupd.ponggroupd.gameobjects.Ball

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
    val powerBallColor = Color.rgb(255, 215, 0)

    //"Multi Ball" (creates two extra balls)
    val multiBallColor = Color.rgb(50, 215, 100)
    val multiBallGrayColor = Color.WHITE

    //Power up types
    val powerUpTypes = arrayOf("POWER_BALL", "MULTI_BALL")

    /* Power Up methods */

    fun generatePowerUpType(): String{
        //Get a random index position from power up types array
        val index: Int = (powerUpTypes.indices).random()

        return this.powerUpTypes[index]
    }

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

    fun updatePowerUps(){
        //Power Ball
        if(this.powerBallActive){
            this.updatePowerBall()
        }

        //Multi Ball
        //This power up does not need an update-method

        //Power up nr 3 etc...
    }

    fun clearActivePowerUps(){
        this.powerBallActive = false
    }

    /* "Power Ball" methods*/

    fun activatePowerBall(){
        this.powerBallActive = true
        this.powerBallTimer = this.powerBallDuration
        GameManager.ball!!.paint.color = this.powerBallColor
    }

    fun updatePowerBall(){
        this.powerBallTimer -= 1
        if(this.powerBallTimer <= 0){
            this.powerBallActive = false
            GameManager.ball!!.paint.color = GameManager.ballColor
        }
    }

    /* "Multi Ball" methods */

    fun activateMultiBall(){

        val multiBall1 = Ball(GameManager.ball!!.posX, GameManager.ball!!.posY, multiBallColor)
        multiBall1.grayPaint.color = multiBallGrayColor
        multiBall1.mainBall = false
        setMultiBallSpeed(multiBall1)

        val multiBall2 = Ball(GameManager.ball!!.posX, GameManager.ball!!.posY, multiBallColor)
        multiBall2.grayPaint.color = multiBallGrayColor
        multiBall2.mainBall = false
        setMultiBallSpeed(multiBall2)

        //while(multiBall1.speedX == multiBall2.speedX && multiBall1.speedY == multiBall2.speedY){
        //    setMultiBallSpeed(multiBall2)
        //}

        GameManager.multiBallObjects.add(multiBall1)
        GameManager.multiBallObjects.add(multiBall2)
    }

    fun setMultiBallSpeed(multiBall: Ball){

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