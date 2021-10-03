package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.tilemap.data.GameColor

class InfoBox(
    context: Context,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    private val color: GameColor = GameColor.PURPLE
): ImageButton(context.getImage("pixel"), x, y) {

    private val patch = Array(8) {
        context.getImage("infobox", it)
    }
    private val pixel = context.getImage("pixel")

    private val left = x - width / 2
    private val right = x + width / 2
    private val top = y + height / 2
    private val bottom = y - height / 2

    init {
        this.width = width
        this.height = height
    }

    override fun render(sb: SpriteBatch) {
        sb.setColor(color)
        sb.draw(pixel, left, bottom, width, height)
        sb.resetColor()
        sb.drawCentered(patch[1], left, top, width, patch[1].regionHeight.toFloat())
        sb.drawCentered(patch[6], left, bottom, width, patch[6].regionHeight.toFloat())
        sb.drawCentered(patch[3], left, bottom, patch[3].regionWidth.toFloat(), height)
        sb.drawCentered(patch[4], right, bottom, patch[3].regionWidth.toFloat(), height)
        sb.drawCentered(patch[0], left, top)
        sb.drawCentered(patch[2], right, top)
        sb.drawCentered(patch[5], left, bottom)
        sb.drawCentered(patch[7], right, bottom)
    }

}