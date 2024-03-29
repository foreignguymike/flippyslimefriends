package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.data.GameColor

class TitleState(context: Context) : GameState(context) {
    private val title = ImageButton(context.getImage("title"))
    private val playButton = TextButton(context.getImage("play"), context.getImage("button"),Constants.WIDTH / 4, 30f)
    private val onlineButton =
        TextButton(context.getImage("online"), context.getImage("button"), Constants.WIDTH / 2, 30f)
    private val avatarButton =
        TextButton(context.getImage("avatar"), context.getImage("button"),3 * Constants.WIDTH / 4, 30f)

    init {
        title.setPosition(Constants.WIDTH / 2f, Constants.HEIGHT + 100f)
        title.lerpTo(Constants.WIDTH / 2f, Constants.HEIGHT / 2f)
    }

    private fun goToAreaSelect() {
        ignoreInput = true
        context.gsm.push(TransitionState(context, AreaSelectState(context)))
    }

    private fun handleInput() {
        if (Gdx.input.justTouched()) {
            unprojectTouch()
            if (playButton.containsPoint(touchPoint.x, touchPoint.y)) {
                goToAreaSelect()
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) goToAreaSelect()
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            handleInput()
        }
        title.update(dt)
        camera.update()
    }

    override fun render(sb: SpriteBatch) {
        clearScreen(GameColor.SKY_BLUE)
        sb.use {
            sb.projectionMatrix = camera.combined

            sb.color = GameColor.MIDNIGHT_BLUE
            sb.draw(pixel, 0f, 0f, Constants.WIDTH, 60f)
            sb.draw(pixel, 0f, Constants.HEIGHT - 60f, Constants.WIDTH, 60f)
            sb.resetColor()
            sb.draw(pixel, 0f, 56f, Constants.WIDTH, 1f)
            sb.draw(pixel, 0f, Constants.HEIGHT - 58f, Constants.WIDTH, 1f)

            sb.resetColor()
            title.render(sb)
            playButton.render(sb)
            onlineButton.render(sb)
            avatarButton.render(sb)
        }
    }
}