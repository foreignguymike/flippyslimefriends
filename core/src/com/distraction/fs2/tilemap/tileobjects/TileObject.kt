package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.tilemap.Tile
import com.distraction.fs2.tilemap.TileMap

abstract class TileObject(val context: Context, val tileMap: TileMap) {

    val p = Vector3()
    val isop = Vector3()
    val pdest = Vector3()

    var row = 0
    var col = 0
    var width = 0f
    var height = 0f
    var remove = false

    val pixel = context.getImage("pixel")

    var currentTile: Tile? = null

    open fun setPositionFromTile(row: Int, col: Int) {
        this.row = row
        this.col = col
        tileMap.toPosition(row, col, p)
    }

    open fun setPosition(x: Float, y: Float) {
        p.x = x
        p.y = y
    }

    abstract fun update(dt: Float)
    abstract fun render(sb: SpriteBatch)
}