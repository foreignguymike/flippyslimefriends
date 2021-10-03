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
    private val goal: Int,
    private val newRecord: Boolean
) : GameState(context) {

    private val dimColor = Color(0f, 0f, 0f, 0f)
    private val staticCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    private var time = 0f
    private val time1 = 0.5f
    var alpha = 0f
    var scale = 0f

    private val infoBox = InfoBox(
        context,
        Constants.WIDTH / 2f,
        Constants.HEIGHT / 2f,
        Constants.WIDTH - 100f,
        4f * Constants.HEIGHT / 5f,
        GameColor.DARK_BLUE
    )

    private val completeImage =
        ImageButton(context.getImage("complete"), Constants.WIDTH / 2f, Constants.HEIGHT - 50f)

    private val diamondEmpty =
        ImageButton(context.getImage("diamondfinishempty"), Constants.WIDTH / 2, Constants.HEIGHT / 2 + 50f).apply {
            alpha = 0f
            scale = 20f
        }

    private val diamond =
        ImageButton(context.getImage("diamondfinish"), Constants.WIDTH / 2, Constants.HEIGHT / 2 + 50f).apply {
            alpha = 0f
            scale = 20f
        }

    private val star =
        ImageButton(context.getImage("starfinish"), Constants.WIDTH / 2, Constants.HEIGHT / 2 - 15f).apply {
            alpha = 0f
            scale = 20f
        }

    private val bestLabel = NumberLabel(
        context,
        context.getImage("best"),
        Vector2(3f * Constants.WIDTH / 4f, Constants.HEIGHT / 2f - 20f),
        best
    )

    private val goalLabel = NumberLabel(
        context,
        context.getImage("goal"),
        Vector2(3f * Constants.WIDTH / 4f, Constants.HEIGHT / 2f),
        best
    )

    private val movesLabel = NumberLabel(
        context,
        context.getImage("moves"),
        Vector2(3f * Constants.WIDTH / 4f, Constants.HEIGHT / 2f + 20),
        moves
    )

    private val newRecordImage =
        ImageButton(context.getImage("newrecord"), 3f * Constants.WIDTH / 4f, Constants.HEIGHT / 2f - 50)

    private val backButton = IconButton(
        context.getImage("backicon"),
        context.getImage("iconbuttonbg"),
        Constants.WIDTH / 2 - infoBox.width / 2 + 24f,
        Constants.HEIGHT / 2 + infoBox.height / 2 - 24f,
        5f
    )

    private val restartButton = IconButton(
        context.getImage("restarticon"),
        context.getImage("iconbuttonbg"),
        Constants.WIDTH / 2 - infoBox.width / 2 + 64f,
        Constants.HEIGHT / 2 + infoBox.height / 2 - 24f,
        5f
    )

    private val nextButton = IconButton(
        context.getImage("next"),
        context.getImage("buttonbg"),
        Constants.WIDTH / 2f,
        55f,
        5f
    )

    init {
        context.gsm.depth++
        camera.position.y = 2f * Constants.HEIGHT
        camera.update()
    }

    private fun handleInput() {
        if (Gdx.input.justTouched()) {
            unprojectTouch()
            when {
                nextButton.containsPoint(touchPoint.x, touchPoint.y) -> {
                    if (level < context.gameData.getMapData(area).size - 1) {
                        ignoreInput = true
                        context.gsm.push(
                            TransitionState(
                                context,
                                PlayState(context, area, level + 1),
                                2
                            )
                        )
                    }
                }
                backButton.containsPoint(touchPoint.x, touchPoint.y) -> {
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
                }
                restartButton.containsPoint(touchPoint.x, touchPoint.y) -> {
                    ignoreInput = true
                    context.gsm.push(TransitionState(context, PlayState(context, area, level), 2))
                }
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
        time += dt
        if (time > time1) {
            alpha = (time - time1) / (time1 / 2)
            if (alpha > 1f) {
                alpha = 1f
            }
            scale = 50f / (49f * (time - time1) / (time1 / 2) + 1f)
            if (scale < 1f) {
                scale = 1f
            }

            diamondEmpty.alpha = alpha
            diamondEmpty.scale = scale
            diamond.alpha = alpha
            diamond.scale = scale
            star.alpha = alpha
            star.scale = scale
        }
        camera.position.lerp(Constants.WIDTH / 2f, Constants.HEIGHT / 2f, 0f, 0.1f)
        camera.update()
    }

    override fun render(sb: SpriteBatch) {
        sb.use {
            sb.projectionMatrix = staticCam.combined

            sb.color = dimColor
            sb.draw(pixel, 0f, 0f, Constants.WIDTH, Constants.HEIGHT)

            sb.projectionMatrix = camera.combined

            sb.resetColor()
            infoBox.render(sb)
            diamondEmpty.render(sb)
            if (moves == goal) {
                diamond.render(sb)
            }
            star.render(sb)
            completeImage.render(sb)
            goalLabel.render(sb)
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