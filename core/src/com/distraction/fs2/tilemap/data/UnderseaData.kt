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
        )
    )

}