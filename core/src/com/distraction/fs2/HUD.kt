package com.distraction.fs2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.ButtonListener.ButtonType.*
import com.distraction.fs2.tilemap.data.GameColor

class HUD(context: Context, private val buttonListener: ButtonListener) {
    private val touchPoint = Vector3()
    private val pixel = context.getImage("pixel")

    var hideInfo = false

    private val topCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }
    private val bottomCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    // for arrow button placement
    private val a = Vector2(60f, 60f)
    private val dist = 22f
    private val arrowButtons = mapOf(
        LEFT to
                ImageButton(
                    context.getImage("upleftarrow"),
                    a.x - dist, a.y + dist
                ),
        UP to
                ImageButton(
                    context.getImage("uprightarrow"),
                    a.x + dist, a.y + dist
                ),
        RIGHT to
                ImageButton(
                    context.getImage("downrightarrow"),
                    a.x + dist, a.y - dist
                ),
        DOWN to
                ImageButton(
                    context.getImage("downleftarrow"),
                    a.x - dist, a.y - dist
                )
    )
    private val uiButtons = mapOf(
        BACK to
                IconButton(
                    context.getImage("backicon"),
                    context.getImage("iconbuttonbg"),
                    25f, Constants.HEIGHT - 22f,
                    5f
                ),
        RESTART to
                IconButton(
                    context.getImage("restarticon"),
                    context.getImage("iconbuttonbg"),
                    65f, Constants.HEIGHT - 22f,
                    5f
                )
    )

    private val labels = arrayOf(
        NumberLabel(
            context,
            context.getImage("goal"),
            Vector2(Constants.WIDTH - 50f, Constants.HEIGHT - 15f)
        ),
        NumberLabel(
            context,
            context.getImage("best"),
            Vector2(Constants.WIDTH - 50f, Constants.HEIGHT - 35f)
        ),
        NumberLabel(
            context,
            context.getImage("moves"),
            Vector2(Constants.WIDTH - 130f, Constants.HEIGHT - 35f),
            0
        )
    )

    fun setTarget(target: Int) {
        labels[0].num = target
    }

    fun setBest(best: Int) {
        if (best == 0) {
            labels[1].num = -1
        } else {
            labels[1].num = best
        }
    }

    fun getGoal() = labels[0].num
    fun getBest() = labels[1].num
    fun getMoves() = labels[2].num

    fun incrementMoves() = labels[2].num++

    private fun updateVisibility() {
        if (hideInfo) {
            topCam.position.lerp(Constants.WIDTH / 2f, Constants.HEIGHT / 2f - HEIGHT * 2, 0f, 0.1f)
            bottomCam.position.lerp(Constants.WIDTH / 2f, 300f, 0f, 0.1f)
        } else {
            topCam.position.lerp(Constants.WIDTH / 2f, Constants.HEIGHT / 2f, 0f, 0.1f)
            bottomCam.position.lerp(Constants.WIDTH / 2f, Constants.HEIGHT / 2f, 0f, 0.1f)
        }
        topCam.update()
        bottomCam.update()
    }

    fun handleInput() {
        arrowButtons.values.forEach { it.scale = 1f }
        uiButtons.values.forEach { it.scale = 1f }
        if (Gdx.input.isTouched) {
            touchPoint.set(1f * Gdx.input.x, 1f * Gdx.input.y, 0f)
            topCam.unproject(touchPoint)
            arrowButtons.forEach { (key, value) ->
                if (value.containsPoint(touchPoint.x, touchPoint.y)) {
                    buttonListener.onButtonPressed(key)
                    value.scale = 0.75f
                }
            }
            uiButtons.forEach { (key, value) ->
                if (value.containsPoint(touchPoint.x, touchPoint.y)) {
                    buttonListener.onButtonPressed(key)
                    value.scale = 0.75f
                }
            }
        }
    }

    fun update() {
        updateVisibility()
    }

    fun render(sb: SpriteBatch) {
        sb.projectionMatrix = topCam.combined
        sb.setColor(GameColor.DARK_TEAL)
        sb.draw(pixel, 0f, Constants.HEIGHT - HEIGHT, Constants.WIDTH, HEIGHT)
        sb.resetColor()
        sb.draw(pixel, 0f, Constants.HEIGHT - HEIGHT + 2f, Constants.WIDTH, 1f)
        labels.forEach { it.render(sb) }
        uiButtons.values.forEach { it.render(sb) }

        sb.projectionMatrix = bottomCam.combined
        arrowButtons.values.forEach { it.render(sb) }
    }

    companion object {
        const val HEIGHT = 50f
    }
}

interface ButtonListener {
    enum class ButtonType {
        UP,
        LEFT,
        DOWN,
        RIGHT,
        RESTART,
        BACK
    }

    fun onButtonPressed(type: ButtonType)
}

class NumberLabel(
    context: Context,
    private val image: TextureRegion,
    private val pos: Vector2,
    var num: Int = -1
) {
    private val numberFont = NumberFont(context)
    fun render(sb: SpriteBatch) {
        sb.draw(image, pos.x - image.regionWidth / 2, pos.y - image.regionHeight / 2)
        numberFont.render(sb, pos.x + image.regionWidth / 2 + 5, pos.y, num)
    }
}