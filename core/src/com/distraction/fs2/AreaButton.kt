package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class AreaButton(image: TextureRegion, x: Float = 0f, y: Float = 0f) : ImageButton(image, x, y) {

    var scale = 1f

    override fun render(sb: SpriteBatch) {
        val scaledWidth = width * scale
        val scaledHeight = height * scale
        sb.draw(
            image,
            pos.x - scaledWidth / 2f,
            pos.y - scaledHeight / 2f,
            scaledWidth,
            scaledHeight
        )
    }

}