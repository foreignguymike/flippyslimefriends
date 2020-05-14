package com.distraction.fs2

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.distraction.fs2.states.GSM
import com.distraction.fs2.tilemap.GameData

class Context {
    val assets = AssetManager().apply {
        load("fs2.atlas", TextureAtlas::class.java)
        finishLoading()
//        getAtlas().regions.forEach {region ->
//            if (region.name.contains("tile")) {
//                val fix = 0.01f
//                val x: Float = region.regionX.toFloat()
//                val y: Float = region.regionY.toFloat()
//                val width: Float = region.regionWidth.toFloat()
//                val height: Float = region.regionHeight.toFloat()
//                val invTexWidth: Float = 1f / region.texture.width
//                val invTexHeight: Float = 1f / region.texture.height
//                region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight)
//            }
//        }
    }
    val gsm = GSM()
    val scoreHandler = ScoreHandler().apply { load() }
    val gameData = GameData(this)
}