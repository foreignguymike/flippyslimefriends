package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.tilemap.Tile
import com.distraction.fs2.tilemap.TileMap

abstract class TileObject(val context: Context, val tileMap: TileMap) {

    // position (in 2d cartesian)
    val p = Vector3()

    // isometric position (position converted to "3d" position)
    val isop = Vector3()

    // destination position
    val pdest = Vector3()

    // speed
    open var speed = 0f

    // current tile position
    var currentTile: Tile? = null
    var row = 0
    var col = 0

    // size of object (will generally be the size of the sprite)
    var width = 0f
    var height = 0f

    // flag for removal
    var remove = false

    fun setPositionFromTile(row: Int, col: Int) {
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