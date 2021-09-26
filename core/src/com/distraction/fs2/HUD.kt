package com.distraction.fs2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.ButtonListener.ButtonType.*

class HUD(context: Context, private val buttonListener: ButtonListener) {
    private val touchPoint = Vector3()
    private val alpha = 0.5f

    // for arrow button placement
    private val a = Vector2(60f, 60f)
    private val dist = 22f

    private val buttons = hashMapOf(
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
                ),
        BACK to
                ImageButton(
                    context.getImage("back"),
                    50f, Constants.HEIGHT - 20f
                ),
        RESTART to
                ImageButton(
                    context.getImage("restart"),
                    50f, Constants.HEIGHT - 37f
                )
    )

    private val labels = arrayOf(
        NumberLabel(
            context,
            context.getImage("goal"),
            Vector2(Constants.WIDTH - 50f, Constants.HEIGHT - 16f),
            0
        ),
        NumberLabel(
            context,
            context.getImage("best"),
            Vector2(Constants.WIDTH - 50f, Constants.HEIGHT - 25f),
            0
        ),
        NumberLabel(
            context,
            context.getImage("moves"),
            Vector2(Constants.WIDTH - 55f, Constants.HEIGHT - 34f),
            0
        )
    )

    fun setTarget(target: Int) {
        labels[0].num = target
    }

    fun setBest(best: Int) {
        labels[1].num = best
    }

    fun getBest() = labels[1].num
    fun incrementMoves() = labels[2].num++
    fun getMoves() = labels[2].num

    private val cam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }
    private val fontCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH * 2f, Constants.HEIGHT * 2f)
    }

    fun update() {
        if (Gdx.input.isTouched) {
            touchPoint.set(1f * Gdx.input.x, 1f * Gdx.input.y, 0f)
            cam.unproject(touchPoint)
            buttons.forEach { (key, value) ->
                if (value.containsPoint(touchPoint.x, touchPoint.y)) {
                    buttonListener.onButtonPressed(key)
                }
            }
        }
    }

    fun render(sb: SpriteBatch) {
        sb.projectionMatrix = fontCam.combined
        sb.projectionMatrix = cam.combined
        buttons.values.forEach { it.render(sb) }
        labels.forEach { it.render(sb) }
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
    var num: Int
) {
    private val numberFont = NumberFont(context)
    fun render(sb: SpriteBatch) {
        sb.draw(image, pos.x - image.regionWidth / 2, pos.y - image.regionHeight / 2)
        numberFont.render(sb, pos.x + image.regionWidth + 5, pos.y, num)
    }
}