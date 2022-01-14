package com.kyhgroupd.ponggroupd.gameobjects

import android.graphics.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.graphics.contains
import com.kyhgroupd.ponggroupd.*
import kotlin.math.abs

/**
 * A ball class to represent a visual and moving ball in the game.
 * This class also handles collision algorithms.
 * When calculating collisions, this class is treated as a square/rect shape.
 *
 * @param startX X position
 * @param startY Y position
 * @param color Color of object
 */
class Ball(startX: Int, startY: Int, color: Int) : GameObject(startX, startY, color) {

    private var radius: Int = 0
    var speedX: Int = 0
    var speedY: Int = 0

    var mainBall = true //Is this the main ball?
    var shouldDeleteThis = false //Is this ball about to be removed?

    init {
        radius = GameManager.referenceBrick!!.height / 2
        width = radius * 2
        height = radius * 2
        speedX = GameManager.ballSpeed
        speedY = -GameManager.ballSpeed
        //The grayscale color (when colors are off in settings)
        this.grayPaint.color = Color.LTGRAY
    }

    /**
     * A method for drawing this object on the canvas every frame.
     * "shader"/LinearGradient creates a retro "3D"-feeling by combining the main color
     * with the color white. It creates a little "triangle" in the top left corner of the object.
     *
     * The circle drawing position needs to be adjusted to fit the hitbox.
     *
     * @param canvas Canvas object
     */
    override fun draw(canvas: Canvas?) {
        if (GameManager.useColors || GameManager.gameMode == "pong") {
            this.paint.shader = LinearGradient(
                posX.toFloat(),
                posY.toFloat(),
                (posX + (radius)).toFloat(),
                (posY + (radius)).toFloat(),
                GameManager.gradientColor,
                this.paint.color,
                Shader.TileMode.CLAMP
            )
            canvas?.drawCircle(
                (this.posX.toFloat() + this.radius), (this.posY.toFloat() + this.radius),
                this.radius.toFloat(), this.paint
            )
        } else {
            this.grayPaint.shader = LinearGradient(
                posX.toFloat(),
                posY.toFloat(),
                (posX + (radius)).toFloat(),
                (posY + (radius)).toFloat(),
                GameManager.gradientColor,
                this.grayPaint.color,
                Shader.TileMode.CLAMP
            )
            canvas?.drawCircle(
                (this.posX.toFloat() + this.radius), (this.posY.toFloat() + this.radius),
                this.radius.toFloat(), this.grayPaint
            )
        }

    }

    /**
     * Update-method for updating position (etc) of this object every frame.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun update() {
        //Create a ball trail effect (except for pong game mode)
        if (GameManager.gameMode != "pong") {
            if (this.mainBall) {
                if (GameManager.useColors) {
                    if (PowerUpManager.powerBallActive) {
                        GameManager.trailObjects.add(BallTrail(this.posX, this.posY,
                                PowerUpManager.powerBallTrailColor))
                    } else {
                        GameManager.trailObjects.add(BallTrail(this.posX, this.posY, this.paint.color))
                    }
                } else {
                    if (PowerUpManager.powerBallActive) {
                        GameManager.trailObjects.add(BallTrail(this.posX, this.posY,
                                PowerUpManager.powerBallTrailGrayColor))
                    } else {
                        GameManager.trailObjects.add(BallTrail(this.posX, this.posY, this.paint.color))
                    }
                }
            } else {
                GameManager.trailObjects.add(BallTrail(this.posX, this.posY, this.grayPaint.color))
            }
        }

        //Update the position of this object based on its speed
        posX += speedX
        posY += speedY

        //Border collision check
        checkBorderCollision()

        //Object collision check (return if other game object is null)
        val gameObject: GameObject = this.collidingWith() ?: return

        //Brick collision
        when (gameObject) {
            is Brick -> brickCollision(gameObject)
            is Paddle -> paddleCollision(gameObject)
            is Goal -> goalCollision()
        }
    }

    /**
     * Check if this object has collidied with the goal-object.
     */
    private fun goalCollision() {
        GameManager.score += 100 + (GameManager.level - 1) * GameManager.bonusScorePerLevel
        val golfLevels = GolfLevels().levels
        if (golfLevels.size <= GameManager.level) {
            GameManager.win = true
            GameManager.context?.gameOver()
        }
        GameManager.nextLevel()
    }

