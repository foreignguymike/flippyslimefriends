package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.data.GameColor

/**
 * Visual effects for Teleports.
 * This is going in a separate class for rendering purposes.
 * All instances of this are added to TileMap.otherObjects for last rendering.
 */
class TeleportLight(context: Context, tileMap: TileMap, row: Int, col: Int) :
    TileObject(context, tileMap) {
    private val image = context.getImage("teleport")
    private val pixel = context.getImage("pixel")
    private val color = GameColor.BRIGHT_SKY_BLUE
    private val particles = arrayListOf<Vector3>()

    private val interval = 0.1f
    private var time = interval

    init {
        setPositionFromTile(row, col)
        p.z = 8f
        height = 32f
        speed = 40f
    }

    override fun update(dt: Float) {
        time += dt
        while (time >= interval) {
            time -= interval
            val v = Vector3(p.x, p.y, 0f)
            tileMap.toIsometric(v.x, v.y, v)
            v.x += MathUtils.round(MathUtils.random() * (image.regionWidth - 1) - image.regionWidth / 2)
            v.y += MathUtils.random() * 5
            v.z = v.y + height
            particles.add(v)
        }
        particles.forEach {
            it.y += speed * dt
        }
        particles.removeAll {
            it.y > it.z
        }
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.draw(image, isop.x - image.regionWidth / 2, isop.y - image.regionHeight / 2 + p.z)
        val c = sb.color
        sb.color = color
        particles.forEach {
            color.a = (it.z - it.y) / height
            sb.color = color
            sb.draw(pixel, it.x, it.y, 2f, 2f)
        }
        sb.color = c
    }
}