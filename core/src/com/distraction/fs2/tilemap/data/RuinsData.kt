package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.e

object RuinsData {

    val data = listOf(
        MapData(
            numRows = 4, numCols = 3,
            map = intArrayOf(
                0, e, e,
                0, e, 0,
                0, e, 0,
                e, e, e
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
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, e, e,
                0, e, e, e,
                e, e, e, e,
                e, e, e, e
            ),
            target = 4,
            playerPositions = listOf(TilePoint(0, 0)),
            path = listOf(
                listOf(
                    StopPathPointData(0, 1),
                    StopPathPointData(0, 3)
                ),
                listOf(
                    StopPathPointData(1, 0),
                    StopPathPointData(3, 0)
                )
            )
        )
    )
}