    /**
     * Check if this object has collided with the paddle.
     */
    private fun paddleCollision(paddle: Paddle) {

        val speedXY = this.getSpeedXY(paddle)

        //Player 1
        if (paddle.player == 1) {
            if (this.posY + this.height > paddle.posY && this.posY + this.height < paddle.posY + (paddle.height / 0.25)) {

                this.speedY = speedXY.y
                this.speedX = speedXY.x

            } else {
                speedX *= -1
            }
        }
        //Player 2
        else {
            if (this.posY < paddle.posY + paddle.height && this.posY > paddle.posY + (paddle.height / 4)) {

                this.speedY = -speedXY.y
                this.speedX = speedXY.x

            } else {
                speedX *= -1
            }
        }

        //Reset combo
        GameManager.currentCombo = 0
        UIManager.comboText = null

        //SFX
        SoundManager.playBallBounceSFX()
    }

    /**
     * Check of this object has collided with a brick-object.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun brickCollision(brick: Brick) {

        //Split this object into 4 points for more detailed collision detection

        val pointLeft = Point(posX, posY + radius)
        val pointTop = Point(posX + radius, posY)
        val pointRight = Point(posX + width, posY + radius)
        val pointBottom = Point(posX + radius, posY + height)

        val brickRect =
            Rect(brick.posX, brick.posY, brick.posX + brick.width, brick.posY + brick.height)

        //Store previous speed
        val oldSpeedY = speedY
        val oldSpeedX = speedX

        //Check which of the four points collided with the brick, and set direction.
        if (brickRect.contains(pointBottom)) {
            speedY = abs(speedY) * -1
            destroyBrick(brick)
            GameManager.addScore()
        }
        if (brickRect.contains(pointTop)) {
            speedY = abs(speedY)
            destroyBrick(brick)
            GameManager.addScore()
        }
        if (brickRect.contains(pointRight)) {
            speedX = abs(speedX) * -1
            destroyBrick(brick)
            GameManager.addScore()
        }
        if (brickRect.contains(pointLeft)) {
            speedX = abs(speedX)
            destroyBrick(brick)
            GameManager.addScore()
        }

        //Don't bounce if "power ball" is active (it will just smash through the bricks)
        if (PowerUpManager.powerBallActive && this.mainBall) {
            speedY = oldSpeedY
            speedX = oldSpeedX
        }
    }

    /**
     * A method for destroying bricks that this object has collided with.
     * It also handles the power up spawn algorithm.
     *
     * @param brick Brick. The Brick-object to be destroyed.
     */
    private fun destroyBrick(brick: Brick) {
        //Unbreakable brick?
        if (brick.unbreakable) {
            //SFX
            SoundManager.playBallBounceSFX()
            return
        }

        //Non-unbreakable brick?
        brick.health--
        if (GameManager.gameMode == "golf") {
            brick.changeColor()
        }

        //Check brick health
        if (brick.health > 0) {
            //SFX
            SoundManager.playBallBounceSFX()
            return
        }

        //Spawn Power Up?
        if (GameManager.gameMode == "breakout") {
            val random = (1..100).random()
            var powerUpChance = PowerUpManager.powerUpChance
            //Lower the chance of a new power up if "power balL" is active
            if (PowerUpManager.powerBallActive) {
                powerUpChance += PowerUpManager.powerBallPowerUpChanceModifier
            }
            //Eg: 25% chance = 100 - 25 = 75. The RNG must be above 75 for power up to spawn.
            if (random > (100 - powerUpChance)) {
                //Creates a new power up object and adds it to the list
                GameManager.powerUpObjects.add(
                    PowerUp(
                        brick.posX,
                        brick.posY,
                        PowerUpManager.powerUpColor
                    )
                )
            }
        }

        //Finally
        brick.destroy()
        GameManager.gameObjects.remove(brick)

        //SFX
        SoundManager.playDestroyBrickSFX()
    }

