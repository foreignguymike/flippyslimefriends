package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.tilemap.TileMap

/**
 * SuperJump tile object
 */
class SuperJump(context: Context, tileMap: TileMap, row: Int, col: Int) :
    TileObject(context, tileMap) {
    private var time = INTERVAL

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
        time += dt
        while (time > INTERVAL) {
            time -= INTERVAL
            // adding SuperJumpLights to TileMap.otherObjects for rendering purposes
            currentTile?.addTopObject(
                SuperJumpLight(context, tileMap, row, col).apply {
                    currentTile = this@SuperJump.currentTile
                    p.x = this@SuperJump.p.x
                    p.y = this@SuperJump.p.y
                })
        }
    }

    override fun render(sb: SpriteBatch) {

    }

    companion object {
        const val INTERVAL = 0.6f
    }
}