package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TextButton(
    private val textImage: TextureRegion,
    bgImage: TextureRegion,
    x: Float = 0f,
    y: Float = 0f
) : ImageButton(bgImage, x, y) {

    override fun render(sb: SpriteBatch) {
        super.render(sb)
        sb.draw(textImage, pos.x - textImage.regionWidth / 2f, pos.y - textImage.regionHeight / 2f)
    }

}