    /**
     * A method for getting the new ball-angle after colliding with the paddle.
     * The paddle is split into 6 zones, each with its different angel.
     * 1-6 (from left to right)
     *
     * Paddle = ______
     * Zone nr= 123456
     *
     * @param paddle Paddle. The paddle-object that this object collided with.
     */
    private fun getSpeedXY(paddle: Paddle): Point {
        //100% of ball speed to be shared by a percentage over y/x-axis
        val totalBallSpeed = GameManager.ballSpeed * 2

        //Change ball angle depending on paddle collision zone
        val paddleZones = 6
        val widthPerZone = paddle.width / paddleZones

        var speedY: Int = 0
        var speedX: Int = 0

        //Zone 1 (far left side)
        if (this.posX < paddle.posX + widthPerZone && this.posX + this.width > paddle.posX) {
            speedY = -(totalBallSpeed * 0.3).toInt()
            speedX = -(totalBallSpeed * 0.7).toInt()
        }
        //Zone 2
        else if (this.posX < paddle.posX + (widthPerZone * 2) && this.posX + this.width > paddle.posX + widthPerZone) {
            speedY = -(totalBallSpeed * 0.5).toInt()
            speedX = -(totalBallSpeed * 0.5).toInt()
        }
        //Zone 3
        else if (this.posX < paddle.posX + (widthPerZone * 3) && this.posX + this.width > paddle.posX + (widthPerZone * 2)) {
            speedY = -(totalBallSpeed * 0.7).toInt()
            speedX = -(totalBallSpeed * 0.3).toInt()
        }
        //Zone 4
        else if (this.posX < paddle.posX + (widthPerZone * 4) && this.posX + this.width > paddle.posX + (widthPerZone * 3)) {
            speedY = -(totalBallSpeed * 0.7).toInt()
            speedX = (totalBallSpeed * 0.3).toInt()
        }
        //Zone 5
        else if (this.posX < paddle.posX + (widthPerZone * 5) && this.posX + this.width > paddle.posX + (widthPerZone * 4)) {
            speedY = -(totalBallSpeed * 0.5).toInt()
            speedX = (totalBallSpeed * 0.5).toInt()
        }
        //Zone 6 (far right side)
        else {
            speedY = -(totalBallSpeed * 0.3).toInt()
            speedX = (totalBallSpeed * 0.7).toInt()
        }

        return Point(speedX, speedY)
    }

    /**
     * A method for checking the collision with the border (edges of the playfield).
     */
    private fun checkBorderCollision() {
        if (GameManager.gameMode == "pong") {
            var player = 0
            if (this.posY < -GameManager.screenSizeY / 6) {
                player = 1
            }
            if (this.posY + this.height > GameManager.screenSizeY + (GameManager.screenSizeY / 6)) {
                player = 2
            }
            if (player != 0) {
                GameManager.scorePointPong(player)
                resetPosPong(player)
            }
        } else {
            if (this.posY < UIManager.uiHeight) {
                this.speedY = abs(this.speedY)
                //SFX
                SoundManager.playBallBounceSFX()
            }
            if (this.posY + this.height > GameManager.screenSizeY + (GameManager.screenSizeY / 6)) {
                if (this.mainBall) {
                    resetPos()
                    GameManager.loseLife()
                } else {
                    this.shouldDeleteThis = true
                }
            }
        }
        if (this.posX < 0) {
            this.speedX = abs(this.speedX)
            //SFX
            SoundManager.playBallBounceSFX()
        }
        if (this.posX + this.width > GameManager.screenSizeX) {
            this.speedX = -abs(this.speedX)
            //SFX
            SoundManager.playBallBounceSFX()
        }
    }

    /**
     * Function to check if ball is colliding with another game object.
     *
     * @return GameObject Returns the game object that the ball collided with.
     *                    (Returns null if no collision)
     */
    private fun collidingWith(): GameObject? {
        for (gameObject in GameManager.gameObjects) {
            //Algorithm to check if this object has collided with another object.
            if (this.posX < gameObject.posX + gameObject.width && this.posX + this.width > gameObject.posX) {
                if (this.posY < gameObject.posY + gameObject.height && this.posY + this.height > gameObject.posY) {
                    //Return the other game object if collision was detected
                    return gameObject
                }
            }
        }
        //Return null if no collision
        return null
    }

    /**
     * A function to reset this objects position/speed to original value.
     */
    fun resetPos() {
        //Reset ball position and speed
        this.posX = GameManager.ballStartX
        this.posY = GameManager.ballStartY
        this.speedX = GameManager.ballSpeed
        this.speedY = -GameManager.ballSpeed
    }

    /**
     * A function to reset this objects position/speed to original value in pong game mode.
     */
    private fun resetPosPong(player: Int) {
        this.posX = GameManager.ballStartX
        this.posY = GameManager.ballStartY
        this.speedX = GameManager.ballSpeed
        when (player) {
            1 -> this.speedY = GameManager.ballSpeed
            2 -> this.speedY = -GameManager.ballSpeed
        }
    }


}