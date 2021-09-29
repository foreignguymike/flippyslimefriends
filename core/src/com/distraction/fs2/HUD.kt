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
    private val infoBox = InfoBox(context, Constants.WIDTH - 55f, Constants.HEIGHT - 40f, 100f, 70f)

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
                )
    )

    private val backButton = TextButton(
        context.getImage("back"),
        context.getImage("buttonbg"),
        50f, Constants.HEIGHT - 20f
    )
    private val restartButton = TextButton(
        context.getImage("restart"),
        context.getImage("buttonbg"),
        50f, Constants.HEIGHT - 45f
    )

    private val labels = arrayOf(
        NumberLabel(
            context,
            context.getImage("goal"),
            Vector2(Constants.WIDTH - 60f, Constants.HEIGHT - 20f)
        ),
        NumberLabel(
            context,
            context.getImage("best"),
            Vector2(Constants.WIDTH - 60f, Constants.HEIGHT - 40f)
        ),
        NumberLabel(
            context,
            context.getImage("moves"),
            Vector2(Constants.WIDTH - 64f, Constants.HEIGHT - 60f),
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

    fun getBest() = labels[1].num
    fun incrementMoves() = labels[2].num++
    fun getMoves() = labels[2].num

    private val cam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    fun update() {
        buttons.values.forEach { it.scale = 1f }
        if (Gdx.input.isTouched) {
            touchPoint.set(1f * Gdx.input.x, 1f * Gdx.input.y, 0f)
            cam.unproject(touchPoint)
            buttons.forEach { (key, value) ->
                if (value.containsPoint(touchPoint.x, touchPoint.y)) {
                    buttonListener.onButtonPressed(key)
                    value.scale = 0.75f
                }
            }
            if (backButton.containsPoint(touchPoint.x, touchPoint.y)) {
                buttonListener.onButtonPressed(BACK)
            }
            if (restartButton.containsPoint(touchPoint.x, touchPoint.y)) {
                buttonListener.onButtonPressed(RESTART)
            }
        }
    }

    fun render(sb: SpriteBatch) {
        sb.projectionMatrix = cam.combined
        sb.setColor(0f, 0f, 0f, 0.5f)
        sb.resetColor()
        buttons.values.forEach { it.render(sb) }
        backButton.render(sb)
        restartButton.render(sb)
        infoBox.render(sb)
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
    var num: Int = -1
) {
    private val numberFont = NumberFont(context)
    fun render(sb: SpriteBatch) {
        sb.draw(image, pos.x - image.regionWidth / 2, pos.y - image.regionHeight / 2)
        numberFont.render(sb, pos.x + image.regionWidth / 2 + 5, pos.y, num)
    }
}