package com.kyhgroupd.ponggroupd

import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.util.*

object DataManager {

    var context: AppCompatActivity? = null
    var path: String? = null
    var fullPath: String? = null

    const val subDirName = "data"
    const val fileName = "score_data"
    const val fileExt = ".txt"
    const val separator = "###"

    fun initiate(context: AppCompatActivity){
        this.context = context
        this.path = context.filesDir.toString()
        this.fullPath = this.path + "/" + this.subDirName
        this.createSubDir()
    }

    fun loadScoreList(): MutableList<PlayerScore> {
        this.createSubDir()

        val scoreList = mutableListOf<PlayerScore>()

        try{
            val file = File(this.fullPath, this.fileName+this.fileExt)
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

        return scoreList
    }

    fun saveScore(newPlayerScore: PlayerScore){
        this.createSubDir()

        val scoreList = this.loadScoreList()
        scoreList.add(newPlayerScore)
        scoreList.sortBy { it.score }

        try{
            val file = File(this.fullPath, this.fileName+this.fileExt)
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