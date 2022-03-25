package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.distraction.fs2.Context
import com.distraction.fs2.drawCentered
import com.distraction.fs2.resetColor
import com.distraction.fs2.tilemap.data.Direction
import com.distraction.fs2.tilemap.data.PathPointData
import com.distraction.fs2.tilemap.tileobjects.TileObject

class TilePathRenderer(
    context: Context,
    val tileMap: TileMap,
    pathData: List<List<PathPointData>>
) {

    private val renderList = mutableListOf<TilePathRenderObject>()
    private val renderCenters = mutableListOf<TilePathRenderObject>()

    private val orderedList: List<TilePathRenderObject>

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
        orderedList = renderList.sortedBy { it.direction?.ordinal }.distinct()
    }

    fun update(dt: Float) {
        timer += dt

        for (i in renderList.indices) {
            renderList[i].alpha = MathUtils.sin((i * -0.2f + timer) * 1.5f * MathUtils.PI) * 0.375f + 0.5f
        }
        renderCenters.forEach { it.alpha = (renderList[0].alpha + 1) / 2f }
    }

    fun render(sb: SpriteBatch) {
        orderedList.forEach { it.render(sb) }
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

        var alpha = 1f

        init {
            setPositionFromTile(row, col)
        }

        override fun update(dt: Float) {
        }

        override fun render(sb: SpriteBatch) {
            tileMap.toIsometric(p.x, p.y, isop)
            sb.setColor(1f, 1f, 1f, alpha)
            when (direction) {
                Direction.UP -> sb.drawCentered(
                    up,
                    isop.x + up.regionWidth / 2 - 1,
                    isop.y + up.regionHeight / 2 - 1
                )
                Direction.RIGHT -> sb.drawCentered(
                    right,
                    isop.x + right.regionWidth / 2 - 1,
                    isop.y - right.regionHeight / 2 + 2
                )
                Direction.DOWN -> sb.drawCentered(
                    down,
                    isop.x - down.regionWidth / 2,
                    isop.y - down.regionHeight / 2 + 2
                )
                Direction.LEFT -> sb.drawCentered(
                    left,
                    isop.x - left.regionWidth / 2,
                    isop.y + left.regionHeight / 2 - 1
                )
                null -> sb.drawCentered(center, isop.x, isop.y)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TilePathRenderObject

            if (direction != other.direction) return false
            if (row != other.row) return false
            if (col != other.col) return false

            return true
        }

        override fun hashCode() = 0

    }

}