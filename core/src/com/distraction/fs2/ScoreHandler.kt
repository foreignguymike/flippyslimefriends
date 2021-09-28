package com.distraction.fs2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.distraction.fs2.tilemap.data.Area

class ScoreHandler(context: Context) {
    val scores = HashMap<Area, IntArray>().apply {
        Area.values().forEach { area ->
            put(area, IntArray(context.gameData.getMapData(area).size))
        }
    }

    private fun getPreferences(area: Area): Preferences =
        Gdx.app.getPreferences("${area.text}scores")

    fun load() {
        Area.values().forEach { area ->
            with(getPreferences(area)) {
                val areaScores = getScores(area)
                for (i in areaScores.indices) {
                    if (!contains(i.toString())) {
                        putInteger(i.toString(), 0)
                        flush()
                    }
                    areaScores[i] = getInteger(i.toString(), 0)
                }
            }
        }
    }

    fun saveScore(area: Area, level: Int, score: Int) {
        with(getPreferences(area)) {
            putInteger(level.toString(), score)
            getScores(area)[level] = score
            flush()
        }
    }

    fun getScores(area: Area) =
        scores[area] ?: throw IllegalStateException("could not find scores for area ${area.text}")
}