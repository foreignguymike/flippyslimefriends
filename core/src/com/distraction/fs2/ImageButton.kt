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

    var scale = 1f
    var alpha = 1f

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

    fun containsPoint(x: Float, y: Float): Boolean {
        val scaledWidth = width * scale
        val scaledHeight = height * scale
        return pos.x - scaledWidth / 2 - padding <= x
                && pos.x + scaledWidth / 2 + padding >= x
                && pos.y - scaledHeight / 2 - padding <= y
                && pos.y + scaledHeight / 2 + padding >= y
    }

    open fun update(dt: Float) {
        if (lerpAlpha >= 0f) {
            pos.lerp(destination, lerpAlpha)
        }
        if (destination.diff(pos) < 0.1f) {
            lerpAlpha = -1f
        }
    }

    open fun render(sb: SpriteBatch) {
        val scaledWidth = width * scale
        val scaledHeight = height * scale
        val temp = sb.color.a
        sb.setAlpha(alpha)
        if (flipped) {
            sb.drawHFlip(
                image,
                pos.x + scaledWidth / 2f,
                pos.y - scaledHeight / 2f,
                scaledWidth,
                scaledHeight
            )
        } else {
            sb.draw(
                image,
                pos.x - scaledWidth / 2f,
                pos.y - scaledHeight / 2f,
                scaledWidth,
                scaledHeight
            )
        }
        sb.setAlpha(temp)
    }

}