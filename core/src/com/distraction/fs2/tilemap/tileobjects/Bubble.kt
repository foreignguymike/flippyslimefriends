package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.BreathingImage
import com.distraction.fs2.Context
import com.distraction.fs2.drawCentered
import com.distraction.fs2.tilemap.TileMap
import kotlin.math.max

class Bubble(context: Context, tileMap: TileMap, row: Int, col: Int) :
    TileObject(context, tileMap) {

    private val bubbleMaker = context.getImage("bubblemaker")
    val bubbleBase = BubbleBase(context, tileMap, row, col)

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.drawCentered(bubbleMaker, isop.x, isop.y)
    }

}

class BubbleBase(context: Context, tileMap: TileMap, row: Int, col: Int) :
    TileObject(context, tileMap) {
    private val bubble = BreathingImage(context.getImage("bubbleidle")).apply { scale = 0f }

    var resetTime = 0f
    var resetting = false
        set(value) {
            field = value
            if (value) {
                resetTime = 0f
            }
        }

    init {
        setPositionFromTile(row, col)
        resetTime = RESET_INTERVAL
        resetting = false
    }

    override fun update(dt: Float) {
        bubble.update(dt)
        if (resetting) {
            resetTime += dt
            if (resetTime >= RESET_INTERVAL) {
                resetting = false
                resetTime = 0f
            } else {
                bubble.scale = max(0f, (resetTime - BLOW_UP_TIME) / (RESET_INTERVAL - BLOW_UP_TIME))
            }
        }
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        bubble.setPosition(isop.x, isop.y + 15)
        bubble.render(sb)
    }

    companion object {
        const val RESET_INTERVAL = 2f
        const val BLOW_UP_TIME = 1f
    }
}