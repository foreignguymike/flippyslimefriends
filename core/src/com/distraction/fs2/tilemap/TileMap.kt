package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
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
        const val TILE_HEIGHT_3D = 0
    }

    val mapData = context.gameData.mapData[level]
    val tileset = context.gameData.tileset
    val otherObjects = arrayListOf<TileObject>()

    val numRows = mapData.numRows
    val numCols = mapData.numCols
    val map = parseMapData(mapData.map)
    val bgMap = mapData.bgMap

    private val bgp = Vector3()

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
                        context,
                        this,
                        row,
                        col,
                        index)
                        .apply {
                            mapData.path?.forEach { ppd ->
                                if (row == ppd[0].tilePoint.row && col == ppd[0].tilePoint.col) {
                                    this.path = ppd
                                    moveListeners.add(tileMap)
                                }
                            }

//                            mapData.path?.let { path ->
//                                if (row == path[0].tilePoint.row && col == path[0].tilePoint.col) {
//                                    this.path = mapData.path
//                                    moveListeners.add(tileMap)
//                                }
//                            }
                        }

                mapData.objects
                        .filter { objData -> objData.row == row && objData.col == col }
                        .map { objData ->
                            when (objData) {
                                is ArrowData -> Arrow(context, this, row, col, objData.direction).apply { currentTile = tile }
                                is SuperJumpData -> SuperJump(context, this, row, col).apply { currentTile = tile }
                                is TeleportData -> Teleport(context, this, row, col, objData.destRow, objData.destCol).apply {
                                    currentTile = tile
                                    log("current tile parse: $currentTile")
                                }
                                else -> throw IllegalArgumentException("incorrect tile object data")
                            }
                        }
                        .forEach {
                            tile.objects.add(it)
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
        getTile(row, col)?.setType(index)
    }

    fun toIndex(row: Int, col: Int) = row * numCols + col

    fun getTile(row: Int, col: Int) = map[toIndex(row, col)]

    fun getTileImage(tileIndex: Int) = tileset[tileIndex]
            ?: throw IllegalStateException("invalid tile index: $tileIndex")

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

    override fun onTileStartMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
    }

    override fun onTileEndMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
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

    private fun renderBg(sb: SpriteBatch) {
        sb.color = Color.GRAY
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val image = getTileImage(bgMap[toIndex(row, col)])
                toPosition(row, col, bgp)
                toIsometric(bgp.x, bgp.y, bgp)
                sb.draw(image, bgp.x - TileMap.TILE_IWIDTH / 2, bgp.y - TileMap.TILE_IHEIGHT / 2 - 5)
            }
        }
        sb.color = Color.WHITE
    }

    fun render(sb: SpriteBatch) {
        renderBg(sb)
        for (diag in 0 until (numRows + numCols)) {
            val startCol = max(0, diag - numRows)
            val count = min(diag, min(numCols - startCol, numRows))
            for (j in 0 until count) {
                getTile(min(numRows, diag) - j - 1, startCol + j)?.renderBottom(sb)
            }
        }
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