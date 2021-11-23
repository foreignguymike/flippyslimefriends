package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.BreathingImage
import com.distraction.fs2.Context
import com.distraction.fs2.drawCentered
import com.distraction.fs2.tilemap.TileMap

class Bubble(context: Context, tileMap: TileMap, row: Int, col: Int) :
    TileObject(context, tileMap) {

    private val bubble = BreathingImage(context.getImage("bubbleidle"))
    private val bubblemaker = context.getImage("bubblemaker")

    private var resetTime = 0f
    var resetting = false

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
        bubble.update(dt)
        if (resetting) {
            resetTime += dt
            if (resetTime >= RESET_INTERVAL) {
                resetting = false
                resetTime = 0f
            } else {
                bubble.scale = (resetTime - BLOW_UP_TIME) / (RESET_INTERVAL - BLOW_UP_TIME)
            }
        }
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.drawCentered(bubblemaker, isop.x, isop.y)
        if (!resetting || resetTime > BLOW_UP_TIME) {
            bubble.setPosition(isop.x, isop.y + 10)
            bubble.render(sb)
        }
    }

    companion object {
        const val RESET_INTERVAL = 2f
        const val BLOW_UP_TIME = 1f
    }

}