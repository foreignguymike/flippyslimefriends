package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.e

object RuinsData {

    val data = listOf(
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                0, e, e,
                0, e, 0,
                0, e, 0
            ),
            target = 4,
            playerPositions = listOf(TilePoint(2, 0)),
            path = listOf(
                listOf(
                    StopPathPointData(0, 0),
                    StopPathPointData(0, 2)
                )
            )
        ),
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                0, 0, e,
                0, 0, 0,
                e, 0, 0
            ),
            target = 6,
            playerPositions = listOf(TilePoint(0, 0)),
            path = listOf(
                listOf(
                    StopPathPointData(1, 0),
                    StopPathPointData(2, 0)
                ),
                listOf(
                    StopPathPointData(1, 2),
                    StopPathPointData(0, 2)
                )
            )
        ),
        MapData(
            numRows = 5, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, e, 0,
                e, e, e,
                0, 0, 0,
                0, 0, 0
            ),
            target = 10,
            playerPositions = listOf(TilePoint(3, 2)),
            path = listOf(
                listOf(
                    StopPathPointData(3, 1),
                    StopPathPointData(1, 1)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, e, e, e,
                e, e, e, 0,
                0, 0, 0, 0
            ),
            target = 9,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                SuperJumpData(1, 0),
                SuperJumpData(2, 3)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 0),
                    StopPathPointData(1, 1)
                ),
                listOf(
                    StopPathPointData(2, 3),
                    StopPathPointData(2, 2)
                )
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                0, 0, e, e, 0,
                0, 0, 0, 0, 0,
                0, e, e, 0, 0
            ),
            target = 10,
            playerPositions = listOf(TilePoint(0, 0)),
            path = listOf(
                listOf(
                    StopPathPointData(0, 1),
                    StopPathPointData(0, 3)
                ),
                listOf(
                    StopPathPointData(2, 3),
                    StopPathPointData(2, 1)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                e, 0, 0, 0, 0,
                e, e, 0, 0, e,
                0, 0, 0, 0, e,
                e, e, 0, 0, e
            ),
            target = 11,
            playerPositions = listOf(TilePoint(2, 3)),
            path = listOf(
                listOf(
                    StopPathPointData(0, 4),
                    StopPathPointData(3, 4)
                ),
                listOf(
                    StopPathPointData(1, 2),
                    StopPathPointData(1, 0)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                0, 0, 0, e, e,
                0, e, 0, 0, 0,
                0, 0, 0, e, 0,
                e, e, 0, 0, 0
            ),
            target = 13,
            playerPositions = listOf(TilePoint(2, 0)),
            objects = listOf(
                ArrowData(1, 3, Direction.LEFT),
                ArrowData(0, 1, Direction.RIGHT)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(2, 1),
                    StopPathPointData(1, 1)
                ),
                listOf(
                    StopPathPointData(3, 3),
                    StopPathPointData(2, 3)
                )
            )
        ),
        MapData(
            numRows = 3, numCols = 4,
            map = intArrayOf(
                e, 0, 0, e,
                0, e, 0, 0,
                0, 0, 0, 0
            ),
            target = 10,
            playerPositions = listOf(TilePoint(0, 1)),
            objects = listOf(
                ArrowData(2, 3, Direction.LEFT),
                ArrowData(2, 0, Direction.RIGHT)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 2),
                    StopPathPointData(1, 1)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 3,
            map = intArrayOf(
                e, 0, 0,
                0, e, 0,
                0, e, 0,
                0, 0, 0
            ),
            target = 12,
            playerPositions = listOf(TilePoint(0, 1)),
            path = listOf(
                listOf(
                    StopPathPointData(2, 0),
                    StopPathPointData(2, 1),
                    StopPathPointData(1, 1),
                    PathPointData(2, 1)
                )
            ),
            objects = listOf(
                ArrowData(3, 1, Direction.RIGHT),
                ArrowData(1, 2, Direction.DOWN)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, e, e,
                0, e, e, e,
                0, 0, 0, 0,
                e, 0, 0, 0
            ),
            target = 15,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                SuperJumpData(2, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(2, 2),
                    PathPointData(1, 2),
                    StopPathPointData(1, 1),
                    PathPointData(1, 2)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                e, 0, 0, 0,
                0, e, e, 0,
                0, e, 0, 0,
                0, 0, 0, e
            ),
            target = 18,
            playerPositions = listOf(TilePoint(3, 0)),
            objects = listOf(
                SuperJumpData(2, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(2, 2),
                    StopPathPointData(1, 2),
                    StopPathPointData(1, 1),
                    StopPathPointData(2, 1),
                    PathPointData(2, 2)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, e, 0, 0,
                0, 0, e, 0,
                0, e, 0, 0
            ),
            target = 16,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                ArrowData(3, 3, Direction.LEFT),
                ArrowData(0, 3, Direction.LEFT)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 0),
                    StopPathPointData(1, 1)
                ),
                listOf(
                    StopPathPointData(1, 2),
                    StopPathPointData(2, 2)
                ),
                listOf(
                    StopPathPointData(3, 0),
                    StopPathPointData(3, 1)
                )
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, e, e, 0
            ),
            target = 18,
            playerPositions = listOf(TilePoint(1, 2)),
            objects = listOf(
                ArrowData(1, 3, Direction.LEFT),
                ArrowData(1, 1, Direction.RIGHT),
                SuperJumpData(0, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(2, 1),
                    StopPathPointData(2, 2),
                    StopPathPointData(2, 3),
                    PathPointData(2, 2)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                e, e, 0, e, e,
                e, 0, 0, 0, e,
                0, 0, e, 0, 0,
                0, 0, 0, 0, 0
            ),
            target = 15,
            playerPositions = listOf(TilePoint(3, 0), TilePoint(3, 4)),
            objects = listOf(ArrowData(1, 2, Direction.RIGHT)),
            path = listOf(
                listOf(
                    StopPathPointData(1, 2),
                    StopPathPointData(2, 2)
                )
            )
        )
    )
}