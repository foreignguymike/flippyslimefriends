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
            ), 2, 2)
    )

    val tileset = arrayOf(
            context.assets.getAtlas().findRegion("tileoff"),
            context.assets.getAtlas().findRegion("tileon"),
            context.assets.getAtlas().findRegion("tilegrayfloor")
    )

}

class MapData(
        val numRows: Int,
        val numCols: Int,
        var map: IntArray,
        val startRow: Int,
        val startCol: Int)