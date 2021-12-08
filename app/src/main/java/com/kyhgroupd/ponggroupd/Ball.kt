package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader

class Ball(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0
    var speedX: Int = 0
    var speedY: Int = 0

    init {
        radius = GameManager.screenSizeX / GameManager.ballRadiusFactor
        width = radius*2
        height = radius*2
        speedX = GameManager.ballSpeed
        speedY = GameManager.ballSpeed
    }

    override fun draw(canvas: Canvas?) {
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(radius)).toFloat(), (posY+(radius)).toFloat(), GameManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
    }

    override fun update(){
        posX += speedX
        posY += speedY

        //Border collision check
        checkBorderCollision()

        //Object collision check (return if other game object is null)
        val gameObject: GameObject = this.collidingWith() ?: return

        //Brick collision
        if(gameObject is Brick){
            brickCollision(gameObject)
        }
        //Paddle collision
        else if(gameObject is Paddle){
            paddleCollision(gameObject)
        }
    }

    fun paddleCollision(gameObject: Paddle) {
        if(this.posY + this.height > gameObject.posY && this.posY + this.height < gameObject.posY + GameManager.ballSpeed){

            //100% of ball speed to be shared by a percentage over y/x-axis
            val totalBallSpeed = GameManager.ballSpeed*2

            //Change ball angle depending on paddle collision zone
            val paddleZones = 6
            val widthPerZone = gameObject.width/paddleZones
            //Zone 1 (far left side)
            if(this.posX < gameObject.posX+widthPerZone && this.posX+this.width > gameObject.posX){
                speedY = -(totalBallSpeed*0.3).toInt()
                speedX = -(totalBallSpeed*0.7).toInt()
            }
            //Zone 2
            else if(this.posX < gameObject.posX+(widthPerZone*2) && this.posX+this.width > gameObject.posX+widthPerZone){
                speedY = -(totalBallSpeed*0.5).toInt()
                speedX = -(totalBallSpeed*0.5).toInt()
            }
            //Zone 3
            else if(this.posX < gameObject.posX+(widthPerZone*3) && this.posX+this.width > gameObject.posX+(widthPerZone*2)){
                speedY = -(totalBallSpeed*0.7).toInt()
                speedX = -(totalBallSpeed*0.3).toInt()
            }
            //Zone 4
            else if(this.posX < gameObject.posX+(widthPerZone*4) && this.posX+this.width > gameObject.posX+(widthPerZone*3)){
                speedY = -(totalBallSpeed*0.7).toInt()
                speedX = (totalBallSpeed*0.3).toInt()
            }
            //Zone 5
            else if(this.posX < gameObject.posX+(widthPerZone*5) && this.posX+this.width > gameObject.posX+(widthPerZone*4)){
                speedY = -(totalBallSpeed*0.5).toInt()
                speedX = (totalBallSpeed*0.5).toInt()
            }
            //Zone 6 (far right side)
            else {
                speedY = -(totalBallSpeed*0.3).toInt()
                speedX = (totalBallSpeed*0.7).toInt()
            }

        } else {
            speedX *= -1
        }
        //SFX
        SoundManager.playBallBounceSFX()
    }

    fun brickCollision(gameObject: Brick) {
        if (this.posY < gameObject.posY+gameObject.height-GameManager.ballSpeed &&
            this.posY+this.height > gameObject.posY+GameManager.ballSpeed) {
            speedX *= -1
        } else {
            speedY *= -1
        }
        gameObject.destroy()
        GameManager.gameObjects.remove(gameObject)
        GameManager.score += GameManager.scorePerBrick
        if(GameManager.highScore < GameManager.score){
            GameManager.highScore = GameManager.score
        }
        //SFX
        SoundManager.playDestroyBrickSFX()
    }

    fun checkBorderCollision() {
        if(this.posY < GameManager.uiHeight){
            this.speedY = Math.abs(this.speedY)
            //SFX
            SoundManager.playBallBounceSFX()
        }
        if(this.posY+this.height > GameManager.screenSizeY+(GameManager.screenSizeY/6)){
            this.resetBall()
        }
        if(this.posX < 0){
            this.speedX = Math.abs(this.speedX)
            //SFX
            SoundManager.playBallBounceSFX()
        }
        if(this.posX+this.width > GameManager.screenSizeX){
            this.speedX = -Math.abs(this.speedX)
            //SFX
            SoundManager.playBallBounceSFX()
        }
    }

    //Function to check if ball is colliding with another game object
    fun collidingWith(): GameObject? {
        for (gameObject in GameManager.gameObjects) {
            if (this.posX < gameObject.posX+gameObject.width && this.posX+this.width > gameObject.posX) {
                if (this.posY < gameObject.posY+gameObject.height && this.posY+this.height > gameObject.posY) {
                    //Return the other game object if colliding
                    return gameObject
                }
            }
        }
        //Return null if no colliding
        return null
    }

    fun resetPos(){
        //Reset ball position and speed
        this.posX = GameManager.ballStartX
        this.posY = GameManager.ballStartY
        this.speedX = GameManager.ballSpeed
        this.speedY = GameManager.ballSpeed
    }


    fun resetBall(){
        resetPos()
        //Decrement number of lifes
        GameManager.lives--
        if(GameManager.lives <= 0){
            SoundManager.playGameOverSFX()
            GameManager.context?.gameOver()
        }
        else{
            SoundManager.playLoseLifeSFX()
        }
    }
}