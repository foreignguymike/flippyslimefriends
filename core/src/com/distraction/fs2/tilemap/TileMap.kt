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
    val startBubble = mapData.startBubble

    // cache finish tiles here
    private var finishTiles = mutableListOf<FinishTile>()

    val numRows = mapData.numRows
    val numCols = mapData.numCols
    val map = parseMapData(mapData.map)

    // when there are moving tiles, the map must be sorted
    // orderedMap is used to determine rendering order
    private var numTilesMoving = 0
    private val orderedMap = map.sortedBy { it?.isop?.y }.toMutableList()

    private var tilePathRenderer: TilePathRenderer? = null

    /**
     * Convert map data to tiles
     */
    private fun parseMapData(map: IntArray): MutableList<Tile?> {
        return MutableList(map.size) { index ->
            if (mapData.map[index] < 0) {
                null
            } else {

                val row = index / numCols
                val col = index % numCols

                val tile = Tile(context, this, row, col, map[index], area).apply {
                    // add moving tiles
                    mapData.path?.forEach { ppd ->
                        if (row == ppd[0].tilePoint.row && col == ppd[0].tilePoint.col) {
                            this.path = ppd
                            moveListeners.add(tileMap)
                        }
                    }
                }

                // add tile objects
                mapData.objects
                    .filter { it.row == row && it.col == col }
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
                            is BubbleData -> Bubble(context, this, row, col).apply {
                                tile.addTopObject(bubbleBase)
                                currentTile = tile
                            }
                            is FinishTileData -> FinishTile(context, this, row, col).apply {
                                currentTile = tile
                            }.also {
                                finishTiles.add(it)
                            }
                            else -> throw IllegalArgumentException("incorrect tile object data")
                        }
                    }
                    .forEach { tile.addObject(it) }

                tile
            }
        }.also {
            mapData.path?.let { path ->
                tilePathRenderer = TilePathRenderer(context, this, path)
            }
        }
    }

    private fun toIndex(row: Int, col: Int) = MathUtils.clamp(row * numCols + col, 0, map.size - 1)

    fun toggleTile(row: Int, col: Int) {
        getTile(row, col)?.let {
            if (it.toggle()) {
                tileListener.onTileToggled(this)
            }
            if (it.isActive()) {
                it.addTopObject(TileLight(context, this, row, col))
            }
        }
    }

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

    fun isFinished(players: List<Player>?) = map.none { it?.index == 0 } &&
            if (finishTiles.isNotEmpty()) finishTiles.all { finishTile ->
                finishTile.active && players?.any {
                    it.row == finishTile.row && it.col == finishTile.col
                } ?: false
            } else true

    override fun onTileStartMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
        numTilesMoving++
    }

    override fun onTileEndMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
        numTilesMoving--
        map[toIndex(oldRow, oldCol)] = null
        map[toIndex(newRow, newCol)] = tile
    }

    fun update(dt: Float) {
        map.forEach {
            it?.update(dt)
        }
        tilePathRenderer?.update(dt)
    }

    fun render(sb: SpriteBatch) {
        if (numTilesMoving > 0) {
            // lazy sort, sort on every frame
            // could be optimized to sort only when required
            // although sorting an already sorted list should be quick (?) so just leave it
            // plus i don't have to care about figuring out when to sort
            orderedMap.sortByDescending { it?.isop?.y }
        }
        orderedMap.forEach { tile ->
            tile?.renderBottom(sb)
            tile?.render(sb)
        }
        tilePathRenderer?.render(sb)
    }

    fun renderTop(sb: SpriteBatch, sortedPlayers: List<Player>) {
        var playerIndex = 0
        for (i in 0 until orderedMap.size - 1) {
            val item = orderedMap[i] ?: continue
            val item2 = orderedMap[i + 1] ?: continue
            if (i < orderedMap.size - 1 && playerIndex < sortedPlayers.size) {
                val playery = -sortedPlayers[playerIndex].isop.y
                if (playery >= -item.isop.y && playery <= -item2.isop.y) {
                    sortedPlayers[playerIndex].render(sb)
                    playerIndex++
                }
            }
            item.renderTop(sb)
        }
        for (i in playerIndex until sortedPlayers.size) sortedPlayers[i].render(sb)
        orderedMap[orderedMap.size - 1]?.renderTop(sb)
    }

    companion object {
        const val TILE_SIZE = 30f
        const val TILE_IWIDTH = 60f
        const val TILE_IHEIGHT = 30f
    }

}