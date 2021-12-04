package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.tilemap.TileMap

/**
 * Teleport tile object
 */
class Teleport(
    context: Context,
    tileMap: TileMap,
    row: Int,
    col: Int,
    val row2: Int,
    val col2: Int
) : TileObject(context, tileMap) {

    var first = true

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
        if (first) {
            first = false
            // adding TeleportLights to TileMap.otherObjects for rendering purposes
            currentTile?.addTopObject(
                TeleportLight(context, tileMap, row, col).apply {
                    currentTile = this@Teleport.currentTile
                })
        }
    }

    override fun render(sb: SpriteBatch) {

    }
}