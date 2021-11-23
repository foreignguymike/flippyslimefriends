package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils

class BreathingImage(
    image: TextureRegion,
    x: Float,
    y: Float,
    padding: Float = 0f,
    private val interval: Float = 1f,
    private val offset: Float = 0.1f
) : ImageButton(image, x, y, padding) {

    private var time = 0f

    override fun update(dt: Float) {
        super.update(dt)
        time += dt
        scale = 1 + offset * MathUtils.sin(MathUtils.PI2 * time / interval)
    }

}