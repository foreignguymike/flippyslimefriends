package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.log
import com.distraction.fs2.tilemap.tileobjects.Arrow
import com.distraction.fs2.tilemap.tileobjects.SuperJump
import com.distraction.fs2.tilemap.tileobjects.Teleport
import com.distraction.fs2.tilemap.tileobjects.TileObject
import kotlin.math.max
import kotlin.math.min

class TileMap(val context: Context, level: Int) : Tile.TileMoveListener {

    companion object {
        const val TILE_SIZE = 30f
        const val TILE_IWIDTH = 60f
        const val TILE_IHEIGHT = 30f
        const val TILE_HEIGHT_3D = 5
    }

    val mapData = context.gameData.mapData[level]
    val tileset = context.gameData.tileset
    val otherObjects = arrayListOf<TileObject>()

    val numRows = mapData.numRows
    val numCols = mapData.numCols
    val map = parseMapData(mapData.map)

    private val p = Vector3()
    private val pv = Vector3()

    val pixel = context.assets.getAtlas().findRegion("pixel")

    private fun parseMapData(map: IntArray): Array<Tile?> {
        return Array(map.size) {
            val tile: Tile?
            if (mapData.map[it] < 0) {
                tile = null
            } else {

                val row = it / numCols
                val col = it % numCols

                val index = map[it]
                tile = Tile(
                        this,
                        row,
                        col,
                        index,
                        getTileImage(index))
                        .apply {
                            mapData.path?.let { path ->
                                if (row == path[0].tilePoint.row && col == path[0].tilePoint.col) {
                                    this.path = mapData.path
                                    moveListeners.add(tileMap)
                                }
                            }
                        }

                mapData.objects
                        .filter { objData -> objData.row == row && objData.col == col }
                        .map { objData ->
                            when (objData) {
                                is ArrowData -> Arrow(context, this, row, col, objData.direction)
                                is SuperJumpData -> SuperJump(context, this, row, col)
                                is TeleportData -> Teleport(context, this, row, col, objData.destRow, objData.destCol)
                                else -> throw IllegalArgumentException("incorrect tile object data")
                            }
                        }
                        .forEach { tileObject ->
                            tile.objects.add(tileObject)
                        }
            }
            tile
        }
    }

    fun toggleTile(row: Int, col: Int) {
        when (getTile(row, col)?.index) {
            0 -> setTileType(row, col, 1)
            1 -> setTileType(row, col, 0)
        }
    }

    fun setTile(row: Int, col: Int, tile: Tile?) {
        map[toIndex(row, col)] = tile
    }

    fun setTileType(row: Int, col: Int, index: Int) {
        getTile(row, col)?.setType(index, getTileImage(index))
    }

    fun toIndex(row: Int, col: Int) = row * numCols + col

    fun getTile(row: Int, col: Int) = map[toIndex(row, col)]

    fun getTileImage(tileIndex: Int) = tileset[tileIndex]

    fun toIsometric(x: Float, y: Float, p: Vector3) {
        val xo = x / TILE_SIZE
        val yo = y / TILE_SIZE
        p.x = (xo - yo) * TILE_IWIDTH / 2
        p.y = (-xo - yo) * TILE_IHEIGHT / 2
    }

    fun toPosition(row: Int, col: Int, p: Vector3) {
        p.x = col * TILE_SIZE
        p.y = row * TILE_SIZE
    }

    fun toPosition(tile: Int) = tile * TILE_SIZE

    fun isValidTile(row: Int, col: Int): Boolean {
        if (row !in 0 until numRows || col !in 0 until numCols) return false
        val tile = getTile(row, col)
        return tile != null && !tile.moving
    }

    fun isFinished(): Boolean {
        map.forEach {
            if (it?.index == 0) {
                return false
            }
        }
        return true
    }

    override fun onTileMoved(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
        setTile(oldRow, oldCol, null)
        setTile(newRow, newCol, tile)
    }

    fun update(dt: Float) {
        map.forEach {
            it?.update(dt)
        }
        otherObjects.forEach { it.update(dt) }
        otherObjects.removeAll { it.remove }
    }

    fun render(sb: SpriteBatch) {
        // must render diagonally now that there are moving tiles (ie tile depth changes)
        for (diag in 0 until (numRows + numCols)) {
            val startCol = max(0, diag - numRows)
            val count = min(diag, min(numCols - startCol, numRows))
            for (j in 0 until count) {
                getTile(min(numRows, diag) - j - 1, startCol + j)?.render(sb)
            }
        }
    }

    fun renderOther(sb: SpriteBatch) {
        otherObjects.forEach {
            it.render(sb)
        }
    }

}

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
    var path: Array<PathData>? = null
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
        log("tile: $row, $col at position: $p")
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

    fun moveToDest(dist: Float) {
        if (p.x < pdest.x) {
            p.x += dist
            if (p.x > pdest.x) {
                p.x = pdest.x
            }
        }
        if (p.x > pdest.x) {
            p.x -= dist
            if (p.x < pdest.x) {
                p.x = pdest.x
            }
        }
        if (p.y < pdest.y) {
            p.y += dist
            if (p.y > pdest.y) {
                p.y = pdest.y
            }
        }
        if (p.y > pdest.y) {
            p.y -= dist
            if (p.y < pdest.y) {
                p.y = pdest.y
            }
        }
    }

    fun update(dt: Float) {
        objects.forEach { it.update(dt) }

        path?.let {
            if (!moving) {
                stayTimer += dt
                if (stayTimer >= it[pathIndex].time && !lock) {
                    goNext()
                    moving = true
                    stayTimer = 0f
                }
            } else {
                moveToDest(speed * dt)
                if (p.x == pdest.x && p.y == pdest.y) {
                    row = it[pathIndex].tilePoint.row
                    col = it[pathIndex].tilePoint.col
                    moveListeners.forEach {  it.onTileMoved(this, prevRow, prevCol, row, col) }
                    moving = false
                    prevRow = row
                    prevCol = col
                }
            }
        }
    }

    fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.draw(image, isop.x - TileMap.TILE_IWIDTH / 2, isop.y - TileMap.TILE_IHEIGHT / 2)

        objects.forEach {
            it.render(sb)
        }
    }
}