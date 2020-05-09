package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.tilemap.TileMap

class Arrow(context: Context, tileMap: TileMap, row: Int, col: Int, val direction: Player.Direction) : TileObject(context, tileMap) {
    private val image = context.assets.getAtlas().findRegion("arrow")

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {

    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        when (direction) {
            Player.Direction.RIGHT -> sb.draw(image, isop.x - image.regionWidth / 2 - 2, isop.y - image.regionHeight / 2 - 2)
            Player.Direction.LEFT -> sb.draw(image, isop.x + image.regionWidth / 2 + 3, isop.y + image.regionHeight / 2 - 3, -image.regionWidth * 1f, -image.regionHeight * 1f)
            Player.Direction.UP -> sb.draw(image, isop.x - image.regionWidth / 2 - 2, isop.y + image.regionHeight / 2 - 3, image.regionWidth * 1f, -image.regionHeight * 1f)
            Player.Direction.DOWN -> sb.draw(image, isop.x + image.regionWidth / 2 + 3, isop.y - image.regionHeight / 2 - 2, -image.regionWidth * 1f, image.regionHeight * 1f)
        }
    }
}