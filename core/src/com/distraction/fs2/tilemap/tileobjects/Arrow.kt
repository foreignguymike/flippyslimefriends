package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.Direction
import com.distraction.fs2.tilemap.TileMap

class Arrow(context: Context, tileMap: TileMap, row: Int, col: Int, val direction: Direction) : TileObject(context, tileMap) {

    private val animation = Animation(arrayOf(
            context.getImage("arrow", 1),
            context.getImage("arrow", 2)),
            0.5f)

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
        animation.update(dt)
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        val image = animation.getImage()
        when (direction) {
            Direction.RIGHT -> sb.draw(image, isop.x - image.regionWidth / 2, isop.y - image.regionHeight / 2)
            Direction.DOWN -> sb.drawHFlip(image, isop.x + image.regionWidth / 2, isop.y - image.regionHeight / 2)
            Direction.UP -> sb.drawVFlip(image, isop.x - image.regionWidth / 2, isop.y + image.regionHeight / 2 + 1)
            Direction.LEFT -> sb.drawVHFlip(image, isop.x + image.regionWidth / 2, isop.y + image.regionHeight / 2 + 1)
        }
    }
}