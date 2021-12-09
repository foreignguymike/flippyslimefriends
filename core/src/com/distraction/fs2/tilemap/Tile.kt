package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.drawPadded
import com.distraction.fs2.findFirstInstance
import com.distraction.fs2.moveTo
import com.distraction.fs2.tilemap.data.Area
import com.distraction.fs2.tilemap.data.PathPointData
import com.distraction.fs2.tilemap.tileobjects.*

class Tile(
    val context: Context,
    val tileMap: TileMap,
    var row: Int,
    var col: Int,
    var index: Int,
    var area: Area
) {

    interface TileMoveListener {
        fun onTileStartMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {}
        fun onTileEndMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int)
    }

    // tile objects, use Tile.addObject() to add so that the objects are sorted
    val objects = arrayListOf<TileObject>()
    private val topObjects = arrayListOf<TileObject>()
    private val topObjectsToAdd = arrayListOf<TileObject>()

    // moving tile params
    var path: List<PathPointData>? = null
        set(value) {
            field = value
            if (value != null) {
                if (area == Area.RUINS) {
                    bottomImage = context.getImage("${area.tilesetOn}glow")
                }
            }
        }
    private var pathIndex = 0
    private val speed = 100f
    private var stayTimer = 0f
    var lock = false

    var moving = false
    var moveListeners = ArrayList<TileMoveListener>()

    private var prevRow = row
    private var prevCol = col

    // position (in 2d cartesian)
    val p = Vector3()

    // isometric position (position converted to "3d" position)
    val isop = Vector3()

    // destination position
    private val pdest = Vector3()

    private var image = context.gameData.getTile(index)
    private var bottomImage = context.getImage(area.tilesetOn)

    init {
        tileMap.toPosition(row, col, p)
        setType(index)
    }

    /**
     * Toggle the tile.
     * @return true if the tile was toggled
     */
    fun toggle(): Boolean {
        when (index) {
            TILE_OFF -> setType(TILE_ON)
            TILE_ON -> setType(TILE_OFF)
            else -> return false
        }
        return true
    }

    /**
     * Sets the tile, the tile image, and tile bottom.
     */
    fun setType(index: Int) {
        this.index = index
        this.image = context.gameData.getTile(index)
        if (area == Area.MATRIX) {
            bottomImage =
                context.getImage(if (index == TILE_OFF) area.tilesetOff else area.tilesetOn)
        }
        objects.findFirstInstance<FinishTile> {
            it.active = index == TILE_ON
        }
    }

    fun addObject(tileObject: TileObject) {
        objects.add(tileObject)
        objects.sortBy { tileObjectRenderOrder.indexOf(tileObject::class.java) }
    }

    fun addTopObject(tileObject: TileObject) {
        topObjectsToAdd.add(tileObject)
        topObjectsToAdd.sortBy { tileObjectRenderOrder.indexOf(tileObject::class.java) }
    }

    fun isActive() = index == 1

    fun isMovingTile() = path != null

    fun isBlocked() = index == 5

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

        if (topObjectsToAdd.isNotEmpty()) {
            topObjects.addAll(topObjectsToAdd)
            topObjects.sortBy { tileObjectRenderOrder.indexOf(it::class.java) }
            topObjectsToAdd.clear()
        }
        objects.forEach { it.update(dt) }
        topObjects.forEach { it.update(dt) }
        topObjects.removeAll { it.remove }
    }

    fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.drawPadded(image, isop.x - TileMap.TILE_IWIDTH / 2, isop.y - TileMap.TILE_IHEIGHT / 2)
        objects.forEach { it.render(sb) }
    }

    fun renderBottom(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.drawPadded(
            bottomImage,
            isop.x - bottomImage.regionWidth / 2,
            isop.y - bottomImage.regionHeight + TileMap.TILE_IHEIGHT / 2 + 1
        )
    }

    fun renderTop(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        topObjects.forEach { it.render(sb) }
    }

    companion object {
        // rendering order for tile objects
        val tileObjectRenderOrder = listOf(
            Ice::class.java,
            Arrow::class.java,
            SuperJumpLight::class.java,
            TeleportLight::class.java
        )

        private const val TILE_OFF = 0
        private const val TILE_ON = 1
    }
}