package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

/**
 * Need to be wary that Rectangle uses x,y as the bottom left corner.
 */
open class ImageButton(protected val image: TextureRegion, x: Float = 0f, y: Float = 0f) {

    protected var pos = Vector2()
    protected var width = image.regionWidth.toFloat()
    protected var height = image.regionHeight.toFloat()

    private var lerpAlpha = -1f
    private var destination = Vector2()

    init {
        setPosition(x, y)
    }

    fun x() = pos.x

    fun setPosition(x: Float, y: Float) {
        pos.x = x
        pos.y = y
    }

    fun lerpTo(x: Float, y: Float, a: Float = 0.1f) {
        destination.set(x, y)
        lerpAlpha = a
    }

    fun containsPoint(x: Float, y: Float): Boolean {
        return pos.x - width / 2 <= x && pos.x + width / 2 >= x && pos.y - height / 2 <= y && pos.y + height / 2 >= y
    }

    fun update(dt: Float) {
        if (lerpAlpha >= 0f) {
            pos.lerp(destination, lerpAlpha)
        }
        if (destination.diff(pos) < 0.1f) {
            lerpAlpha = -1f
        }
    }

    open fun render(sb: SpriteBatch) {
        sb.draw(image, pos.x - width / 2f, pos.y - height / 2f)
    }

}