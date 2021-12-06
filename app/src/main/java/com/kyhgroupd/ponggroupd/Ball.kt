package com.kyhgroupd.ponggroupd

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader

class Ball(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    var radius : Int = 0
    var speedX: Int = 0
    var speedY: Int = 0

    init {
        radius = DataManager.screenSizeX / 50
        width = radius*2
        height = radius*2
        speedX = DataManager.ballSpeed
        speedY = DataManager.ballSpeed
    }

    override fun draw(canvas: Canvas?) {
        this.paint.shader = LinearGradient(posX.toFloat(), posY.toFloat(), (posX+(height/2)).toFloat(), (posY+(height/2)).toFloat(), DataManager.gradientColor, this.paint.color, Shader.TileMode.CLAMP)
        canvas?.drawCircle((this.posX.toFloat()+this.radius), (this.posY.toFloat()+this.radius),
            this.radius.toFloat(), this.paint)
    }

    fun collidingWith(): GameObject? {
        for (gameObject in DataManager.gameObjects) {
            if (this.posX < gameObject.posX+gameObject.width && this.posX+this.width > gameObject.posX) {
                if (this.posY < gameObject.posY+gameObject.height && this.posY+this.height > gameObject.posY) {
                    return gameObject
                }
            }
        }
        return null
    }

    override fun update(){
        posX += speedX
        posY += speedY

        //Screen border check
        if(this.posY < DataManager.uiHeight){
            this.speedY = DataManager.ballSpeed
            //SFX
            SoundManager.playBallBounceSFX()
        }
        if(this.posY+this.height > DataManager.screenSizeY+(DataManager.screenSizeY/6)){
            this.resetBall()
        }
        if(this.posX < 0){
            this.speedX = DataManager.ballSpeed
            //SFX
            SoundManager.playBallBounceSFX()
        }
        if(this.posX+this.width > DataManager.screenSizeX){
            this.speedX = -DataManager.ballSpeed
            //SFX
            SoundManager.playBallBounceSFX()
        }

        //Collision check
        var gameObject: GameObject? = this.collidingWith()
        if (gameObject != null) {
            //Brick collision
            if(gameObject is Brick){
                if (this.posY < gameObject.posY+gameObject.height-(DataManager.ballSpeed) &&
                    this.posY+this.height > gameObject.posY+(DataManager.ballSpeed)) {
                    speedX *= -1
                } else {
                    speedY *= -1
                }
                gameObject.destroy()
                DataManager.gameObjects.remove(gameObject)
                DataManager.score += DataManager.scorePerBrick
                if(DataManager.highScore < DataManager.score){
                    DataManager.highScore = DataManager.score
                }
                //SFX
                SoundManager.playDestroyBrickSFX()
            }
            //Paddle collision
            else if(gameObject is Paddle){
                if(this.posY+this.height > gameObject.posY && this.posY + this.height < gameObject.posY + DataManager.ballSpeed){
                    speedY *= -1
                } else {
                    speedX *= -1
                }
                //SFX
                SoundManager.playBallBounceSFX()
            }
        }
    }

    fun resetBall(){
        //Reset ball position and speed
        this.posX = DataManager.ballStartX
        this.posY = DataManager.ballStartY
        this.speedX = DataManager.ballSpeed
        this.speedY = DataManager.ballSpeed

        //Decrement number of lifes
        DataManager.lives--
        if(DataManager.lives <= 0){
            DataManager.resetGame()
            SoundManager.resetMusic()
            SoundManager.playGameOverSFX()
        }
        else{
            SoundManager.playLoseLifeSFX()
        }
    }
}