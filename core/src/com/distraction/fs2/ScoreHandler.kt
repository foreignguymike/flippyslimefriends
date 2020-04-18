package com.distraction.fs2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.distraction.fs2.tilemap.TileMapData

class ScoreHandler {
    val scores = IntArray(TileMapData.levelData.size) { 0 }

    private fun getPreferences(): Preferences = Gdx.app.getPreferences("scores")

    fun init() {
        with(getPreferences()) {
            for (i in scores.indices) {
                if (!contains(i.toString())) {
                    putInteger(i.toString(), 0)
                }
            }
            flush()
        }
    }

    fun load() {
        with(getPreferences()) {
            for (i in scores.indices) {
                if (!contains(i.toString())) {
                    init()
                }
                scores[i] = getInteger(i.toString(), 0)
            }
        }
    }

    fun saveScore(index: Int, score: Int) {
        with(getPreferences()) {
            putInteger(index.toString(), score)
            scores[index] = score
            flush()
        }
    }
}