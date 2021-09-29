package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.tilemap.data.GameColor

class Background(val context: Context, private var color: GameColor = GameColor.SKY_BLUE) {

    private val dot = context.getImage("dot")
    private val image = context.getImage("bgs")

    private val bgs = arrayListOf<Vector3>()
    private val speed = 5f
    private val interval = 5f * 2
    private var time = interval
    private var time2 = 0f
    private var rot = 0f

    init {
        for (row in 0..5) {
            for (col in -4..4) {
                bgs.add(
                    Vector3(
                        1f * col * Constants.WIDTH / 4 + (row + 1) * speed * interval,
                        row * speed * interval,
                        0f
                    )
                )
            }
        }
    }

    fun update(dt: Float) {
        time += dt
        time2 += dt
        while (time > interval) {
            time -= interval
            for (i in -4..4) {
                bgs.add(Vector3(1f * i * Constants.WIDTH / 4, -speed * interval, 0f))
            }
        }
        rot = MathUtils.sin(time2)
        bgs.forEach {
            it.x += speed * dt
            it.y += speed * dt
            it.z = rot
        }
        bgs.removeAll {
            it.x > Constants.WIDTH && it.y > Constants.HEIGHT
        }
    }

    fun render(sb: SpriteBatch) {
        sb.setColor(color)
        sb.draw(dot, 0f, 0f, Constants.WIDTH, Constants.HEIGHT)
        sb.resetColor()
        bgs.forEach {
            sb.draw(
                image,
                it.x - rot * image.regionWidth / 2,
                it.y,
                rot * image.regionWidth,
                1f * image.regionHeight
            )
        }
    }

}