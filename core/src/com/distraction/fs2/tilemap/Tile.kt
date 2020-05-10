package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.moveTo
import com.distraction.fs2.tilemap.tileobjects.TileObject

class Tile(
        val tileMap: TileMap,
        var row: Int,
        var col: Int,
        var index: Int,
        var image: TextureRegion) {

    interface TileMoveListener {
        fun onTileMoved(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int)
    }

    val objects = arrayListOf<TileObject>()

    // moving tile params
    var prevRow = row
    var prevCol = col
    var path: ArrayList<PathPointData>? = null
    var moveListeners = ArrayList<TileMoveListener>()
    val speed = 100f
    val stayTime = 2f
    var stayTimer = 0f
    var pathIndex = 0
    var moving = false
    val p = Vector3()
    val pdest = Vector3()
    var lock = false

    val isop = Vector3()

    init {
        tileMap.toPosition(row, col, p)
    }

    fun setType(index: Int, image: TextureRegion) {
        this.index = index
        this.image = image
    }

    fun isMovingTile() = path != null

    fun goNext() {
        path?.let { path ->
            pathIndex++
            if (pathIndex == path.size) {
                pathIndex = 0
            }
            tileMap.toPosition(path[pathIndex].tilePoint.row, path[pathIndex].tilePoint.col, pdest)
        }
    }

    fun update(dt: Float) {
        path?.let {
            if (!moving) {
                stayTimer += dt
                if (stayTimer >= it[pathIndex].time && !lock) {
                    goNext()
                    moving = true
                    stayTimer = 0f
                }
            } else {
                p.moveTo(pdest, speed * dt)
                if (p.x == pdest.x && p.y == pdest.y) {
                    row = it[pathIndex].tilePoint.row
                    col = it[pathIndex].tilePoint.col
                    moveListeners.forEach { ml -> ml.onTileMoved(this, prevRow, prevCol, row, col) }
                    moving = false
                    prevRow = row
                    prevCol = col
                }
            }
        }

        objects.forEach { it.update(dt) }
    }

    fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.draw(image, isop.x - TileMap.TILE_IWIDTH / 2, isop.y - TileMap.TILE_IHEIGHT / 2)

        objects.forEach {
            it.render(sb)
        }
    }
}