package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.TileMapData

class LevelFinishState(context: Context, private val level: Int, private val moves: Int, private val best: Int) : GameState(context) {
    private val dot = context.getImage("dot")
    private val dimColor = Color(0f, 0f, 0f, 0f)

    private val completeImage = Button(context.getImage("complete"), 0f, 80f, centered = true)
    private val bestLabel = NumberLabel(context, context.getImage("best"), Vector2(Constants.WIDTH / 2 - 20f, Constants.HEIGHT / 2f), best)
    private val movesLabel = NumberLabel(context, context.getImage("moves"), Vector2(Constants.WIDTH / 2 - 20f, Constants.HEIGHT / 2f - 10), moves)
    private val newRecordImage = Button(context.getImage("newrecord"), 0f, 40f, centered = true)

    private val restartButton = Button(context.getImage("restart"), 5f, 98f)
    private val backButton = Button(context.getImage("back"), 5f, 115f)
    private val nextButton = Button(context.getImage("next"), 0f, 15f, centered = true)

    init {
        context.gsm.depth++
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            if (Gdx.input.justTouched()) {
                touchPoint.set(1f * Gdx.input.x, 1f * Gdx.input.y, 0f)
                camera.unproject(touchPoint)
                if (level < TileMapData.levelData.size && nextButton.rect.contains(touchPoint)) {
                    ignoreInput = true
                    context.gsm.push(TransitionState(context, PlayState(context, level + 1), 2))
                } else if (backButton.rect.contains(touchPoint)) {
                    ignoreInput = true
                    context.gsm.push(TransitionState(context, LevelSelectState(context, (level - 1) / LevelSelectState.LEVELS_PER_PAGE), 2))
                } else if (restartButton.rect.contains(touchPoint)) {
                    ignoreInput = true
                    context.gsm.push(TransitionState(context, PlayState(context, level), 2))
                }
            }
        }
        if (dimColor.a < 0.7f) {
            dimColor.a += 2f * dt
            if (dimColor.a > 0.7f) {
                dimColor.a = 0.7f
            }
        }
    }

    override fun render(sb: SpriteBatch) {
        sb.use {
            val c = sb.color
            sb.color = dimColor
            sb.draw(dot, 0f, 0f, Constants.WIDTH, Constants.HEIGHT)
            sb.color = c

            sb.drawButton(completeImage)
            bestLabel.render(sb)
            movesLabel.render(sb)
            if (best == 0 || moves < best) {
                sb.drawButton(newRecordImage)
            }

            sb.drawButton(restartButton)
            sb.drawButton(backButton)
            if (level < TileMapData.levelData.size) sb.drawButton(nextButton)
        }
    }
}