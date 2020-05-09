package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.draw
import com.distraction.fs2.getAtlas
import com.distraction.fs2.tilemap.tileobjects.TileObject

class TileMap(val context: Context, level: Int) {

    companion object {
        const val TILE_SIZE = 30f
        const val TILE_IWIDTH = 60f
        const val TILE_IHEIGHT = 31f

        val VALID_TILES = IntArray(2) { it }
    }

    val mapData = context.gameData.mapData[level]
    val tileset = context.gameData.tileset

    val numRows = mapData.numRows
    val numCols = mapData.numCols
    val map = parseMap(mapData.map)

    private val p = Vector3()
    private val pv = Vector3()

    val otherObjects = arrayListOf<TileObject>()

    val pixel = context.assets.getAtlas().findRegion("pixel")

    private fun parseMap(map: IntArray): Array<Tile2> {
        return Array(map.size) {
            val index = map[it]
            Tile2(index, getTileImage(index))
        }
    }

    fun toggleTile(row: Int, col: Int) {
        when (getTile(row, col).index) {
            0 -> updateTile(row, col, 1)
            1 -> updateTile(row, col, 0)
        }
    }

    fun updateTile(row: Int, col: Int, index: Int) {
        getTile(row, col).update(index, getTileImage(index))
    }

    fun getTile(row: Int, col: Int) = map[row * numCols + col]

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
        return getTile(row, col).index in VALID_TILES
    }

    fun isFinished(): Boolean {
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val tile = getTile(row, col)
                if (tile.index == 0) {
                    return false
                }
            }
        }
        return true
    }

    fun update(dt: Float) {

    }

    fun render(sb: SpriteBatch) {
        for (row in 0 until mapData.numRows) {
            for (col in 0 until mapData.numCols) {
                val tile = getTile(row, col)
                if (tile.index >= 0) {
                    toIsometric(col * TILE_SIZE, row * TILE_SIZE, p)
                    sb.draw(tile.image, p.x - TILE_IWIDTH / 2, p.y - TILE_IHEIGHT / 2)
//                    sb.draw(pixel, p)

//                    toIsometric(col * TILE_SIZE, row * TILE_SIZE, pv)
//                    sb.color = Color.RED
//                    sb.draw(pixel, p.x, p.y, TILE_IWIDTH, TILE_IHEIGHT)
//                    sb.color = Color.WHITE
                }
                tile.objects.forEach {
                    it.render(sb)
                }
            }
        }
    }

    fun renderOther(sb: SpriteBatch) {
        otherObjects.forEach {
            it.render(sb)
        }
    }

}

class Tile2(var index: Int, var image: TextureRegion) {
    val objects = arrayListOf<TileObject>()
    fun update(index: Int, image: TextureRegion) {
        this.index = index
        this.image = image
    }
}