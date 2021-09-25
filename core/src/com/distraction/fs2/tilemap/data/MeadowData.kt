package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.e

object MeadowData {

    val data = listOf(
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
    )

}