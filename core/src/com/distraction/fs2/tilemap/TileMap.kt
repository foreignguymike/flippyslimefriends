package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.tilemap.data.*
import com.distraction.fs2.tilemap.tileobjects.*

class TileMap(
    private val context: Context,
    private val tileListener: TileListener,
    private val area: Area,
    level: Int
) :
    Tile.TileMoveListener {

    interface TileListener {
        fun onTileToggled(tileMap: TileMap)
    }

    val mapData = context.gameData.mapData[area]?.getOrNull(level)
        ?: throw IllegalStateException("level not found [${area}][$level]")
    val otherObjects = arrayListOf<TileObject>()

    val numRows = mapData.numRows
    val numCols = mapData.numCols
    val map = parseMapData(mapData.map)

    // when there are moving tiles, the map must be sorted
    // orderedMap is used to determine rendering order
    private var numTilesMoving = 0
    private val orderedMap = map.sortedBy { it?.isop?.y }.toMutableList()

    private fun parseMapData(map: IntArray): MutableList<Tile?> {
        return MutableList(map.size) {
            if (mapData.map[it] < 0) {
                null
            } else {

                val row = it / numCols
                val col = it % numCols

                val index = map[it]
                val tile = Tile(
                    context,
                    this,
                    row,
                    col,
                    index,
                    area
                )
                    .apply {
                        mapData.path?.forEach { ppd ->
                            if (row == ppd[0].tilePoint.row && col == ppd[0].tilePoint.col) {
                                this.path = ppd
                                moveListeners.add(tileMap)
                            }
                        }
                    }

                mapData.objects
                    .filter { objData -> objData.row == row && objData.col == col }
                    .map { objData ->
                        when (objData) {
                            is ArrowData -> Arrow(
                                context,
                                this,
                                row,
                                col,
                                objData.direction
                            ).apply { currentTile = tile }
                            is SuperJumpData -> SuperJump(
                                context,
                                this,
                                row,
                                col
                            ).apply { currentTile = tile }
                            is IceData -> Ice(context, this, row, col).apply { currentTile = tile }
                            is TeleportData -> Teleport(
                                context,
                                this,
                                row,
                                col,
                                objData.destRow,
                                objData.destCol
                            ).apply {
                                currentTile = tile
                            }
                            else -> throw IllegalArgumentException("incorrect tile object data")
                        }
                    }
                    .forEach { tileObject -> tile.addObject(tileObject) }
                tile
            }
        }
    }

    fun toggleTile(row: Int, col: Int) {
        getTile(row, col)?.let {
            if (it.toggle()) {
                tileListener.onTileToggled(this)
            }
            if (it.isActive()) {
                otherObjects.add(TileLight(context, this, row, col))
            }
        }
    }

    fun setTile(row: Int, col: Int, tile: Tile?) {
        map[toIndex(row, col)] = tile
    }

    fun setTileType(row: Int, col: Int, index: Int) {
        getTile(row, col)?.setType(index)
    }

    fun toIndex(row: Int, col: Int) = MathUtils.clamp(row * numCols + col, 0, map.size - 1)

    fun getTile(row: Int, col: Int) = map[toIndex(row, col)]

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
        return tile != null && !tile.moving && !tile.isBlocked()
    }

    fun isFinished() = map.none { it?.index == 0 }

    override fun onTileStartMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
        numTilesMoving++
    }

    override fun onTileEndMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
        numTilesMoving--
        setTile(oldRow, oldCol, null)
        setTile(newRow, newCol, tile)
    }

    fun update(dt: Float) {
        map.forEach {
            it?.update(dt)
        }
        otherObjects.forEach {
            it.currentTile?.let { tile ->
                if (tile.isMovingTile()) {
                    it.setPosition(tile.p.x, tile.p.y)
                }
            }
            it.update(dt)
        }
        otherObjects.removeAll { it.remove }
    }

    fun render(sb: SpriteBatch) {
        if (numTilesMoving > 0) {
            // lazy sort, sort on every frame
            // could be optimized to sort only when required
            // although sorting an already sorted list should be quick (?) so just leave it
            // plus i don't have to care about figuring out when to sort
            orderedMap.sortByDescending { it?.isop?.y }
        }
        orderedMap.forEach { it?.renderBottom(sb) }
        orderedMap.forEach { it?.render(sb) }
    }

    fun renderOther(sb: SpriteBatch) {
        otherObjects.forEach {
            it.render(sb)
        }
    }

    companion object {
        const val TILE_SIZE = 30f
        const val TILE_IWIDTH = 60f
        const val TILE_IHEIGHT = 30f
    }

}