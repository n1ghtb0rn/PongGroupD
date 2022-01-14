package com.kyhgroupd.ponggroupd

import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.util.*

/**
 * Singleton class.
 * Save and load high score and setting from text files.
 */
object DataManager {

    private var path: String? = null
    private var fullPath: String? = null

    private const val subDirName = "data"
    private var scoreFileName = "score_"
    private const val settingsFileName = "settings"
    private const val fileExt = ".txt"
    private const val separator = "###"

    fun initiate(path: String){
        this.path = path
        this.fullPath = this.path + "/" + this.subDirName
        this.createSubDir()
    }

    /**
     * Return largest score for game mode
     *
     * @param gameMode Game mode string
     * @return High score
     */
    fun loadHighScore(gameMode: String): Int {
        val scoreList = this.loadScoreList(gameMode)
        if(scoreList.size > 0){
            return scoreList[0].score
        }
        return 0
    }

    /**
     * Returns a list of the ten highest scores for game mode.
     * Reads from local text file.
     *
     * @param gameMode Game mode String
     * @return MutableList of high score
     */
    fun loadScoreList(gameMode: String): MutableList<PlayerScore> {
        this.createSubDir()

        var scoreList = mutableListOf<PlayerScore>()

        try{
            // Read from text file
            val file = File(this.fullPath, this.scoreFileName + gameMode + this.fileExt)
            val scanner = Scanner(file)
            while(scanner.hasNext()){
                val line = scanner.nextLine()
                if(line.contains(this.separator)){
                    val splitLine = line.split(this.separator)
                    scoreList.add(PlayerScore(splitLine[0], splitLine[1].toInt()))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Limit list to 10 items
        if(scoreList.size > 10){
            scoreList = scoreList.take(10) as MutableList<PlayerScore>
        }

        return scoreList
    }

    /**
     * Loads and sorts list of PlayerScore objects.
     * Writes player name and score to text file.
     *
     * @param newPlayerScore PlayerScore Object
     * @param gameMode Game mode String
     */
    fun saveScore(newPlayerScore: PlayerScore, gameMode: String){
        this.createSubDir()

        val scoreList = this.loadScoreList(gameMode)
        scoreList.add(newPlayerScore)
        scoreList.sortBy { it.score }
        scoreList.reverse()

        try{
            // Write to text file
            val file = File(this.fullPath, this.scoreFileName + gameMode + this.fileExt)
            val writer = PrintWriter(file)
            for(playerScore in scoreList){
                writer.append(playerScore.username + this.separator + playerScore.score + "\n")
            }
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Save setting chosen by the user to a text file.
     */
    fun saveSettings(){
        try{
            val file = File(this.fullPath, this.settingsFileName+this.fileExt)
            val writer = PrintWriter(file)
            writer.append(GameManager.useSFX.toString() + "\n")
            writer.append(GameManager.useMusic.toString() + "\n")
            writer.append(GameManager.useColors.toString())
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Load settings previously chosen by the user.
     */
    fun loadSettings(): MutableList<Boolean>{
        this.createSubDir()

        val settingsList = mutableListOf<Boolean>()

        try{
            val file = File(this.fullPath, this.settingsFileName+this.fileExt)
            val scanner = Scanner(file)
            while(scanner.hasNext()){
                settingsList.add(scanner.nextLine().toBoolean())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return settingsList
    }

    /**
     * Create directory for text files
     */
    private fun createSubDir(){
        try{
            val subDir = File(this.path, this.subDirName)
            if(!subDir.exists()){
                subDir.mkdir()
            }
        } catch(e: IOException) {
            e.printStackTrace()
        }
    }
}