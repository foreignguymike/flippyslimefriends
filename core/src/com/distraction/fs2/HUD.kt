package com.distraction.fs2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.ButtonListener.ButtonType.*
import com.distraction.fs2.tilemap.data.GameColor

class HUD(
    context: Context,
    level: Int,
    private val buttonListener: ButtonListener,
    private val multiplayer: Boolean
) {
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
    private val bottomButtons = mapOf(
        LEFT to
                ImageButton(
                    context.getImage("upleftarrow"),
                    a.x - dist, a.y + dist, 5f
                ),
        UP to
                ImageButton(
                    context.getImage("uprightarrow"),
                    a.x + dist, a.y + dist, 5f
                ),
        RIGHT to
                ImageButton(
                    context.getImage("downrightarrow"),
                    a.x + dist, a.y - dist, 5f
                ),
        DOWN to
                ImageButton(
                    context.getImage("downleftarrow"),
                    a.x - dist, a.y - dist, 5f
                )
    )
    private val topButtons = mapOf(
        BACK to
                IconButton(
                    context.getImage("backicon"),
                    context.getImage("iconbuttonbg"),
                    25f, Constants.HEIGHT - HEIGHT / 2f,
                    5f
                ),
        RESTART to
                IconButton(
                    context.getImage("restarticon"),
                    context.getImage("iconbuttonbg"),
                    65f, Constants.HEIGHT - HEIGHT / 2f,
                    5f
                )
    )
    private val switchButton = TextButton(
        context.getImage("switch"),
        context.getImage("buttonbg"),
        Constants.WIDTH / 2f, 30f, 5f
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
        ),
        NumberLabel(
            context,
            context.getImage("leveltitle"),
            Vector2(130f, Constants.HEIGHT - HEIGHT / 2f),
            level + 1,
            NumberFont.NumberSize.LARGE
        )
    )

    fun setTarget(target: Int) {
        labels[0].font.num = target
    }

    fun setBest(best: Int) {
        if (best == 0) {
            labels[1].font.num = -1
        } else {
            labels[1].font.num = best
        }
    }

    fun getGoal() = labels[0].font.num
    fun getBest() = labels[1].font.num
    fun getMoves() = labels[2].font.num

    fun incrementMoves() = labels[2].font.num++

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
        topButtons.values.forEach { it.scale = 1f }
        bottomButtons.values.forEach { it.scale = 1f }
        switchButton.scale = 1f
        if (Gdx.input.isTouched) {
            touchPoint.set(1f * Gdx.input.x, 1f * Gdx.input.y, 0f)
            topCam.unproject(touchPoint)
            topButtons.forEach { (key, value) ->
                if (value.containsPoint(touchPoint.x, touchPoint.y)) {
                    buttonListener.onButtonPressed(key)
                    value.scale = 0.75f
                } else {
                    value.scale = 1f
                }
            }
            touchPoint.set(1f * Gdx.input.x, 1f * Gdx.input.y, 0f)
            bottomCam.unproject(touchPoint)
            bottomButtons.forEach { (key, value) ->
                if (value.containsPoint(touchPoint.x, touchPoint.y)) {
                    buttonListener.onButtonPressed(key)
                    value.scale = 0.75f
                } else {
                    value.scale = 1f
                }
            }
            if (switchButton.containsPoint(touchPoint.x, touchPoint.y)) {
                switchButton.scale = 0.75f
            }
        }
        if (Gdx.input.justTouched()) {
            if (switchButton.scale < 1f) {
                buttonListener.onButtonPressed(SWITCH)
            }
        }
    }

    fun update() {
        updateVisibility()
    }

    fun render(sb: SpriteBatch) {
        sb.projectionMatrix = topCam.combined
        sb.setColor(GameColor.DARK_TEAL, 0.5f)
        sb.draw(pixel, 0f, Constants.HEIGHT - HEIGHT, Constants.WIDTH, HEIGHT)
        sb.resetColor()
        labels.forEach { it.render(sb) }
        topButtons.forEach { it.value.render(sb) }

        sb.projectionMatrix = bottomCam.combined
        bottomButtons.forEach { it.value.render(sb) }
        if (multiplayer) {
            switchButton.render(sb)
        }
    }

    companion object {
        const val HEIGHT = 48f
    }
}

interface ButtonListener {
    enum class ButtonType {
        UP,
        LEFT,
        DOWN,
        RIGHT,
        RESTART,
        BACK,
        SWITCH
    }

    fun onButtonPressed(type: ButtonType)
}

class NumberLabel(
    context: Context,
    private val image: TextureRegion,
    private val pos: Vector2,
    num: Int = -1,
    size: NumberFont.NumberSize = NumberFont.NumberSize.MEDIUM
) {
    val font = NumberFont(context, size = size)

    init {
        font.num = num
    }

    fun render(sb: SpriteBatch) {
        sb.draw(image, pos.x - image.regionWidth / 2, pos.y - image.regionHeight / 2)
        font.render(sb, pos.x + image.regionWidth / 2 + 5, pos.y)
    }
}