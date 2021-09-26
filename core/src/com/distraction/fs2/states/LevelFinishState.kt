package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.data.Area

class LevelFinishState(
    context: Context,
    private val area: Area,
    private val level: Int,
    private val moves: Int,
    private val best: Int
) : GameState(context) {

    private val dot = context.getImage("dot")
    private val dimColor = Color(0f, 0f, 0f, 0f)

    private val completeImage = ImageButton(context.getImage("complete"), Constants.WIDTH / 2f, Constants.HEIGHT / 2)
    private val bestLabel = NumberLabel(
        context,
        context.getImage("best"),
        Vector2(Constants.WIDTH / 2 - 20f, Constants.HEIGHT  - 20f),
        best
    )
    private val movesLabel = NumberLabel(
        context,
        context.getImage("moves"),
        Vector2(Constants.WIDTH / 2 - 20f, Constants.HEIGHT - 30f),
        moves
    )
    private val newRecordImage = ImageButton(context.getImage("newrecord"), Constants.WIDTH / 2f, Constants.HEIGHT / 4)

    private val restartButton = ImageButton(context.getImage("restart"), 50f, Constants.HEIGHT - 37f)
    private val backButton = ImageButton(context.getImage("back"), 50f, Constants.HEIGHT - 20f)
    private val nextButton = ImageButton(context.getImage("next"), Constants.WIDTH / 2f, 15f)

    init {
        context.gsm.depth++
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            if (Gdx.input.justTouched()) {
                unprojectTouch()
                if (level < context.gameData.getMapData(area).size && nextButton.containsPoint(touchPoint.x, touchPoint.y)) {
                    ignoreInput = true
                    context.gsm.push(
                        TransitionState(
                            context,
                            PlayState(context, area, level + 1),
                            2
                        )
                    )
                } else if (backButton.containsPoint(touchPoint.x, touchPoint.y)) {
                    ignoreInput = true
                    context.gsm.push(
                        TransitionState(
                            context,
                            LevelSelectState(
                                context,
                                area,
                                level
                            ),
                            2
                        )
                    )
                } else if (restartButton.containsPoint(touchPoint.x, touchPoint.y)) {
                    ignoreInput = true
                    context.gsm.push(TransitionState(context, PlayState(context, area, level), 2))
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

            completeImage.render(sb)
            bestLabel.render(sb)
            movesLabel.render(sb)
            if (best == 0 || moves < best) {
                newRecordImage.render(sb)
            }

            restartButton.render(sb)
            backButton.render(sb)
            if (level < context.gameData.getMapData(area).size) nextButton.render(sb)
        }
    }
}