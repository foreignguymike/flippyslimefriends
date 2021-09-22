package com.distraction.fs2.tilemap

import com.distraction.fs2.Context

/**
 * All level data will be here, including map data, tile data, etc.
 */
class GameData(val context: Context) {

    val mapData = mapOf(
        Area.TUTORIAL to arrayOf<MapData>(),
        Area.GRASS to arrayOf(
            MapData(
                numRows = 3, numCols = 3,
                map = intArrayOf(
                    0, 0, e,
                    0, 0, 0,
                    e, 0, 0
                ),
                playerPositions = listOf(TilePoint(1, 0))
            )
        )
    )

    /**
     * Specific tile indices indicate different uses
     * 0-99 "walkable" tiles, these are the puzzle tiles
     * 100+ background tiles, only for show
     */
    private val tileset = mapOf(
        0 to context.getImage("tileoff"),
        1 to context.getImage("tileon"),
        2 to context.getImage("tileperm"),
        3 to context.getImage("tileperm2"),
        4 to context.getImage("tileperm3"),
        5 to context.getImage("tileblocked"),

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

enum class Area {
    TUTORIAL,
    GRASS,
    ICE,
    DESERT,
    FLOATING,
    WATER,
    MATRIX
}

class MapData(
    val numRows: Int,
    val numCols: Int,
    var map: IntArray,
    val playerPositions: List<TilePoint>,
    val objects: ArrayList<TileObjectData> = arrayListOf(),
    val path: ArrayList<ArrayList<PathPointData>>? = null
)

abstract class TileObjectData(val row: Int, val col: Int)
class ArrowData(row: Int, col: Int, val direction: Direction) : TileObjectData(row, col)
class SuperJumpData(row: Int, col: Int) : TileObjectData(row, col)
class IceData(row: Int, col: Int) : TileObjectData(row, col)
class TeleportData(row: Int, col: Int, val destRow: Int, val destCol: Int) :
    TileObjectData(row, col)

class TilePoint(var row: Int = 0, var col: Int = 0)
class PathPointData(val tilePoint: TilePoint, val time: Float = 0f) {
    constructor(row: Int, col: Int, time: Float = 0f) : this(TilePoint(row, col), time)
}