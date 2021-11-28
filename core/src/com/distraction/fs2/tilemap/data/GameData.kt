package com.distraction.fs2.tilemap.data

import com.badlogic.gdx.graphics.Color
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

enum class Area(val text: String, val color: Color = GameColor.CALM_BLUE) {
    TUTORIAL("tutorial", GameColor.SKY_BLUE),
    MEADOW("meadow", GameColor.BRIGHT_SKY_BLUE),
    TUNDRA("tundra", GameColor.LIGHT_GRAY),
    RUINS("ruins", GameColor.PEACH),
    UNDERSEA("undersea", GameColor.DARK_BLUE),
    MATRIX("matrix", GameColor.TEAL);

    fun colorCopy() = Color(color)
}

class MapData(
    val numRows: Int,
    val numCols: Int,
    val map: IntArray,
    val target: Int,
    val playerPositions: List<TilePoint>,
    val objects: List<TileObjectData> = listOf(),
    val path: List<List<PathPointData>>? = null,
    val startBubble: Boolean = false
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

object GameColor {
    val CALM_BLUE = Color(72f / 255f, 139f / 255f, 212f / 255f, 1f)
    val BRIGHT_SKY_BLUE = Color(176 / 255f, 255 / 255f, 241 / 255f, 1f)
    val SKY_BLUE = Color(120f / 255f, 215 / 255f, 1f, 1f)
    val TEAL = Color(16f / 255f, 144f / 255f, 142f / 255f, 1f)
    val GREEN = Color(119 / 255f, 176 / 255f, 42 / 255f, 1f)
    val DARK_TEAL = Color(21f / 255f, 60 / 255f, 74 / 255f, 1f)
    val DARK_BLUE = Color(26f / 255f, 70f / 255f, 107f / 255f, 1f)
    val PURPLE = Color(91f / 255f, 83f / 255f, 125f / 255f, 1f)
    val LIGHT_GRAY = Color(199 / 255f, 212 / 255f, 225 / 255f, 1f)
    val PEACH = Color(255 / 255f, 207 / 255f, 142 / 255f, 1f)
    val BLACK_1 = Color(5f / 255f, 9f / 255f, 20f / 255f, 1f)
}

enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
}