package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.tilemap.TileMap

/**
 * Visual effects for Teleports.
 * This is going in a separate class for rendering purposes.
 * All instances of this are added to TileMap.otherObjects for last rendering.
 */
class TeleportLight(context: Context, tileMap: TileMap, row: Int, col: Int) :
    TileObject(context, tileMap) {
    private val image = context.getImage("teleport")
    private val dot = context.getImage("dot")
    private val color = Color.valueOf("AAE2FF30")
    private val particles = arrayListOf<Vector3>()
    override var speed = 40f

    private val interval = 0.1f
    private var time = interval

    init {
        setPositionFromTile(row, col)
        p.z = 8f
        height = 32f
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
            sb.draw(dot, it.x, it.y, 2f, 2f)
        }
        sb.color = c
    }
}

/**
 * Teleport tile object
 */
class Teleport(
    context: Context,
    tileMap: TileMap,
    row: Int,
    col: Int,
    val row2: Int,
    val col2: Int
) : TileObject(context, tileMap) {

    var first = true

    init {
        setPositionFromTile(row, col)
    }

    override fun update(dt: Float) {
        if (first) {
            first = false
            // adding TeleportLights to TileMap.otherObjects for rendering purposes
            tileMap.otherObjects.add(
                TeleportLight(context, tileMap, row, col).apply {
                    currentTile = this@Teleport.currentTile
                })
        }
    }

    override fun render(sb: SpriteBatch) {

    }
}