package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.tilemap.TileMap
import kotlin.math.min

class TileLight(context: Context, tileMap: TileMap, row: Int, col: Int) : TileObject(context, tileMap) {

    private var timer = 0f
    private var image = context.getImage("tilelight")

    init {
        setPositionFromTile(row, col)
        height = 0f
    }

    override fun update(dt: Float) {
        timer += dt
        height = min(image.regionHeight.toFloat(), image.regionHeight * timer / LIFE_TIME * 5f)
        if (timer >= LIFE_TIME) remove = true
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.setColor(1f, 1f, 1f, 1f - timer / LIFE_TIME)
        sb.draw(image.texture,
                isop.x - image.regionWidth / 2f,
                isop.y - TileMap.TILE_IHEIGHT / 2f + 2,
                image.regionWidth.toFloat(),
                height,
                image.regionX,
                image.regionY + image.regionHeight,
                image.regionWidth,
                -height.toInt(),
                false,
                true)
        sb.setColor(1f, 1f, 1f, 1f)
    }

    companion object {
        const val LIFE_TIME = 1f
    }
}