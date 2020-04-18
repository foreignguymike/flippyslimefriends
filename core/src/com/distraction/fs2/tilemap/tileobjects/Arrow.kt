package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.TileObject

class Arrow(context: Context, tileMap: TileMap, row: Int, col: Int, val direction: Player.Direction) : TileObject(context, tileMap) {
    private val image = context.assets.getAtlas().findRegion("arrow")

    init {
        setTile(row, col)
    }

    override fun update(dt: Float) {

    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, pp)
        when (direction) {
            Player.Direction.RIGHT -> sb.draw(image, pp.x - image.regionWidth / 2 - 2, pp.y - image.regionHeight / 2 - 2)
            Player.Direction.LEFT -> sb.draw(image, pp.x + image.regionWidth / 2 + 3, pp.y + image.regionHeight / 2 - 3, -image.regionWidth * 1f, -image.regionHeight * 1f)
            Player.Direction.UP -> sb.draw(image, pp.x - image.regionWidth / 2 - 2, pp.y + image.regionHeight / 2 - 3, image.regionWidth * 1f, -image.regionHeight * 1f)
            Player.Direction.DOWN -> sb.draw(image, pp.x + image.regionWidth / 2 + 3, pp.y - image.regionHeight / 2 - 2, -image.regionWidth * 1f, image.regionHeight * 1f)
        }
    }
}