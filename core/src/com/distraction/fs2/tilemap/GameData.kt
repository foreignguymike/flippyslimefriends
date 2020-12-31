package com.distraction.fs2.tilemap

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas

class GameData(val context: Context) {

    val mapData = arrayOf(
            MapData(numRows = 5, numCols = 3,
                    map = intArrayOf(
                            0, 0, 0,
                            0, 0, 0,
                            0, e, e,
                            0, 0, 0,
                            0, 0, 0
                    ), startRow = 0, startCol = 0,
                    path = arrayListOf(
                            arrayListOf(
                                    PathPointData(TilePoint(2, 0), 2f),
                                    PathPointData(TilePoint(2, 2), 2f)
                            )
                    ),
                    objects = arrayListOf(
                            TeleportData(0, 2, 1, 0),
                            TeleportData(1, 0, 0, 2),
                            ArrowData(2, 0, Direction.UP)
//                    SuperJumpData(2, 0)
                    )),
//            MapData(numRows = 3, numCols = 3,
//            map = intArrayOf(
//                    0,0,0,
//                    0,0,0,
//                    0,0,0
//            ), startRow = 0, startCol = 0,
//            objects = arrayListOf(
//                    ArrowData(0, 1, Direction.RIGHT),
//                    ArrowData(0, 2, Direction.RIGHT),
//                    ArrowData(0, 3, Direction.DOWN)
//            )),
//            MapData(
//                    numRows = 5, numCols = 5,
//                    map = intArrayOf(
//                            0, 0, 0, e, e,
//                            0, e, 3, e, e,
//                            0, e, 3, e, 0,
//                            e, e, e, e, 0,
//                            e, e, 0, 0, 0
//                    ),
//                    startRow = 2, startCol = 2,
//                    path = arrayListOf(
//                            arrayListOf(
//                                    PathPointData(1, 2, 2f),
//                                    PathPointData(1, 3),
//                                    PathPointData(2, 3, 2f),
//                                    PathPointData(3, 3),
//                                    PathPointData(3, 2, 2f),
//                                    PathPointData(3, 1),
//                                    PathPointData(2, 1, 2f),
//                                    PathPointData(1, 1)
//                            )
//                    )
//            ),
            MapData(
                    7,
                    5,
                    intArrayOf(
                            0, e, 0, 0, 0,
                            0, e, e, 0, e,
                            0, e, e, 0, e,
                            e, e, e, 0, e,
                            0, e, e, e, e,
                            0, 0, 0, 0, e,
                            0, e, e, e, e

                    ),
                    startRow = 0,
                    startCol = 0,
                    objects = arrayListOf(
                            SuperJumpData(0, 2)
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

    /**
     * Specific tile indices indicate different uses
     * 0-99 "walkable" tiles, these are the puzzle tiles
     * 100+ background tiles, only for show
     */
    val tileset = mapOf(
            0 to context.getImage("tileoff"),
            1 to context.getImage("tileon"),
            2 to context.getImage("tileperm"),
            3 to context.getImage("tileperm2"),
            4 to context.getImage("tileperm3"),

            100 to context.getImage("tilegrayfloor"),
            101 to context.getImage("tilebluegraycheckeredfloor"),
            102 to context.getImage("tilebluecheckeredfloor"),
            103 to context.getImage("tilegrass"),
            104 to context.getImage("tilegrasslong")
    )

    fun isWalkableTile(index: Int) = index < 100

    fun getTile(index: Int) = tileset[index] ?: error("cannot find tile $index")

    companion object {
        const val e = -1
        const val g = 100
        const val c = 101
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
class PathPointData(val tilePoint: TilePoint, val time: Float = 0f) {
    constructor(row: Int, col: Int, time: Float = 0f) : this(TilePoint(row, col), time)
}