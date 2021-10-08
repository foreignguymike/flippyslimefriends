package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils

class BreathingImage(
    image: TextureRegion,
    x: Float,
    y: Float,
    private val interval: Float = 1f,
    private val offset: Float = 0.1f
) : ImageButton(image, x, y) {

    private var time = 0f

    override fun update(dt: Float) {
        time += dt
        scale = 1 + offset * MathUtils.sin(MathUtils.PI2 * time / interval)
    }

}