package com.distraction.fs2.tilemap.data

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
                IceData(0, 1),
                IceData(0, 2),
                IceData(1, 1),
                IceData(1, 2),
                IceData(2, 1),
                IceData(2, 2)
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
        )
    )

}