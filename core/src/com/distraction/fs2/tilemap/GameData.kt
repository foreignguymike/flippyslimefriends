package com.distraction.fs2.tilemap

import com.distraction.fs2.Context

/**
 * All level data will be here, including map data, tile data, etc.
 */
class GameData(val context: Context) {

    val mapData = mapOf(
        Area.TUTORIAL to arrayOf(),
        Area.GRASS to arrayOf(
            MapData(
                numRows = 3, numCols = 3,
                map = intArrayOf(
                    0, 0, e,
                    0, 0, 0,
                    e, 0, 0
                ),
                target = 6,
                playerPositions = listOf(TilePoint(1, 0))
            ),
            MapData(
                numRows = 3, numCols = 4,
                map = intArrayOf(
                    e, 0, 0, 0,
                    0, 0, e, 0,
                    e, 0, 0, 0
                ),
                target = 8,
                playerPositions = listOf(TilePoint(2, 1))
            ),
            MapData(
                numRows = 4, numCols = 3,
                map = intArrayOf(
                    e, 0, 0,
                    0, 0, 0,
                    0, 0, 0,
                    0, 0, 0
                ),
                target = 12,
                playerPositions = listOf(TilePoint(1, 1))
            ),
            MapData(
                numRows = 3, numCols = 4,
                map = intArrayOf(
                    0, 0, e, e,
                    0, 0, 0, 0,
                    e, e, 0, 0
                ),
                target = 11,
                playerPositions = listOf(TilePoint(1, 2))
            ),
            MapData(
                numRows = 3, numCols = 3,
                map = intArrayOf(
                    0, 0, 0,
                    0, 0, 0,
                    0, e, 0
                ),
                target = 9,
                playerPositions = listOf(TilePoint(1, 0))
            ),
            MapData(
                numRows = 5, numCols = 3,
                map = intArrayOf(
                    e, 0, 0,
                    e, 0, 0,
                    0, 0, e,
                    0, 0, e,
                    0, 0, 0
                ),
                target = 16,
                playerPositions = listOf(TilePoint(2, 1))
            ),
            MapData(
                numRows = 4, numCols = 4,
                map = intArrayOf(
                    0, e, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, e, 0
                ),
                target = 19,
                playerPositions = listOf(TilePoint(2, 1))
            ),
            MapData(
                numRows = 3, numCols = 4,
                map = intArrayOf(
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    e, 0, 0, 0
                ),
                target = 10,
                playerPositions = listOf(TilePoint(0, 3)),
                objects = listOf(
                    ArrowData(0, 0, Direction.RIGHT)
                )
            ),
            MapData(
                numRows = 4, numCols = 3,
                map = intArrayOf(
                    0, 0, 0,
                    0, 0, 0,
                    0, 0, 0,
                    0, 0, 0
                ),
                target = 15,
                playerPositions = listOf(TilePoint(2, 2)),
                objects = listOf(
                    ArrowData(1, 1, Direction.UP),
                    ArrowData(2, 1, Direction.UP)
                )
            ),
            MapData(
                numRows = 3, numCols = 4,
                map = intArrayOf(
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0
                ),
                target = 11,
                playerPositions = listOf(TilePoint(1, 1)),
                objects = listOf(
                    ArrowData(2, 1, Direction.LEFT),
                    ArrowData(0, 1, Direction.LEFT)
                )
            )
        ),
        Area.ICE to arrayOf(),
        Area.DESERT to arrayOf(),
        Area.FLOATING to arrayOf(),
        Area.WATER to arrayOf(),
        Area.MATRIX to arrayOf()
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
    val map: IntArray,
    val target: Int,
    val playerPositions: List<TilePoint>,
    val objects: List<TileObjectData> = listOf(),
    val path: List<List<PathPointData>>? = null
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

enum class GameColor(val r: Float, val g: Float, val b: Float, val a: Float = 1f) {
    SKY_BLUE(120f / 255f, 215 / 255f, 1f),
    DARK_TEAL(21f / 255f, 60 / 255f, 74 / 255f)
}