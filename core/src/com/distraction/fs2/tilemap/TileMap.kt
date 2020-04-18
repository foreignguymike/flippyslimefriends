package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.tilemap.TileObjectType.*
import com.distraction.fs2.tilemap.tileobjects.Arrow
import com.distraction.fs2.tilemap.tileobjects.Player
import com.distraction.fs2.tilemap.tileobjects.SuperJump
import com.distraction.fs2.tilemap.tileobjects.Teleport

class Tile(val context: Context, val value: Int, var active: Boolean = false) {
    val objects = arrayListOf<TileObject>()
    fun toggleActive() {
        active = !active
    }

    fun getImage(): TextureAtlas.AtlasRegion = if (active) context.assets.getAtlas().findRegion("tilea") else context.assets.getAtlas().findRegion("tile")
}

class TileMap(private val context: Context, val levelData: TileMapDataModel) {

    companion object {
        const val TILE_WIDTH = 32f
        const val TILE_IWIDTH = 16f
        const val TILE_IHEIGHT = 8f
    }

    private val p = Vector3()
    private val grid = Array(levelData.grid.size) {
        Tile(context, levelData.grid[it], levelData.grid[it] == 2)
    }
    val otherObjects = arrayListOf<TileObject>()

    init {
        levelData.objs.forEach {
            val tile = getTile(it.row, it.col)
            when (it.type) {
                ARROW_RIGHT -> tile.objects.add(Arrow(context, this, it.row, it.col, Player.Direction.RIGHT))
                ARROW_LEFT -> tile.objects.add(Arrow(context, this, it.row, it.col, Player.Direction.LEFT))
                ARROW_DOWN -> tile.objects.add(Arrow(context, this, it.row, it.col, Player.Direction.DOWN))
                ARROW_UP -> tile.objects.add(Arrow(context, this, it.row, it.col, Player.Direction.UP))
                SUPER_JUMP -> tile.objects.add(SuperJump(context, this, it.row, it.col))
                TELEPORT -> {
                    if (it is TeleportDataModel) {
                        val teleport = Teleport(context, this, it.row, it.col, it.row2, it.col2)
                        tile.objects.add(teleport)
                        val tile2 = getTile(teleport.row2, teleport.col2)
                        tile2.objects.add(teleport)
                    }
                }
            }

        }
    }

    fun update(dt: Float) {
        for (row in 0 until levelData.numRows) {
            for (col in 0 until levelData.numCols) {
                val tile = getTile(row, col)
                tile.objects.forEach {
                    it.update(dt)
                }
            }
        }
        otherObjects.forEach {
            it.update(dt)
        }
        otherObjects.removeAll {
            it.remove
        }
    }

    fun render(sb: SpriteBatch) {
        for (row in 0 until levelData.numRows) {
            for (col in 0 until levelData.numCols) {
                val tile = getTile(row, col)
                if (tile.value != 0) {
                    toIsometric(col * TILE_WIDTH, row * TILE_WIDTH, p)
                    sb.draw(tile.getImage(), p.x - TILE_WIDTH / 2, p.y - TILE_IHEIGHT * 2)
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

    fun toPosition(row: Int, col: Int, p: Vector3) {
        p.x = col * TILE_WIDTH
        p.y = row * TILE_WIDTH
    }

    fun toPosition(tile: Int) = tile * TILE_WIDTH

    fun toIsometric(x: Float, y: Float, p: Vector3) {
        val xo = x / TILE_WIDTH
        val yo = y / TILE_WIDTH
        p.x = (xo - yo) * TILE_IWIDTH
        p.y = (-xo - yo) * TILE_IHEIGHT
    }

    fun isValidTile(row: Int, col: Int): Boolean {
        if (row < 0 || row >= levelData.numRows || col < 0 || col >= levelData.numCols) return false
        return getTile(row, col).value != 0
    }

    fun contains(row: Int, col: Int) = row >= 0 && row < levelData.numRows && col >= 0 && col <= levelData.numCols && getTile(row, col).value != 0

    fun getTile(row: Int, col: Int) = grid[row * levelData.numCols + col]

    fun isFinished(): Boolean {
        for (row in 0 until levelData.numRows) {
            for (col in 0 until levelData.numCols) {
                val tile = getTile(row, col)
                if (tile.value != 0 && !tile.active) {
                    return false
                }
            }
        }
        return true
    }

}