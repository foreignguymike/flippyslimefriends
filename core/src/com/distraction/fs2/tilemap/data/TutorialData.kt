package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.e
import com.distraction.fs2.tilemap.data.GameData.Companion.g

object TutorialData {

    val data = listOf(
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, e, 0,
                0, 0, 0
            ),
            goal = 7,
            playerPositions = listOf(TilePoint(0, 0))
        ),
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                e, e, 0,
                0, 0, 0,
                e, e, 0
            ),
            goal = 6,
            playerPositions = listOf(TilePoint(1, 0))
        ),
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                0, g, 0,
                0, g, 0,
                0, g, 0
            ),
            goal = 7,
            playerPositions = listOf(TilePoint(0, 1))
        ),
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, e, 0,
                0, 0, 0
            ),
            goal = 6,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(2, 2))
        )
    )
}