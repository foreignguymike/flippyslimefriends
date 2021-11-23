package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.drawCentered
import com.distraction.fs2.tilemap.TileMap

class Bubble(context: Context, tileMap: TileMap, row: Int, col: Int) :
    TileObject(context, tileMap) {

    private val bubble = context.getImage("bubble")

    private var resetTime = 0f
    var resetting = false

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
        if (resetting) {
            resetTime += dt
            if (resetTime >= RESET_INTERVAL) {
                resetting = false
                resetTime = 0f
            }
        }
    }

    override fun render(sb: SpriteBatch) {
        if (!resetting) {
            tileMap.toIsometric(p.x, p.y, isop)
            sb.drawCentered(bubble, isop.x, isop.y)
        }
    }

    companion object {
        const val RESET_INTERVAL = 1f
    }

}