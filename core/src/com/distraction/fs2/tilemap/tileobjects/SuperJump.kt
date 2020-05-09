package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.tilemap.TileMap

class SuperJumpLight(context: Context, tileMap: TileMap, row: Int, col: Int) : TileObject(context, tileMap) {
    private val image = context.assets.getAtlas().findRegion("superjump")
    private val speed = 10f
    private val duration = 1f
    private var time = 0f

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
        sb.draw(image, isop.x - image.regionWidth / 2, isop.y - image.regionHeight / 2 + p.z)
    }
}

class SuperJump(context: Context, tileMap: TileMap, row: Int, col: Int) : TileObject(context, tileMap) {
    private val interval = 0.4f
    private var time = interval

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
        time += dt
        while (time > interval) {
            time -= interval
            tileMap.otherObjects.add(SuperJumpLight(context, tileMap, row, col))
        }
    }

    override fun render(sb: SpriteBatch) {

    }
}