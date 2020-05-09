package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.log
import com.distraction.fs2.tilemap.TileMap

class TeleportLight(context: Context, tileMap: TileMap, row: Int, col: Int) : TileObject(context, tileMap) {
    private val image = context.assets.getAtlas().findRegion("teleport")
    private val dot = context.assets.getAtlas().findRegion("dot")
    private val color = Color.valueOf("AAE2FF30")
    private val particles = arrayListOf<Vector3>()
    private val speed = 32f

    private val interval = 0.1f
    private var time = interval

    init {
        setPositionFromTile(row, col)
        p.z = 8f
        height = 16f
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
        sb.draw(image, isop.x - image.regionWidth / 2, isop.y - image.regionHeight / 2 + p.z + tileHeight3d)
        val c = sb.color
        sb.color = color
        particles.forEach {
            color.a = (it.z - it.y) / height
            sb.color = color
            sb.draw(dot, it.x, it.y, 1f, 2f)
        }
        sb.color = c
    }
}

class Teleport(context: Context, tileMap: TileMap, row: Int, col: Int, val row2: Int, val col2: Int) : TileObject(context, tileMap) {
    private val p2 = Vector3()

    init {
        setPositionFromTile(row, col)
        tileMap.toPosition(row2, col2, p2)
        tileMap.otherObjects.add(TeleportLight(context, tileMap, row, col))
    }

    override fun update(dt: Float) {

    }

    override fun render(sb: SpriteBatch) {

    }
}