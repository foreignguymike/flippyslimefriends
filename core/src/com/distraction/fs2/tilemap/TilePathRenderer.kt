package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.distraction.fs2.Context
import com.distraction.fs2.drawCentered
import com.distraction.fs2.resetColor
import com.distraction.fs2.setColor
import com.distraction.fs2.tilemap.data.Direction
import com.distraction.fs2.tilemap.data.GameColor
import com.distraction.fs2.tilemap.data.PathPointData
import com.distraction.fs2.tilemap.tileobjects.TileObject

class TilePathRenderer(
    context: Context,
    val tileMap: TileMap,
    pathData: List<List<PathPointData>>
) {

    private val renderList = mutableListOf<TilePathRenderObject>()
    private val renderCenters = mutableListOf<TilePathRenderObject>()

    private var alpha = 1f
    private var timer = 0f

    init {
        pathData.forEach { path ->
            var currRow = path[0].tilePoint.row
            var currCol = path[0].tilePoint.col
            for (i in 0 until path.size - 1) {
                val path1 = path[i]
                val path2 = path[i + 1]
                while (currRow != path2.tilePoint.row || currCol != path2.tilePoint.col) {
                    var newRow = currRow
                    var newCol = currCol
                    val direction: Direction
                    when {
                        path2.tilePoint.row < path1.tilePoint.row -> {
                            direction = Direction.UP
                            newRow = currRow - 1
                        }
                        path2.tilePoint.row > path1.tilePoint.row -> {
                            direction = Direction.DOWN
                            newRow = currRow + 1
                        }
                        path2.tilePoint.col < path1.tilePoint.col -> {
                            direction = Direction.LEFT
                            newCol = currCol - 1
                        }
                        path2.tilePoint.col > path1.tilePoint.col -> {
                            direction = Direction.RIGHT
                            newCol = currCol + 1
                        }
                        else -> throw IllegalArgumentException("invalid path")
                    }
                    renderList.add(
                        TilePathRenderObject(
                            context,
                            tileMap,
                            direction,
                            currRow,
                            currCol
                        )
                    )
                    renderList.add(
                        TilePathRenderObject(
                            context,
                            tileMap,
                            direction.opposite(),
                            newRow,
                            newCol
                        )
                    )
                    currRow = newRow
                    currCol = newCol
                }
            }
            path.forEach {
                if (it.time > 0f) {
                    renderCenters.add(
                        TilePathRenderObject(
                            context,
                            tileMap,
                            null,
                            it.tilePoint.row,
                            it.tilePoint.col
                        )
                    )
                }
            }
        }
        renderList.sortBy { it.direction?.ordinal }
    }

    fun update(dt: Float) {
        timer += dt
        alpha = MathUtils.sin(timer * MathUtils.PI) * 0.375f + 0.5f
    }

    fun render(sb: SpriteBatch) {
        sb.setColor(1f, 1f, 1f, alpha)
        renderList.forEach { it.render(sb) }
        sb.resetColor()
        renderCenters.forEach { it.render(sb) }
    }

    internal inner class TilePathRenderObject(
        context: Context,
        tileMap: TileMap,
        val direction: Direction?,
        row: Int,
        col: Int
    ) : TileObject(context, tileMap) {

        private val center = context.getImage("tilepathcenter")
        private val left = context.getImage("tilepathleft")
        private val up = context.getImage("tilepathup")
        private val right = context.getImage("tilepathright")
        private val down = context.getImage("tilepathdown")

        init {
            setPositionFromTile(row, col)
        }

        override fun update(dt: Float) {
        }

        override fun render(sb: SpriteBatch) {
            tileMap.toIsometric(p.x, p.y, isop)
            when (direction) {
                Direction.UP -> sb.drawCentered(up, isop.x + up.regionWidth / 2 - 1, isop.y + up.regionHeight / 2 - 1)
                Direction.RIGHT -> sb.drawCentered(right, isop.x + right.regionWidth / 2 - 1, isop.y - right.regionHeight / 2 + 2)
                Direction.DOWN -> sb.drawCentered(down, isop.x - down.regionWidth / 2, isop.y - down.regionHeight / 2 + 2)
                Direction.LEFT -> sb.drawCentered(left, isop.x - left.regionWidth / 2, isop.y + left.regionHeight / 2 - 1)
                null -> sb.drawCentered(center, isop.x, isop.y)
            }
        }
    }

}