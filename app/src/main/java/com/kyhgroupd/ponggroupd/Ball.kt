package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader

class Ball(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0
    var speedX: Int = 0
    var speedY: Int = 0

    init {
        radius = GameManager.screenSizeX / 50
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

        //Screen border check
        if(this.posY < GameManager.uiHeight){
            this.speedY = GameManager.ballSpeed
            //SFX
            SoundManager.playBallBounceSFX()
        }
        if(this.posY+this.height > GameManager.screenSizeY+(GameManager.screenSizeY/6)){
            this.resetBall()
        }
        if(this.posX < 0){
            this.speedX = GameManager.ballSpeed
            //SFX
            SoundManager.playBallBounceSFX()
        }
        if(this.posX+this.width > GameManager.screenSizeX){
            this.speedX = -GameManager.ballSpeed
            //SFX
            SoundManager.playBallBounceSFX()
        }

        //Collision check
        val gameObject: GameObject? = this.collidingWith()
        if (gameObject != null) {
            //Brick collision
            if(gameObject is Brick){
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
            //Paddle collision
            else if(gameObject is Paddle){
                if(this.posY + this.height > gameObject.posY && this.posY + this.height < gameObject.posY + GameManager.ballSpeed){
                    speedY *= -1

                    //Change direction depending on side of paddle hit
                    if(this.posX + radius < gameObject.posX + gameObject.width/2 && speedX > 0){
                        speedX *= -1
                    } else if(this.posX + radius > gameObject.posX + gameObject.width/2 && speedX < 0){
                        speedX *= -1
                    }
                } else {
                    speedX *= -1
                }
                //SFX
                SoundManager.playBallBounceSFX()
            }
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

    fun resetBall(){
        //Reset ball position and speed
        this.posX = GameManager.ballStartX
        this.posY = GameManager.ballStartY
        this.speedX = GameManager.ballSpeed
        this.speedY = GameManager.ballSpeed

        //Decrement number of lives
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