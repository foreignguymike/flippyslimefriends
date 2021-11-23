package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.e

object RuinsData {

    val data = listOf(
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, e, e, 0,
                0, e, 0, 0,
                0, e, 0, 0,
                e, e, e, 0
            ),
            target = 4,
            playerPositions = listOf(TilePoint(2, 0)),
            path = listOf(
                listOf(
                    StopPathPointData(0, 0),
                    PathPointData(0, 1),
                    PathPointData(3, 1),
                    StopPathPointData(3, 2),
                    PathPointData(3, 1),
                    PathPointData(0, 1)
                )
            )
        )
    )
}