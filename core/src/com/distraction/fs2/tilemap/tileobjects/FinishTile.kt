package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.drawCentered
import com.distraction.fs2.tilemap.TileMap

class FinishTile(context: Context, tileMap: TileMap, row: Int, col: Int) :
    TileObject(context, tileMap) {

    private val on = context.getImage("finishtileon")
    private val off = context.getImage("finishtileoff")

    var active = false

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {

    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.drawCentered(if (active) on else off, isop.x, isop.y)
    }

}