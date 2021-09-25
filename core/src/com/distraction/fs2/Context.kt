package com.distraction.fs2

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.distraction.fs2.states.GSM
import com.distraction.fs2.tilemap.data.GameData

class Context {
    val assets = AssetManager().apply {
        load("fs2.atlas", TextureAtlas::class.java)
        finishLoading()
    }
    val gsm = GSM()
    val gameData = GameData(this)
    val scoreHandler = ScoreHandler(this).apply { load() }

    fun getImage(key: String): TextureRegion = assets.getAtlas().findRegion(key) ?: error("image $key not found")
    fun getImage(key: String, index: Int): TextureRegion = assets.getAtlas().findRegion(key, index) ?: error("image ${key}_$index not found")
}