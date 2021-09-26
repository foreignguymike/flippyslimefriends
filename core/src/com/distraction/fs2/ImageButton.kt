package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

/**
 * Need to be wary that Rectangle uses x,y as the bottom left corner.
 */
open class ImageButton(
    protected val image: TextureRegion,
    x: Float = 0f,
    y: Float = 0f,
    private val padding: Float = 0f
) {

    var pos = Vector2()
    var width = image.regionWidth.toFloat()
    var height = image.regionHeight.toFloat()

    private var lerpAlpha = -1f
    private var destination = Vector2()

    var flipped = false

    init {
        setPosition(x, y)
    }

    fun setPosition(x: Float, y: Float) {
        pos.x = x
        pos.y = y
    }

    fun lerpTo(x: Float, y: Float, a: Float = 0.1f) {
        destination.set(x, y)
        lerpAlpha = a
    }

    fun containsPoint(x: Float, y: Float) =
        pos.x - width / 2 - padding <= x
                && pos.x + width / 2 + padding >= x
                && pos.y - height / 2 - padding <= y
                && pos.y + height / 2 + padding >= y

    fun update(dt: Float) {
        if (lerpAlpha >= 0f) {
            pos.lerp(destination, lerpAlpha)
        }
        if (destination.diff(pos) < 0.1f) {
            lerpAlpha = -1f
        }
    }

    open fun render(sb: SpriteBatch) {
        if (flipped) {
            sb.drawHFlip(image, pos.x + width / 2f, pos.y - height / 2f)
        } else {
            sb.draw(image, pos.x - width / 2f, pos.y - height / 2f)
        }
    }

}