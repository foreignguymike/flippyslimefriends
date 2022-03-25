package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.e
import com.distraction.fs2.tilemap.data.GameData.Companion.g

object MatrixData {

    val data = listOf(
        MapData(
            numRows = 1, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0
            ),
            goal = 8,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                FinishTileData(0, 2)
            )
        ),
        MapData(
            numRows = 2, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
            ),
            goal = 12,
            playerPositions = listOf(TilePoint(1, 0), TilePoint(0, 4)),
            objects = listOf(
                ArrowData(1, 2, Direction.LEFT),
                ArrowData(0, 2, Direction.RIGHT),
                FinishTileData(0, 0),
                FinishTileData(1, 4)
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                e, 0, 0, 0, e,
                0, e, 0, e, 0,
                0, 0, 0, 0, 0
            ),
            goal = 11,
            playerPositions = listOf(TilePoint(1, 0)),
            objects = listOf(
                TeleportData(0, 1, 1, 4),
                TeleportData(1, 4, 0, 1)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, e, 0,
                0, e, 0, 0,
                0, 0, 0, 0
            ),
            goal = 17,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                FinishTileData(3, 3),
                ArrowData(3, 1, Direction.RIGHT),
                ArrowData(0, 2, Direction.LEFT)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 1),
                    StopPathPointData(1, 2)
                ),
                listOf(
                    StopPathPointData(2, 2),
                    StopPathPointData(2, 1)
                )
            )
        ),
        MapData(
            numRows = 3, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            goal = 19,
            playerPositions = listOf(TilePoint(2, 0)),
            objects = listOf(
                ArrowData(1, 3, Direction.UP),
                ArrowData(1, 2, Direction.DOWN),
                ArrowData(1, 1, Direction.DOWN),
                ArrowData(1, 0, Direction.UP),
                TeleportData(2, 3, 0, 0),
                TeleportData(0, 0, 2, 3)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, 0, 0, e, e,
                0, 0, 0, 0, e,
                e, 0, 0, 0, 0,
                0, e, 0, 0, 0,
                e, 0, e, 0, e
            ),
            goal = 17,
            playerPositions = listOf(TilePoint(1, 3)),
            objects = listOf(
                ArrowData(4, 3, Direction.LEFT),
                ArrowData(4, 1, Direction.UP),
                ArrowData(3, 0, Direction.RIGHT),
                ArrowData(1, 0, Direction.DOWN),
                SuperJumpData(1, 0),
                SuperJumpData(2, 2),
                SuperJumpData(3, 0),
                SuperJumpData(4, 1),
                SuperJumpData(4, 3),
                TeleportData(0, 2, 2, 4),
                TeleportData(2, 4, 0, 2)
            )
        ),
        MapData(
            numRows = 1, numCols = 6,
            map = intArrayOf(
                0, 0, g, e, 0, 0
            ),
            goal = 8,
            playerPositions = listOf(TilePoint(0, 1), TilePoint(0, 4)),
            objects = listOf(
                FinishTileData(0, 1),
                SuperJumpData(0, 2),
                FinishTileData(0, 4)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 2),
                    StopPathPointData(0, 3)
                )
            )
        )
    )

}