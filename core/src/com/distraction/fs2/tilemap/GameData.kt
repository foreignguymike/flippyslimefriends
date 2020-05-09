package com.distraction.fs2.tilemap

import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas

class GameData(val context: Context) {

    val mapData = arrayOf(
            MapData(
                    4,
                    3,
                    intArrayOf(
                            0, 0, 0,
                            0, E, E,
                            0, E, E,
                            0, E, E
                    ),
                    0,
                    0,
                    path = arrayOf(
                            PathData(TilePoint(0, 2), 0f),
                            PathData(TilePoint(3, 2)),
                            PathData(TilePoint(3, 1), 0f),
                            PathData(TilePoint(3, 2))
                    )
            )
    )

    val tileset = arrayOf(
            context.assets.getAtlas().findRegion("tileoff"),
            context.assets.getAtlas().findRegion("tileon"),
            context.assets.getAtlas().findRegion("tilegrayfloor"),
            context.assets.getAtlas().findRegion("tilebluegraycheckeredfloor")
    )

    companion object {
        const val E = -1
    }

}

class MapData(
        val numRows: Int,
        val numCols: Int,
        var map: IntArray,
        val startRow: Int,
        val startCol: Int,
        val objects: ArrayList<TileObjectData> = arrayListOf(),
        val path: Array<PathData>? = null
)

abstract class TileObjectData(val row: Int, val col: Int)
class ArrowData(row: Int, col: Int, val direction: Direction) : TileObjectData(row, col)
class SuperJumpData(row: Int, col: Int) : TileObjectData(row, col)
class TeleportData(row: Int, col: Int, val destRow: Int, val destCol: Int) : TileObjectData(row, col)

class TilePoint(var row: Int = 0, var col: Int = 0)
class PathData(val tilePoint: TilePoint, val time: Float = 0f)