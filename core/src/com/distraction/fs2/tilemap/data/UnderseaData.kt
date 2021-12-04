package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.e

object UnderseaData {

    val data = listOf(
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                e, e, e,
                0, 0, 0
            ),
            target = 5,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(BubbleData(0, 2))
        ),
        MapData(
            numRows = 3, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            target = 12,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                ArrowData(1, 2, Direction.UP),
                ArrowData(1, 1, Direction.DOWN)
            ),
            startBubble = true
        ),
        MapData(
            numRows = 5, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, e, 0,
                0, 0, 0,
                0, e, 0,
                0, 0, 0
            ),
            target = 13,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                0, 0, e, 0, 0,
                0, 0, e, 0, 0,
                0, e, e, 0, 0,
                e, e, 0, 0, e
            ),
            target = 13,
            playerPositions = listOf(TilePoint(1, 2)),
            startBubble = true,
            objects = listOf(
                BubbleData(1, 3)
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
            target = 16,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.DOWN),
                ArrowData(1, 1, Direction.DOWN)
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
            target = 16,
            playerPositions = listOf(TilePoint(2, 1)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.DOWN),
                ArrowData(2, 0, Direction.DOWN),
                ArrowData(1, 2, Direction.UP),
                ArrowData(1, 0, Direction.UP)
            )
        ),
        MapData(
            numRows = 5, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, 0, 0,
                0, 0, 0,
                0, 0, 0,
                0, 0, 0
            ),
            target = 21,
            playerPositions = listOf(TilePoint(2, 1)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.UP),
                ArrowData(2, 0, Direction.UP),
                SuperJumpData(2, 1)
            )
        )
    )

}