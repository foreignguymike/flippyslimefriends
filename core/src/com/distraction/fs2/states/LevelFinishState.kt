package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.data.Area
import com.distraction.fs2.tilemap.data.GameColor

class LevelFinishState(
    context: Context,
    private val area: Area,
    private val level: Int,
    private val moves: Int,
    private val best: Int,
    private val newRecord: Boolean
) : GameState(context) {

    private val dimColor = Color(0f, 0f, 0f, 0f)
    private val staticCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    private var bgHeight = 0f
    private val maxBgHeight = 2 * Constants.HEIGHT / 4

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
        Vector2(Constants.WIDTH / 2 - 25f, Constants.HEIGHT - 40f),
        moves
    )
    private val newRecordImage = ImageButton(context.getImage("newrecord"), Constants.WIDTH / 2f, Constants.HEIGHT / 4)

    private val restartButton = TextButton(context.getImage("restart"), context.getImage("buttonbg"),50f, Constants.HEIGHT - 45f)
    private val backButton = TextButton(context.getImage("back"), context.getImage("buttonbg"),50f, Constants.HEIGHT - 20f)
    private val nextButton = TextButton(context.getImage("next"), context.getImage("buttonbg"),Constants.WIDTH / 2f, 15f)

    init {
        context.gsm.depth++
        camera.position.y = Constants.HEIGHT
        camera.update()
    }

    private fun handleInput() {
        if (Gdx.input.justTouched()) {
            unprojectTouch()
            if (level < context.gameData.getMapData(area).size - 1 && nextButton.containsPoint(touchPoint.x, touchPoint.y)) {
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

    override fun update(dt: Float) {
        if (!ignoreInput) {
            handleInput()
        }
        if (dimColor.a < 0.7f) {
            dimColor.a += 2f * dt
            if (dimColor.a > 0.7f) {
                dimColor.a = 0.7f
            }
        }
        if (bgHeight < maxBgHeight) {
            bgHeight += 800f * dt
            if (bgHeight > maxBgHeight) {
                bgHeight = maxBgHeight
            }
        }
        camera.position.lerp(Constants.WIDTH / 2f, Constants.HEIGHT / 2f, 0f, 0.1f)
        camera.update()
    }

    override fun render(sb: SpriteBatch) {
        sb.use {
            sb.projectionMatrix = staticCam.combined

            sb.color = dimColor
            sb.draw(pixel, 0f, 0f, Constants.WIDTH, Constants.HEIGHT)
            sb.setColor(GameColor.DARK_BLUE)
            sb.draw(pixel, 0f, Constants.HEIGHT / 2 - bgHeight / 2, Constants.WIDTH, bgHeight)

            sb.projectionMatrix = camera.combined

            sb.resetColor()
            completeImage.render(sb)
            bestLabel.render(sb)
            movesLabel.render(sb)
            if (newRecord) {
                newRecordImage.render(sb)
            }

            restartButton.render(sb)
            backButton.render(sb)
            if (level < context.gameData.getMapData(area).size - 1) nextButton.render(sb)
        }
    }
}