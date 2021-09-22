package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.drawAlpha
import com.distraction.fs2.tilemap.TileMap

class Ice(context: Context, tileMap: TileMap, row: Int, col: Int) : TileObject(context, tileMap) {

    private val image = context.getImage("ice")
    private var alpha = .85f

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {

    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.drawAlpha(image, isop.x - image.regionWidth / 2, isop.y - image.regionHeight / 2, alpha)
    }
}