package com.distraction.fs2.tilemap

import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas

class GameData(val context: Context) {

    val mapData = arrayOf(
            MapData(
                    7,
                    5,
                    intArrayOf(
                            0, E, 0, 0, 0,
                            E, E, E, 0, E,
                            E, E, E, 0, E,
                            E, E, E, 0, E,
                            0, E, E, E, E,
                            0, 0, 0, 0, E,
                            0, E, E, E, E

                    ),
                    0,
                    0,
                    objects = arrayListOf(
//                            SuperJumpData(0, 2)
//                            TeleportData(0, 2, 0, 3),
//                            TeleportData(0, 3, 0, 2)
                    ),
                    path = arrayListOf(
                            arrayListOf(
                                    PathPointData(TilePoint(0, 2)),
                                    PathPointData(TilePoint(3, 2))
                            ),
                            arrayListOf(
                                    PathPointData(TilePoint(0, 4)),
                                    PathPointData(TilePoint(3, 4))
                            ),
                            arrayListOf(
                                    PathPointData(TilePoint(4, 0)),
                                    PathPointData(TilePoint(4, 3))
                            ),
                            arrayListOf(
                                    PathPointData(TilePoint(6, 0)),
                                    PathPointData(TilePoint(6, 3))
                            )
                    )
            )
    )

    val tileset = arrayOf(
            context.assets.getAtlas().findRegion("tileoff"),
            context.assets.getAtlas().findRegion("tileon"),
            context.assets.getAtlas().findRegion("tileperm"),
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
        val path: ArrayList<ArrayList<PathPointData>>? = null
)

abstract class TileObjectData(val row: Int, val col: Int)
class ArrowData(row: Int, col: Int, val direction: Direction) : TileObjectData(row, col)
class SuperJumpData(row: Int, col: Int) : TileObjectData(row, col)
class TeleportData(row: Int, col: Int, val destRow: Int, val destCol: Int) : TileObjectData(row, col)

class TilePoint(var row: Int = 0, var col: Int = 0)
class PathPointData(val tilePoint: TilePoint, val time: Float = 0f)