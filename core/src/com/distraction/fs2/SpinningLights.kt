package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SpinningLights(
    context: Context,
    x: Float,
    y: Float,
    spokes: Int,
    scale: Float = 1f,
    private val rotationSpeedDeg: Float = -40f
) :
    ImageButton(context.getImage("pixel"), x, y) {

    private val spotlight = context.getImage("spotlight")
    private val degrees = MutableList(spokes) { it * 360f / spokes }

    init {
        pos.y = y + spotlight.regionHeight / 2f
        this.scale = scale
    }

    override fun update(dt: Float) {
        degrees.mapInPlace { it + rotationSpeedDeg * dt }
    }

    override fun render(sb: SpriteBatch) {
        degrees.forEach {
            sb.drawRotated(spotlight, pos.x, pos.y, it, spotlight.regionWidth / 2f, 0f, scale)
        }
    }

}