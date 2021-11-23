package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

typealias IconButton = TextButton

class TextButton(
    private val textImage: TextureRegion,
    bgImage: TextureRegion,
    x: Float = 0f,
    y: Float = 0f,
    padding: Float = 0f
) : ImageButton(bgImage, x, y, padding) {

    fun setImageBg(bgImage: TextureRegion) {
        image = bgImage
    }

    override fun render(sb: SpriteBatch) {
        super.render(sb)
        val scaledWidth = textImage.regionWidth * scale
        val scaledHeight = textImage.regionHeight * scale
        sb.draw(
            textImage,
            pos.x - scaledWidth / 2f,
            pos.y - scaledHeight / 2f,
            scaledWidth,
            scaledHeight
        )
    }

}