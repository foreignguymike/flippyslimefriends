package com.distraction.fs2.tilemap.data

import com.distraction.fs2.tilemap.data.GameData.Companion.b
import com.distraction.fs2.tilemap.data.GameData.Companion.e
import com.distraction.fs2.tilemap.data.GameData.Companion.g

object UnderseaData {

    val data = listOf(
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                e, e, e,
                0, 0, 0
            ),
            goal = 5,
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
            goal = 12,
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
                e, e, 0,
                e, 0, 0,
                0, 0, 0,
                0, 0, e,
                0, e, e
            ),
            goal = 9,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 0, Direction.UP),
                ArrowData(2, 1, Direction.UP),
                ArrowData(1, 2, Direction.UP),
                BubbleData(0, 2),
                BubbleData(1, 1),
                BubbleData(2, 0)
            )
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
            goal = 13,
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
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0)),
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
            goal = 16,
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
            goal = 16,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.DOWN),
                ArrowData(2, 0, Direction.DOWN),
                ArrowData(1, 2, Direction.UP),
                ArrowData(1, 0, Direction.UP)
            )
        ),
        MapData(
            numRows = 5, numCols = 4,
            map = intArrayOf(
                e, 0, 0, e,
                0, 0, 0, e,
                0, 0, e, 0,
                e, 0, 0, 0,
                e, e, 0, 0
            ),
            goal = 12,
            playerPositions = listOf(TilePoint(2, 1)),
            objects = listOf(
                BubbleData(1, 2)
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
            goal = 21,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.UP),
                ArrowData(2, 0, Direction.UP),
                SuperJumpData(2, 1)
            )
        ),
        MapData(
            numRows = 3, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, 0, 0,
                0, 0, 0
            ),
            goal = 9,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 1, Direction.RIGHT),
                ArrowData(1, 1, Direction.RIGHT),
                ArrowData(0, 1, Direction.RIGHT),
                BubbleData(0, 2)
            )
        ),
        MapData(
            numRows = 4, numCols = 3,
            map = intArrayOf(
                0, 0, 0,
                0, e, e,
                0, 0, 0,
                0, 0, 0
            ),
            goal = 12,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.UP),
                ArrowData(2, 0, Direction.UP),
                BubbleData(0, 1)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 0),
                    StopPathPointData(1, 2)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 3,
            map = intArrayOf(
                0, e, e,
                0, 0, 0,
                0, 0, 0,
                0, e, e
            ),
            goal = 8,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 0, Direction.UP),
                ArrowData(0, 0, Direction.DOWN),
                SuperJumpData(0, 0),
                SuperJumpData(3, 0)
            )
        ),
        MapData(
            numRows = 3, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            goal = 12,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                BubbleData(0, 1),
                SuperJumpData(0, 2),
                SuperJumpData(1, 1),
                BubbleData(2, 1),
                SuperJumpData(2, 2)
            )
        ),
        MapData(
            numRows = 4, numCols = 3,
            map = intArrayOf(
                0, 0, e,
                0, 0, e,
                0, 0, e,
                0, 0, 0
            ),
            goal = 9,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                SuperJumpData(1, 0),
                SuperJumpData(1, 1),
                SuperJumpData(2, 0),
                SuperJumpData(2, 1),
                BubbleData(3, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(3, 2),
                    StopPathPointData(0, 2)
                )
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                0, 0, 0, e, e,
                0, 0, 0, 0, 0,
                e, e, 0, 0, 0
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 3, Direction.RIGHT),
                ArrowData(1, 3, Direction.RIGHT),
                ArrowData(1, 1, Direction.LEFT),
                ArrowData(0, 1, Direction.LEFT),
                BubbleData(0, 0),
                BubbleData(2, 4)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                e, 0, 0, e,
                0, 0, 0, 0
            ),
            goal = 14,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 3, Direction.LEFT),
                ArrowData(3, 0, Direction.RIGHT),
                ArrowData(1, 2, Direction.UP),
                ArrowData(1, 1, Direction.DOWN),
                SuperJumpData(1, 0),
                SuperJumpData(1, 3)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, e, e, e,
                0, 0, 0, 0,
                0, 0, 0, 0,
                e, e, e, 0
            ),
            goal = 10,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                IceData(1, 1),
                IceData(1, 2),
                IceData(2, 1),
                IceData(2, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 0),
                    StopPathPointData(0, 3)
                ),
                listOf(
                    StopPathPointData(3, 3),
                    StopPathPointData(3, 0)
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
            goal = 12,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(1, 2, Direction.RIGHT),
                ArrowData(1, 1, Direction.RIGHT),
                SuperJumpData(0, 1),
                SuperJumpData(2, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 4,
            map = intArrayOf(
                e, 0, e, e,
                0, 0, e, e,
                0, 0, 0, e,
                0, 0, 0, 0,
                e, 0, 0, e
            ),
            goal = 12,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                IceData(2, 1),
                IceData(2, 2),
                IceData(3, 1),
                IceData(3, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(0, 1),
                    PathPointData(0, 2),
                    StopPathPointData(1, 2),
                    PathPointData(1, 3),
                    StopPathPointData(2, 3),
                    PathPointData(0, 3),
                    PathPointData(0, 1)
                )
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
            goal = 16,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.RIGHT),
                ArrowData(2, 1, Direction.LEFT),
                ArrowData(1, 2, Direction.RIGHT),
                ArrowData(1, 1, Direction.LEFT)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                e, 0, 0, e,
                0, 0, 0, 0,
                0, 0, 0, 0,
                e, 0, 0, e
            ),
            goal = 18,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.UP),
                ArrowData(1, 1, Direction.DOWN),
                SuperJumpData(1, 1),
                SuperJumpData(2, 2)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, e,
                0, 0, 0, 0,
                0, 0, 0, 0,
                e, 0, 0, e
            ),
            goal = 17,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 2, Direction.UP),
                ArrowData(2, 3, Direction.LEFT),
                SuperJumpData(1, 1)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                e, 0, 0, 0,
                0, 0, 0, 0,
                0, b, 0, 0,
                0, 0, 0, e
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                IceData(1, 1),
                IceData(1, 2),
                IceData(2, 2)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                e, e, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, e
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                SuperJumpData(1, 2),
                SuperJumpData(2, 1),
                IceData(2, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(1, 0),
                    PathPointData(0, 0),
                    StopPathPointData(0, 1),
                    PathPointData(0, 0)
                )
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
            goal = 16,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 3, Direction.UP),
                ArrowData(1, 3, Direction.UP),
                ArrowData(0, 2, Direction.RIGHT),
                ArrowData(0, 1, Direction.RIGHT),
                BubbleData(0, 3),
                IceData(1, 2),
                IceData(2, 1)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, e,
                0, 0, 0, e,
                0, 0, 0, 0,
                e, 0, 0, 0
            ),
            goal = 15,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                IceData(1, 2),
                IceData(2, 1),
                IceData(2, 2)
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                e, 0, 0, 0, e,
                0, 0, g, 0, 0,
                e, 0, 0, 0, e
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.LEFT),
                ArrowData(1, 1, Direction.UP),
                ArrowData(0, 2, Direction.RIGHT),
                BubbleData(1, 2)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                e, 0, 0, e,
                e, 0, 0, 0,
                0, 0, 0, e,
                e, 0, 0, e
            ),
            goal = 12,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                IceData(1, 2),
                IceData(2, 1)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(2, 0),
                    StopPathPointData(1, 0)
                ),
                listOf(
                    StopPathPointData(1, 3),
                    StopPathPointData(2, 3)
                )
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                e, 0, 0, 0, e,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 3, Direction.RIGHT),
                ArrowData(2, 1, Direction.RIGHT),
                ArrowData(1, 3, Direction.LEFT),
                ArrowData(1, 1, Direction.LEFT),
                SuperJumpData(1, 2),
                SuperJumpData(2, 2)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                e, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, e
            ),
            goal = 14,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 1, Direction.RIGHT),
                ArrowData(2, 2, Direction.RIGHT),
                ArrowData(1, 1, Direction.LEFT),
                ArrowData(0, 2, Direction.LEFT),
                IceData(1, 0),
                IceData(1, 3),
                IceData(2, 0),
                IceData(2, 3)
            ),
            path = listOf(
                listOf(StopPathPointData(0, 1), StopPathPointData(0, 0)),
                listOf(StopPathPointData(3, 2), StopPathPointData(3, 3))
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                e, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                e, 0, 0, 0
            ),
            goal = 14,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                IceData(1, 1),
                SuperJumpData(1, 2),
                IceData(1, 3),
                IceData(2, 1),
                SuperJumpData(2, 2),
                IceData(2, 3)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, 0, 0, e, e,
                0, 0, 0, 0, e,
                0, 0, e, 0, 0,
                0, 0, 0, 0, e,
                e, 0, 0, e, e
            ),
            goal = 16,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 2, Direction.RIGHT),
                ArrowData(3, 1, Direction.RIGHT),
                ArrowData(1, 2, Direction.RIGHT),
                ArrowData(1, 1, Direction.RIGHT),
                BubbleData(2, 4)
            )
        ),
        MapData(
            numRows = 4, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                e, 0, 0, 0, e,
                e, 0, 0, 0, e
            ),
            goal = 16,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 3, Direction.DOWN),
                ArrowData(2, 1, Direction.DOWN),
                ArrowData(1, 3, Direction.DOWN),
                ArrowData(1, 1, Direction.DOWN),
                IceData(0, 1),
                IceData(0, 3),
                SuperJumpData(1, 2),
                SuperJumpData(2, 2),
                IceData(3, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, e, 0, e,
                e, 0, 0, 0, 0,
                e, 0, 0, 0, e,
                0, 0, 0, 0, e,
                e, 0, e, e, e
            ),
            goal = 15,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 2, Direction.UP),
                ArrowData(2, 3, Direction.LEFT),
                ArrowData(2, 1, Direction.DOWN),
                ArrowData(1, 2, Direction.RIGHT),
                IceData(2, 2)
            ),
            path = listOf(
                listOf(StopPathPointData(3, 0), StopPathPointData(1, 0)),
                listOf(StopPathPointData(0, 3), StopPathPointData(0, 1)),
                listOf(StopPathPointData(4, 1), StopPathPointData(4, 3)),
                listOf(StopPathPointData(1, 4), StopPathPointData(3, 4))
            )
        ),
        MapData(
            numRows = 5, numCols = 3,
            map = intArrayOf(
                e, 0, 0,
                0, 0, 0,
                0, 0, 0,
                0, 0, 0,
                0, 0, e
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 1, Direction.LEFT),
                ArrowData(2, 1, Direction.RIGHT),
                ArrowData(1, 1, Direction.LEFT)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, 0, e, e,
                e, 0, 0, 0, e,
                0, 0, g, 0, 0,
                e, 0, 0, 0, e,
                e, e, 0, e, e
            ),
            goal = 19,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 3, Direction.DOWN),
                ArrowData(2, 1, Direction.DOWN),
                ArrowData(1, 2, Direction.RIGHT),
                ArrowData(3, 2, Direction.RIGHT),
                BubbleData(2, 2)
            )
        ),
        MapData(
            numRows = 5, numCols = 3,
            map = intArrayOf(
                e, 0, 0,
                0, 0, 0,
                0, e, 0,
                0, e, 0,
                e, 0, 0
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 0, Direction.UP),
                ArrowData(1, 0, Direction.DOWN),
                IceData(1, 2),
                SuperJumpData(2, 2),
                IceData(3, 2)
            ),
            path = listOf(
                listOf(StopPathPointData(1, 1), StopPathPointData(3, 1))
            )
        ),
        MapData(
            numRows = 3, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            goal = 12,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.LEFT),
                ArrowData(2, 1, Direction.LEFT),
                ArrowData(1, 2, Direction.LEFT),
                ArrowData(1, 1, Direction.LEFT),
                ArrowData(0, 2, Direction.LEFT),
                ArrowData(0, 1, Direction.LEFT)
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, e,
                0, 0, 0, 0, 0,
                e, 0, 0, 0, 0
            ),
            goal = 13,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 2, Direction.RIGHT),
                ArrowData(2, 1, Direction.UP),
                ArrowData(0, 3, Direction.DOWN),
                ArrowData(0, 2, Direction.LEFT),
                IceData(1, 1),
                IceData(1, 2),
                IceData(1, 3)
            )
        ),
        MapData(
            numRows = 5, numCols = 4,
            map = intArrayOf(
                e, 0, 0, e,
                e, 0, 0, e,
                e, 0, 0, e,
                0, 0, 0, 0,
                e, 0, 0, e
            ),
            goal = 14,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 2, Direction.UP),
                ArrowData(3, 1, Direction.UP),
                ArrowData(1, 2, Direction.DOWN),
                ArrowData(1, 1, Direction.DOWN),
                IceData(2, 1),
                IceData(2, 2)
            ),
            path = listOf(
                listOf(
                    StopPathPointData(3, 0),
                    StopPathPointData(2, 0),
                    StopPathPointData(1, 0),
                    StopPathPointData(2, 0)
                ),
                listOf(
                    StopPathPointData(3, 3),
                    StopPathPointData(2, 3),
                    StopPathPointData(1, 3),
                    StopPathPointData(2, 3)
                )
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, e, 0,
                0, 0, 0, 0
            ),
            goal = 15,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 2, Direction.RIGHT),
                ArrowData(2, 3, Direction.UP),
                ArrowData(2, 1, Direction.RIGHT),
                ArrowData(1, 2, Direction.LEFT),
                ArrowData(1, 0, Direction.DOWN),
                ArrowData(0, 1, Direction.RIGHT),
                IceData(1, 1),
                SuperJumpData(1, 2),
                SuperJumpData(2, 1)
            )
        ),
        MapData(
            numRows = 4, numCols = 4,
            map = intArrayOf(
                0, 0, 0, e,
                0, 0, 0, e,
                0, 0, 0, 0,
                0, 0, 0, 0
            ),
            goal = 16,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 1, Direction.UP),
                ArrowData(2, 0, Direction.RIGHT),
                ArrowData(1, 0, Direction.UP),
                ArrowData(0, 2, Direction.LEFT),
                IceData(1, 1),
                SuperJumpData(3, 1)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                0, e, e, e, e,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                e, e, e, e, 0
            ),
            goal = 17,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 3, Direction.RIGHT),
                ArrowData(3, 0, Direction.UP),
                ArrowData(2, 2, Direction.RIGHT),
                ArrowData(1, 4, Direction.DOWN),
                ArrowData(1, 1, Direction.RIGHT),
                IceData(2, 1),
                IceData(2, 3)
            ),
            path = listOf(
                listOf(StopPathPointData(0, 0), StopPathPointData(0, 4)),
                listOf(StopPathPointData(4, 4), StopPathPointData(4, 0))
            )
        ),
        MapData(
            numRows = 3, numCols = 5,
            map = intArrayOf(
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
            ),
            goal = 19,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(2, 3, Direction.UP),
                ArrowData(2, 1, Direction.UP),
                ArrowData(0, 3, Direction.DOWN),
                ArrowData(0, 1, Direction.DOWN),
                IceData(1, 1),
                IceData(1, 2),
                IceData(1, 3)
            )
        ),
        MapData(
            numRows = 5, numCols = 5,
            map = intArrayOf(
                e, e, e, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, e, e, e
            ),
            goal = 21,
            playerPositions = listOf(TilePoint(0, 0), TilePoint(1, 0)),
            startBubble = true,
            objects = listOf(
                ArrowData(3, 3, Direction.LEFT),
                ArrowData(3, 1, Direction.RIGHT),
                ArrowData(3, 0, Direction.DOWN),
                ArrowData(2, 4, Direction.UP),
                ArrowData(2, 3, Direction.LEFT),
                ArrowData(2, 1, Direction.RIGHT),
                ArrowData(2, 0, Direction.DOWN),
                ArrowData(1, 4, Direction.UP),
                ArrowData(1, 3, Direction.LEFT),
                ArrowData(1, 1, Direction.RIGHT),
                IceData(2, 2)
            )
        )
    )
}