package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.e
import com.distraction.fs2.tilemap.data.GameData.Companion.g

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
            numRows = 4, numCols = 3,
            map = intArrayOf(
                e, 0, 0,
                e, 0, 0,
                0, 0, e,
                0, 0, 0
            ),
            target = 12,
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
            playerPositions = listOf(TilePoint(3, 1)),
            objects = listOf(
                ArrowData(1, 0, Direction.RIGHT),
                ArrowData(1, 1, Direction.RIGHT),
                ArrowData(2, 1, Direction.UP)
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
            playerPositions = listOf(TilePoint(2, 2)),
            objects = listOf(
                ArrowData(0, 2, Direction.RIGHT),
                ArrowData(0, 1, Direction.LEFT),
                ArrowData(3, 2, Direction.RIGHT),
                ArrowData(3, 1, Direction.LEFT)
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
            playerPositions = listOf(TilePoint(3, 0)),
            objects = listOf(
                ArrowData(0, 2, Direction.DOWN),
                ArrowData(1, 2, Direction.DOWN),
                ArrowData(2, 1, Direction.UP),
                ArrowData(3, 1, Direction.UP)
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
            playerPositions = listOf(TilePoint(3, 0)),
            objects = listOf(
                ArrowData(0, 0, Direction.DOWN),
                ArrowData(0, 3, Direction.DOWN),
                ArrowData(1, 0, Direction.RIGHT),
                ArrowData(2, 0, Direction.RIGHT),
                ArrowData(1, 3, Direction.LEFT),
                ArrowData(2, 3, Direction.LEFT)
            )
        ),
        MapData(
            numRows = 5, numCols = 4,
            map = intArrayOf(
                e, 0, 0, e,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                e, 0, 0, e
            ),
            target = 23,
            playerPositions = listOf(TilePoint(3, 0)),
            objects = listOf(
                ArrowData(1, 1, Direction.DOWN),
                ArrowData(3, 1, Direction.DOWN),
                ArrowData(1, 2, Direction.UP),
                ArrowData(3, 2, Direction.UP)
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, e, 0, 0,
                0, 0, 0, 0, 0
            ),
            target = 13,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                SuperJumpData(0, 2),
                SuperJumpData(2, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 3,
            map = intArrayOf(
                0, 0, e,
                0, 0, 0,
                0, 0, 0,
                0, 0, 0,
                e, 0, 0
            ),
            target = 18,
            playerPositions = listOf(TilePoint(2, 1)),
            objects = listOf(
                SuperJumpData(2, 0),
                SuperJumpData(2, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, 0, e, e, e,
                0, 0, e, 0, 0,
                0, 0, e, 0, 0,
                0, 0, e, 0, 0,
                e, e, e, 0, e
            ),
            target = 17,
            playerPositions = listOf(TilePoint(1, 0)),
            objects = listOf(
                SuperJumpData(1, 3),
                SuperJumpData(3, 1)
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
            ), target = 16,
            playerPositions = listOf(TilePoint(4, 1)),
            objects = listOf(
                ArrowData(1, 1, Direction.RIGHT),
                ArrowData(3, 1, Direction.LEFT)
            )
        ),
        MapData(
            numRows = 5, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, e, 0,
                0, 0, 0,
                0, 0, e,
                0, 0, 0
            ),
            target = 18,
            playerPositions = listOf(TilePoint(2, 1)),
            objects = listOf(
                ArrowData(3, 1, Direction.UP),
                SuperJumpData(1, 0)
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                e, 0, 0, 0, e,
                0, 0, g, 0, 0,
                0, 0, g, 0, 0,
                e, 0, 0, 0, e
            ),
            target = 19,
            playerPositions = listOf(TilePoint(1, 0)),
            objects = listOf(
                SuperJumpData(1, 3),
                SuperJumpData(2, 1),
                ArrowData(0, 3, Direction.DOWN),
                ArrowData(3, 1, Direction.UP)
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
            target = 24,
            playerPositions = listOf(TilePoint(4, 2)),
            objects = listOf(
                ArrowData(2, 3, Direction.DOWN),
                ArrowData(2, 1, Direction.UP),
                ArrowData(1, 2, Direction.RIGHT),
                SuperJumpData(2, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 4,
            map = intArrayOf(
                e, 0, 0, 0,
                e, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, e,
                0, 0, 0, e
            ), target = 19,
            playerPositions = listOf(TilePoint(2, 0)),
            objects = listOf(
                ArrowData(1, 1, Direction.UP),
                ArrowData(1, 3, Direction.UP),
                ArrowData(3, 0, Direction.DOWN),
                ArrowData(3, 2, Direction.DOWN)
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                e, 0, 0, 0, e,
                0, 0, 0, 0, 0
            ),
            target = 21,
            playerPositions = listOf(TilePoint(2, 2)),
            objects = listOf(
                ArrowData(3, 4, Direction.UP),
                ArrowData(3, 0, Direction.UP),
                SuperJumpData(3, 0),
                SuperJumpData(3, 4)
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
            ), target = 28,
            playerPositions = listOf(TilePoint(4, 2)),
            objects = listOf(
                SuperJumpData(1, 1),
                SuperJumpData(1, 3),
                SuperJumpData(3, 2)
            )
        ),
        MapData(
            numRows = 4, numCols = 6,
            map = intArrayOf(
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0
            ), target = 27,
            playerPositions = listOf(TilePoint(3, 5)),
            objects = listOf(
                SuperJumpData(0, 4),
                SuperJumpData(1, 3),
                SuperJumpData(2, 2),
                SuperJumpData(3, 1)
            )
        ),
        MapData(
            numRows = 6, numCols = 8,
            map = intArrayOf(
                e, e, e, e, 0, 0, e, e,
                e, e, 0, e, 0, 0, 0, e,
                e, 0, 0, e, 0, 0, 0, 0,
                0, 0, 0, e, 0, 0, 0, e,
                e, 0, 0, e, 0, 0, e, e,
                e, e, 0, e, e, e, e, e
            ), target = 30,
            playerPositions = listOf(TilePoint(5, 2)),
            objects = listOf(
                SuperJumpData(3, 2),
                SuperJumpData(2, 4)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, 0, 0, 0, e,
                0, 0, e, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, e, 0, 0,
                e, 0, 0, 0, e
            ), target = 28,
            playerPositions = listOf(TilePoint(2, 4)),
            objects = listOf(
                SuperJumpData(2, 2),
                ArrowData(0, 2, Direction.RIGHT),
                ArrowData(4, 2, Direction.RIGHT)
            )
        ),
        MapData(
            numRows = 8, numCols = 5,
            map = intArrayOf(
                e, e, e, 0, e,
                e, e, e, 0, 0,
                e, e, e, 0, e,
                e, e, e, 0, e,
                e, 0, 0, 0, e,
                0, 0, 0, 0, e,
                0, 0, 0, 0, e,
                e, 0, 0, e, e
            ),
            target = 23,
            playerPositions = listOf(TilePoint(6, 0)),
            objects = listOf(
                ArrowData(5, 3, Direction.UP),
                ArrowData(4, 3, Direction.DOWN),
                SuperJumpData(4, 3),
                SuperJumpData(5, 3)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, e, 0, 0,
                0, 0, 0, 0, 0,
                0, e, 0, e, 0,
                0, 0, 0, 0, 0,
                0, 0, e, e, e
            ), target = 15,
            playerPositions = listOf(TilePoint(4, 0), TilePoint(0, 4)),
            objects = listOf(
                ArrowData(2, 2, Direction.UP)
            )
        ),
        MapData(
            numRows = 2, numCols = 7,
            map = intArrayOf(
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0
            ),
            target = 16,
            playerPositions = listOf(TilePoint(0, 3), TilePoint(1, 3)),
            objects = listOf(
                ArrowData(1, 5, Direction.RIGHT),
                ArrowData(1, 4, Direction.LEFT),
                ArrowData(0, 2, Direction.RIGHT),
                ArrowData(0, 1, Direction.LEFT)
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                e, 0, 0, 0, e,
                e, 0, 0, 0, 0,
                0, 0, e, 0, e,
                0, 0, 0, 0, 0
            ), target = 15,
            playerPositions = listOf(TilePoint(2, 0), TilePoint(2, 1)),
            objects = listOf(
                ArrowData(3, 2, Direction.LEFT),
                ArrowData(1, 2, Direction.RIGHT)
            )
        ),
        MapData(
            numRows = 3, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            target = 10,
            playerPositions = listOf(TilePoint(0, 3), TilePoint(2, 3)),
            objects = listOf(
                ArrowData(2, 1, Direction.UP),
                ArrowData(1, 0, Direction.UP)
            )
        ),
        MapData(
            numRows = 4, numCols = 6,
            map = intArrayOf(
                e, e, 0, 0, 0, 0,
                e, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, e,
                0, 0, 0, 0, e, e
            ),
            target = 18,
            playerPositions = listOf(TilePoint(3, 0), TilePoint(0, 5)),
            objects = listOf(
                ArrowData(3, 2, Direction.UP),
                ArrowData(2, 3, Direction.UP)
            )
        ),
        MapData(
            numRows = 5, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, e, e, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            target = 25,
            playerPositions = listOf(TilePoint(2, 0)),
            objects = listOf(
                ArrowData(3, 1, Direction.RIGHT),
                ArrowData(1, 2, Direction.LEFT),
                SuperJumpData(1, 1),
                SuperJumpData(1, 2),
                SuperJumpData(3, 1),
                SuperJumpData(3, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                0, 0, 0, e, e,
                0, e, 0, 0, e,
                0, 0, 0, 0, 0,
                e, 0, 0, e, 0,
                e, e, 0, 0, 0
            ),
            target = 26,
            playerPositions = listOf(TilePoint(0, 0)),
            objects = listOf(
                ArrowData(2, 1, Direction.RIGHT),
                ArrowData(1, 2, Direction.DOWN),
                SuperJumpData(2, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 6,
            map = intArrayOf(
                0, 0, 0, e, e, e,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, e, e, e
            ),
            target = 25,
            playerPositions = listOf(TilePoint(0, 2)),
            objects = listOf(
                SuperJumpData(1, 2),
                SuperJumpData(2, 2),
                SuperJumpData(3, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, e, e, e, 0,
                0, 0, 0, 0, 0,
                0, e, e, e, 0,
                0, 0, 0, 0, 0
            ),
            target = 25,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(0, 4)),
            objects = listOf(
                SuperJumpData(0, 2),
                SuperJumpData(2, 2),
                SuperJumpData(4, 2)
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
            playerPositions = listOf(TilePoint(2, 2)),
            objects = listOf(
                ArrowData(3, 2, Direction.RIGHT),
                ArrowData(2, 3, Direction.UP),
                ArrowData(2, 1, Direction.UP),
                ArrowData(1, 2, Direction.RIGHT)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, e, 0, 0,
                0, e, e, e, 0,
                0, 0, e, 0, 0,
                0, 0, 0, 0, 0
            ),
            target = 29,
            playerPositions = listOf(TilePoint(0, 2)),
            objects = listOf(
                SuperJumpData(1, 1),
                SuperJumpData(3, 3)
            )
        ),
        MapData(
            numRows = 6, numCols = 5,
            map = intArrayOf(
                e, e, 0, 0, 0,
                e, e, 0, e, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, e, 0, e, e,
                0, 0, 0, e, e
            ),
            target = 27,
            playerPositions = listOf(TilePoint(0, 2)),
            objects = listOf(
                ArrowData(4, 2, Direction.UP),
                ArrowData(2, 3, Direction.DOWN),
                ArrowData(2, 1, Direction.RIGHT)
            )
        ),
        MapData(
            numRows = 7, numCols = 5,
            map = intArrayOf(
                e, e, 0, e, e,
                e, 0, 0, 0, e,
                0, 0, 0, 0, 0,
                0, e, 0, e, 0,
                0, 0, 0, 0, 0,
                e, 0, 0, 0, e,
                e, e, 0, e, e
            ),
            target = 21,
            playerPositions = listOf(TilePoint(0, 2), TilePoint(6, 2)),
            objects = listOf(
                ArrowData(4, 1, Direction.RIGHT),
                ArrowData(2, 3, Direction.LEFT),
                SuperJumpData(3, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 6,
            map = intArrayOf(
                e, e, 0, 0, 0, e,
                e, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, e
            ),
            target = 28,
            playerPositions = listOf(TilePoint(4, 0)),
            objects = listOf(
                ArrowData(4, 4, Direction.UP),
                ArrowData(0, 4, Direction.DOWN),
                SuperJumpData(2, 2),
                SuperJumpData(3, 3)
            )
        ),
        MapData(
            numRows = 6, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, e, 0, e, 0,
                0, e, 0, 0, 0,
                0, e, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
            ),
            target = 37,
            playerPositions = listOf(TilePoint(5, 1)),
            objects = listOf(
                ArrowData(5, 2, Direction.LEFT),
                ArrowData(4, 2, Direction.RIGHT),
                ArrowData(2, 3, Direction.DOWN),
                SuperJumpData(3, 3)
            )
        ),
        MapData(
            numRows = 7, numCols = 7,
            map = intArrayOf(
                e, e, 0, 0, 0, e, e,
                e, 0, 0, 0, 0, e, e,
                0, 0, 0, 0, 0, e, e,
                e, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, e,
                e, e, 0, 0, 0, 0, 0,
                e, e, e, 0, e, e, e
            ),
            target = 33,
            playerPositions = listOf(TilePoint(6, 3)),
            objects = listOf(
                ArrowData(5, 6, Direction.UP),
                ArrowData(4, 0, Direction.RIGHT),
                ArrowData(3, 6, Direction.LEFT),
                ArrowData(2, 3, Direction.DOWN),
                ArrowData(2, 0, Direction.DOWN),
                SuperJumpData(2, 0),
                SuperJumpData(5, 6)
            )
        )
    )

}