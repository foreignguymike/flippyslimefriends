package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.Direction
import com.distraction.fs2.tilemap.TileMap

class Arrow(context: Context, tileMap: TileMap, row: Int, col: Int, val direction: Direction) : TileObject(context, tileMap) {
    private val image = context.assets.getAtlas().findRegion("arrow")

    init {
        setPositionFromTile(row, col)
        isoHeight = image.regionHeight.toFloat()
    }

    override fun update(dt: Float) {

    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        tileMap.toIsometric(p.x, p.y, isop)
        when (direction) {
            Direction.RIGHT -> sb.draw(image, isop.x - image.regionWidth / 2, isop.y - image.regionHeight / 2 + tileHeight3d)
            Direction.DOWN -> sb.drawHFlip(image, isop.x + image.regionWidth / 2, isop.y - image.regionHeight / 2 + tileHeight3d)
            Direction.UP -> sb.drawVFlip(image, isop.x - image.regionWidth / 2, isop.y + image.regionHeight / 2 + tileHeight3d + 1)
            Direction.LEFT -> sb.drawVHFlip(image, isop.x + image.regionWidth / 2, isop.y + image.regionHeight / 2 + tileHeight3d + 1)
        }
    }
}