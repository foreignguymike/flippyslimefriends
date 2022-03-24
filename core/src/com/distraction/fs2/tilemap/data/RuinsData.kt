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
            goal = 4,
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
            goal = 6,
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
            goal = 10,
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
            goal = 9,
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
            goal = 10,
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
            goal = 11,
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
            numRows = 6, numCols = 5,
            map = intArrayOf(
                e, e, e, e, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, e, 0, 0, 0,
                e, e, 0, 0, 0
            ),
            goal = 14,
            playerPositions = listOf(TilePoint(4, 3)),
            objects = listOf(
                ArrowData(3, 3, Direction.DOWN),
                ArrowData(3, 2, Direction.UP)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(3, 1),
                    PathPointData(3, 0),
                    PathPointData(0, 0),
                    PathPointData(0, 4),
                    StopPathPointData(3, 4),
                    PathPointData(0, 4),
                    PathPointData(0, 0),
                    PathPointData(3, 0),
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
            goal = 13,
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
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, 0, e, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, e, e, e, e
            ),
            goal = 11,
            playerPositions = listOf(TilePoint(1, 2)),
            objects = listOf(
                SuperJumpData(2, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 2),
                    PathPointData(0, 4),
                    StopPathPointData(2, 4),
                    PathPointData(4, 4),
                    StopPathPointData(4, 2),
                    PathPointData(4, 0),
                    StopPathPointData(2, 0),
                    PathPointData(0, 0),
                    PathPointData(0, 2)
                )
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, e, e, e,
                e, 0, e, 0, e,
                e, e, 0, 0, e,
                e, 0, 0, 0, e,
                e, e, e, e, e
            ),
            goal = 8,
            playerPositions = listOf(TilePoint(3, 1)),
            path = listOf(
                listOf(
                    StopPathPointData(3, 2),
                    PathPointData(4, 2),
                    PathPointData(4, 0),
                    PathPointData(2, 0),
                    StopPathPointData(2, 1),
                    PathPointData(2, 0),
                    PathPointData(4, 0),
                    PathPointData(4, 2),
                    PathPointData(3, 2)
                ),
                listOf(
                    StopPathPointData(2, 3),
                    PathPointData(2, 4),
                    PathPointData(0, 4),
                    PathPointData(0, 2),
                    StopPathPointData(1, 2),
                    PathPointData(0, 2),
                    PathPointData(0, 4),
                    PathPointData(2, 4),
                    PathPointData(2, 3)
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
            goal = 10,
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
                0, e, e,
                0, 0, 0,
                0, e, e,
                0, 0, e
            ),
            goal = 8,
            playerPositions = listOf(TilePoint(1, 0)),
            objects = listOf(
                ArrowData(2, 0, Direction.DOWN),
                ArrowData(1, 1, Direction.RIGHT)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 0),
                    StopPathPointData(0, 2)
                ),
                listOf(
                    StopPathPointData(3, 1),
                    PathPointData(2, 1),
                    StopPathPointData(2, 2),
                    PathPointData(3, 2),
                    PathPointData(3, 1)
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
            goal = 12,
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
            numRows = 4, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                e, 0, 0,
                0, 0, 0,
                e, 0, 0
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(3, 2)),
            objects = listOf(
                ArrowData(2, 1, Direction.UP),
                ArrowData(1, 2, Direction.DOWN)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 0),
                    StopPathPointData(1, 0)
                ),
                listOf(
                    StopPathPointData(2, 0),
                    StopPathPointData(3, 0),
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                e, e, 0, e, e,
                e, e, 0, 0, e,
                0, e, 0, e, e,
                0, 0, 0, 0, 0
            ),
            goal = 11,
            playerPositions = listOf(TilePoint(3, 2)),
            objects = listOf(
                ArrowData(2, 2, Direction.UP)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(2, 0),
                    StopPathPointData(2, 1),
                    StopPathPointData(1, 1),
                    PathPointData(2, 1)
                ),
                listOf(
                    StopPathPointData(1, 3),
                    StopPathPointData(2, 3),
                    StopPathPointData(2, 4),
                    PathPointData(2, 3)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                e, e, e, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                e, 0, e, e
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(2, 3)),
            objects = listOf(
                ArrowData(2, 1, Direction.RIGHT),
                ArrowData(1, 2, Direction.LEFT)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(3, 1),
                    StopPathPointData(3, 3)
                ),
                listOf(
                    StopPathPointData(0, 3),
                    StopPathPointData(0, 1)
                )
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, 0, e, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, e, e, e, e
            ),
            goal = 11,
            playerPositions = listOf(TilePoint(3, 1), TilePoint(3, 3)),
            objects = listOf(
                ArrowData(2, 3, Direction.DOWN),
                ArrowData(2, 1, Direction.DOWN),
                SuperJumpData(2, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 2),
                    PathPointData(0, 4),
                    PathPointData(4, 4),
                    StopPathPointData(4, 2),
                    PathPointData(4, 0),
                    PathPointData(0, 0),
                    PathPointData(0, 2)
                )
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
            goal = 15,
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
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, e, e, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, 0, 0, e, e,
                e, e, e, e, e
            ),
            goal = 11,
            playerPositions = listOf(TilePoint(3, 1)),
            objects = listOf(
                ArrowData(3, 2, Direction.LEFT),
                ArrowData(2, 2, Direction.LEFT),
                ArrowData(1, 2, Direction.LEFT)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 1),
                    PathPointData(0, 1),
                    PathPointData(0, 4),
                    PathPointData(3, 4),
                    StopPathPointData(3, 3),
                    PathPointData(4, 3),
                    PathPointData(4, 0),
                    PathPointData(1, 0),
                    PathPointData(1, 1)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, 0, 0,
                0, e, 0,
                0, 0, 0
            ),
            goal = 17,
            playerPositions = listOf(TilePoint(3, 1), TilePoint(0, 1)),
            objects = listOf(
                ArrowData(0, 2, Direction.DOWN),
                ArrowData(0, 0, Direction.DOWN),
                SuperJumpData(1, 1)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 1),
                    StopPathPointData(2, 1)
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
            goal = 16,
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
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, 0, e, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e,
                e, e, e, e, e
            ),
            goal = 8,
            playerPositions = listOf(TilePoint(1, 1), TilePoint(1, 3)),
            objects = listOf(
                SuperJumpData(2, 2),
                ArrowData(2, 3, Direction.LEFT),
                ArrowData(2, 1, Direction.RIGHT),
                ArrowData(1, 2, Direction.DOWN)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 2),
                    PathPointData(0, 4),
                    StopPathPointData(2, 4),
                    PathPointData(4, 4),
                    StopPathPointData(4, 2),
                    PathPointData(4, 0),
                    StopPathPointData(2, 0),
                    PathPointData(0, 0),
                    PathPointData(0, 2)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, e, 0,
                e, 0, e,
                0, 0, 0
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(3, 0), TilePoint(3, 2)),
            objects = listOf(
                ArrowData(2, 1, Direction.UP),
                ArrowData(0, 2, Direction.LEFT),
                ArrowData(0, 0, Direction.RIGHT),
                SuperJumpData(2, 1)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(2, 1),
                    StopPathPointData(2, 2),
                    StopPathPointData(2, 0)
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
            goal = 18,
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
            numRows = 3, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, e, e, 0
            ),
            goal = 18,
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
            numRows = 5, numCols = 4,
            map = intArrayOf(
                e, e, e, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, e, e, e
            ),
            goal = 19,
            playerPositions = listOf(TilePoint(2, 3)),
            objects = listOf(
                ArrowData(2, 2, Direction.RIGHT),
                ArrowData(2, 1, Direction.RIGHT),
                SuperJumpData(1, 1),
                SuperJumpData(3, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 3),
                    StopPathPointData(0, 0)
                ),
                listOf(
                    StopPathPointData(4, 0),
                    StopPathPointData(4, 3)
                )
            )
        ),
        MapData(
            numRows = 5, numCols = 4,
            map = intArrayOf(
                e, 0, 0, e,
                e, 0, 0, e,
                e, 0, 0, e,
                e, 0, 0, 0,
                0, 0, 0, e
            ),
            goal = 17,
            playerPositions = listOf(TilePoint(2, 1)),
            objects = listOf(
                IceData(1, 1),
                IceData(2, 2),
                IceData(3, 1)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(4, 0),
                    StopPathPointData(2, 0),
                    StopPathPointData(0, 0)
                ),
                listOf(
                    StopPathPointData(3, 3),
                    StopPathPointData(1, 3)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, e, 0, 0,
                0, e, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            goal = 16,
            playerPositions = listOf(TilePoint(2, 2), TilePoint(3, 3)),
            objects = listOf(
                ArrowData(3, 2, Direction.RIGHT),
                ArrowData(3, 1, Direction.RIGHT),
                ArrowData(2, 3, Direction.UP),
                ArrowData(2, 1, Direction.LEFT),
                ArrowData(1, 3, Direction.UP),
                ArrowData(1, 2, Direction.DOWN),
                IceData(1, 0)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 0),
                    PathPointData(1, 1),
                    StopPathPointData(0, 1),
                    PathPointData(1, 1)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, e, e,
                e, e, e,
                0, 0, 0
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 2), TilePoint(3, 0)),
            objects = listOf(
                ArrowData(3, 1, Direction.LEFT),
                ArrowData(0, 1, Direction.RIGHT),
                SuperJumpData(1, 0)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 0),
                    StopPathPointData(1, 2),
                    StopPathPointData(2, 2),
                    StopPathPointData(2, 0),
                    PathPointData(1, 0)
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
            goal = 15,
            playerPositions = listOf(TilePoint(3, 0), TilePoint(3, 4)),
            objects = listOf(
                ArrowData(1, 2, Direction.RIGHT),
                ArrowData(3, 2, Direction.RIGHT)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 2),
                    StopPathPointData(2, 2)
                )
            )
        )
    )
}