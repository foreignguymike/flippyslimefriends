package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.drawPadded
import com.distraction.fs2.moveTo
import com.distraction.fs2.tilemap.tileobjects.TileObject

class Tile(
        val context: Context,
        val tileMap: TileMap,
        var row: Int,
        var col: Int,
        var index: Int) {

    interface TileMoveListener {
        fun onTileStartMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {}
        fun onTileEndMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int)
    }

    val objects = arrayListOf<TileObject>()
    var image = context.gameData.getTile(index)
    var bottomImage = context.getImage("tilebottom")

    // moving tile params
    var pathIndex = 0
    var path: ArrayList<PathPointData>? = null

    var prevRow = row
    var prevCol = col

    var moveListeners = ArrayList<TileMoveListener>()

    val speed = 100f
    var stayTimer = 0f
    var moving = false
    val p = Vector3()
    val pdest = Vector3()
    var lock = false

    val isop = Vector3()

    init {
        tileMap.toPosition(row, col, p)
    }

    fun setType(index: Int) {
        this.index = index
        this.image = context.gameData.getTile(index)
    }

    fun isActive() = index == 1

    fun isMovingTile() = path != null

    fun goNext() {
        path?.let { path ->
            pathIndex++
            if (pathIndex == path.size) {
                pathIndex = 0
            }
            row = path[pathIndex].tilePoint.row
            col = path[pathIndex].tilePoint.col
            tileMap.toPosition(path[pathIndex].tilePoint.row, path[pathIndex].tilePoint.col, pdest)
        }
    }

    fun update(dt: Float) {
        path?.let {
            // check if we should go
            if (!moving) {
                stayTimer += dt
                if (stayTimer >= it[pathIndex].time && !lock) {
                    goNext()
                    moveListeners.forEach { ml -> ml.onTileStartMove(this, prevRow, prevCol, row, col) }
                    moving = true
                    stayTimer = 0f
                }
            } else {
                // travel
                p.moveTo(pdest, speed * dt)
                // check if we arrived
                if (p.x == pdest.x && p.y == pdest.y) {
                    row = it[pathIndex].tilePoint.row
                    col = it[pathIndex].tilePoint.col
                    moveListeners.forEach { ml -> ml.onTileEndMove(this, prevRow, prevCol, row, col) }
                    moving = false
                    prevRow = row
                    prevCol = col
                    update(0f) // edge case, do not stop at 0f timer stays
                }
            }
        }

        objects.forEach {
            it.update(dt)
            if (isMovingTile()) {
                it.setPosition(it.p.x, it.p.y)
            }
        }
    }

    fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.drawPadded(image, isop.x - TileMap.TILE_IWIDTH / 2, isop.y - TileMap.TILE_IHEIGHT / 2)

        objects.forEach {
            it.render(sb)
        }
    }

    fun renderBottom(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.drawPadded(bottomImage, isop.x - bottomImage.regionWidth / 2, isop.y - bottomImage.regionHeight + TileMap.TILE_IHEIGHT / 2)
    }
}