package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.drawPadded
import com.distraction.fs2.moveTo
import com.distraction.fs2.tilemap.tileobjects.Arrow
import com.distraction.fs2.tilemap.tileobjects.TileObject

class Tile(
    val context: Context,
    val tileMap: TileMap,
    var row: Int,
    var col: Int,
    var index: Int
) {

    interface TileMoveListener {
        fun onTileStartMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {}
        fun onTileEndMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int)
    }

    // tile objects, use Tile.addObject() to add so that the objects are sorted
    val objects = arrayListOf<TileObject>()

    // moving tile params
    var path: ArrayList<PathPointData>? = null
    var pathIndex = 0
    val speed = 100f
    var stayTimer = 0f
    var lock = false
    var moving = false
    var moveListeners = ArrayList<TileMoveListener>()
    var prevRow = row
    var prevCol = col

    // position (in 2d cartesian)
    val p = Vector3()

    // isometric position (position converted to "3d" position)
    val isop = Vector3()

    // destination position
    val pdest = Vector3()

    var image = context.gameData.getTile(index)
    var bottomImage = context.getImage("tilebottom")

    init {
        tileMap.toPosition(row, col, p)
    }

    /**
     * Toggle the tile.
     * @return true if the tile was toggled
     */
    fun toggle(): Boolean {
        when (index) {
            0 -> setType(1)
            1 -> setType(0)
            else -> return false
        }
        return true
    }

    fun setType(index: Int) {
        this.index = index
        this.image = context.gameData.getTile(index)
    }

    fun addObject(tileObject: TileObject) {
        objects.add(tileObject)
        objects.sortBy { tileObjectRenderOrder.indexOf(tileObject::class.java) }
    }

    fun isActive() = index == 1

    fun isMovingTile() = path != null

    /**
     * Go to next point in the path (moving tiles only)
     */
    private fun goNext() {
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
        // if this tile has a path
        path?.let {
            // check if we should go
            if (!moving) {
                stayTimer += dt
                if (stayTimer >= it[pathIndex].time && !lock) {
                    goNext()
                    moveListeners.forEach { ml ->
                        ml.onTileStartMove(
                            this,
                            prevRow,
                            prevCol,
                            row,
                            col
                        )
                    }
                    moving = true
                    stayTimer = 0f
                }
            } else {
                // travel to next destination
                p.moveTo(pdest, speed * dt)

                // move tile objects as well
                objects.forEach { tileObject -> tileObject.setPosition(p.x, p.y) }

                // check if we arrived
                if (p.x == pdest.x && p.y == pdest.y) {
                    row = it[pathIndex].tilePoint.row
                    col = it[pathIndex].tilePoint.col
                    moveListeners.forEach { ml ->
                        ml.onTileEndMove(
                            this,
                            prevRow,
                            prevCol,
                            row,
                            col
                        )
                    }
                    moving = false
                    prevRow = row
                    prevCol = col

                    // edge case, do not stop when the stayTimer for this path point is 0
                    // this is to prevent the player from jumping on to a moving tile that has
                    // not yet reached a path point where it must stop
                    update(0f)
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
        sb.drawPadded(
            bottomImage,
            isop.x - bottomImage.regionWidth / 2,
            isop.y - bottomImage.regionHeight + TileMap.TILE_IHEIGHT / 2 + 1
        )
    }

    companion object {
        // rendering order for tile objects
        val tileObjectRenderOrder = listOf(
            Arrow::class.java
        )
    }
}