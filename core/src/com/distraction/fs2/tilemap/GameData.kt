package com.distraction.fs2.tilemap

import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas

class GameData(val context: Context) {

    val mapData = arrayOf(
            MapData(8, 8, intArrayOf(
                    2, 2, 2, 2, 2, 2, 2, 2,
                    2, 2, 2, 2, 2, 2, 2, 2,
                    2, 2, 0, 0, 0, 0, 2, 2,
                    2, 2, 0, 2, 2, 0, 2, 2,
                    2, 2, 0, 2, 2, 0, 2, 2,
                    2, 2, 0, 0, 0, 0, 2, 2,
                    2, 2, 2, 2, 2, 2, 2, 2,
                    2, 2, 2, 2, 2, 2, 2, 2
            ), 2, 2, arrayListOf(
                    TileObjectData.Arrow(2, 4, Direction.RIGHT),
                    TileObjectData.Arrow(4, 5, Direction.DOWN),
                    TileObjectData.Arrow(5, 3, Direction.LEFT),
                    TileObjectData.Arrow(3, 2, Direction.UP),
                    TileObjectData.SuperJump(2, 3)
            ))
    )

    val tileset = arrayOf(
            context.assets.getAtlas().findRegion("tileoff"),
            context.assets.getAtlas().findRegion("tileon"),
            context.assets.getAtlas().findRegion("tilegrayfloor"),
            context.assets.getAtlas().findRegion("tilebluegraycheckeredfloor")
    )

}

class MapData(
        val numRows: Int,
        val numCols: Int,
        var map: IntArray,
        val startRow: Int,
        val startCol: Int,
        val objects: ArrayList<TileObjectData> = arrayListOf())

abstract class TileObjectData(val row: Int, val col: Int) {
    class Arrow(row: Int, col: Int, val direction: Direction) : TileObjectData(row, col)
    class SuperJump(row: Int, col: Int) : TileObjectData(row, col)
}