package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.b
import com.distraction.fs2.tilemap.data.GameData.Companion.e

object TundraData {

    val data = listOf(
        MapData(
            numRows = 3, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            target = 11,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                IceData(1, 1),
                IceData(1, 2),
                IceData(1, 3)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            target = 19,
            playerPositions = listOf(TilePoint(3, 3)),
            objects = listOf(
                ArrowData(1, 0, Direction.RIGHT),
                ArrowData(2, 3, Direction.LEFT),
                IceData(1, 1),
                IceData(2, 2)
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                e, 0, 0, 0, 0
            ),
            target = 28,
            playerPositions = listOf(TilePoint(3, 4)),
            objects = listOf(
                IceData(0, 2),
                IceData(1, 1),
                IceData(1, 2),
                IceData(1, 3),
                IceData(2, 2),
                IceData(2, 3),
                IceData(3, 3)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, 0, 0, 0, e,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                e, 0, 0, 0, e
            ),
            target = 28,
            playerPositions = listOf(TilePoint(0, 2)),
            objects = listOf(
                IceData(1, 2),
                IceData(2, 1),
                IceData(2, 2),
                IceData(2, 3),
                IceData(3, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
            ),
            target = 0,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                ArrowData(4, 0, Direction.RIGHT),
                ArrowData(0, 4, Direction.LEFT),
                IceData(0, 4),
                IceData(1, 3),
                IceData(2, 2),
                IceData(3, 1),
                IceData(4, 0)
            )
        ),


        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                0, e, e, b, e,
                0, 0, 0, 0, e,
                0, e, e, 0, e,
                0, 0, 0, 0, b,
                b, e, e, e, e
            ),
            target = 10,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                IceData(1, 0),
                IceData(2, 0),
                IceData(3, 0),
                IceData(3, 1),
                IceData(3, 2),
                IceData(3, 3),
                IceData(2, 3),
                IceData(1, 3),
                IceData(1, 2)
            )
        ),
        MapData(
            numRows = 6, numCols = 5,
            map = intArrayOf(
                e, e, 0, 0, 0,
                e, e, 0, b, 0,
                e, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, e, e
            ),
            target = 23,
            playerPositions = listOf(TilePoint(3, 1)),
            objects = listOf(
                IceData(2, 2),
                IceData(2, 3),
                SuperJumpData(3, 2),
                IceData(3, 3),
                IceData(4, 2)
            )
        ),



        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                e, e, 0, e, e,
                0, 0, 0, 0, 0,
                e, e, 0, e, e
            ),
            target = 7,
            playerPositions = listOf(TilePoint(1, 0), TilePoint(1, 4)),
            objects = listOf(
                IceData(1, 1),
                IceData(1, 2),
                IceData(1, 3)
            )
        )
    )
}