package com.distraction.fs2.tilemap.data

import com.distraction.fs2.Context

/**
 * All level data will be here, including map data, tile data, etc.
 */
class GameData(val context: Context) {

    val mapData = mapOf(
        Area.TUTORIAL to TutorialData.data,
        Area.MEADOW to MeadowData.data,
        Area.TUNDRA to TundraData.data,
        Area.RUINS to RuinsData.data,
        Area.UNDERSEA to UnderseaData.data,
        Area.MATRIX to listOf()
    )

    fun getMapData(area: Area) =
        mapData[area] ?: throw IllegalStateException("could not find map data for ${area.text}")

    /**
     * Specific tile indices indicate different uses
     * 0-99 "walkable" tiles, these are the puzzle tiles
     * 100+ background tiles, only for show
     */
    private val tileset = mapOf(
        0 to context.getImage("tileoff"),
        1 to context.getImage("tileon"),
        5 to context.getImage("tileblocked"),

        100 to context.getImage("tilegrayfloor")
    )

    fun isWalkableTile(index: Int) = index < 100

    fun getTile(index: Int) = tileset[index] ?: error("cannot find tile $index")

    companion object {
        const val e = -1
        const val b = 5
        const val g = 100 // non active tile
    }

}

enum class Area(val text: String) {
    TUTORIAL("tutorial"),
    MEADOW("meadow"),
    TUNDRA("tundra"),
    RUINS("ruins"),
    UNDERSEA("undersea"),
    MATRIX("matrix")
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
class BubbleData(row: Int, col: Int) : TileObjectData(row, col)

class TilePoint(var row: Int = 0, var col: Int = 0)
open class PathPointData(val tilePoint: TilePoint, val time: Float = 0f) {
    constructor(row: Int, col: Int, time: Float = 0f) : this(TilePoint(row, col), time)
}
class StopPathPointData(row: Int, col: Int) : PathPointData(TilePoint(row, col), DEFAULT_STOP_TIME) {
    companion object {
        const val DEFAULT_STOP_TIME = 3f
    }
}

enum class GameColor(val r: Float, val g: Float, val b: Float, val a: Float = 1f) {
    CALM_BLUE(72f / 255f, 139f / 255f, 212f / 255f, 1f),
    SKY_BLUE(120f / 255f, 215 / 255f, 1f),
    TEAL(16f / 255f, 144f / 255f, 142f / 255f, 1f),
    DARK_TEAL(21f / 255f, 60 / 255f, 74 / 255f),
    DARK_BLUE(26f / 255f, 70f / 255f, 107f / 255f, 1f),
    PURPLE(91f / 255f, 83f / 255f, 125f / 255f, 1f),
    BLACK_1(5f / 255f, 9f / 255f, 20f / 255f, 1f)
}

enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
}