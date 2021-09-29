package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.resetColor
import com.distraction.fs2.tilemap.TileMap

/**
 * Visual effects for SuperJumps.
 * This is going in a separate class for rendering purposes.
 * All instances of this are added to TileMap.otherObjects for last rendering.
 */
class SuperJumpLight(context: Context, tileMap: TileMap, row: Int, col: Int) : TileObject(context, tileMap) {
    private val image = context.getImage("superjump")
    private val duration = 1.0f
    private var time = 0f
    override var speed = 15f

    init {
        setPositionFromTile(row, col)
        p.z = -2f
    }

    override fun update(dt: Float) {
        time += dt
        p.z += speed * dt
        if (time > duration) {
            remove = true
        }
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        if (duration - time < 0.5f) {
            val c = sb.color
            sb.setColor(c.r, c.g, c.b, (duration - time) / 0.5f)
        }
        sb.draw(image, isop.x - image.regionWidth / 2, isop.y - image.regionHeight / 2 + p.z)
        sb.resetColor()
    }
}

/**
 * SuperJump tile object
 */
class SuperJump(context: Context, tileMap: TileMap, row: Int, col: Int) : TileObject(context, tileMap) {
    private var time = INTERVAL

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
        time += dt
        while (time > INTERVAL) {
            time -= INTERVAL
            // adding SuperJumpLights to TileMap.otherObjects for rendering purposes
            tileMap.otherObjects.add(
                    SuperJumpLight(context, tileMap, row, col).apply {
                        currentTile = this@SuperJump.currentTile
                    })
        }
    }

    override fun render(sb: SpriteBatch) {

    }

    companion object {
        const val INTERVAL = 0.6f
    }
}