package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.*

class TitleState(context: Context) : GameState(context) {
    private val title = context.assets.getAtlas().findRegion("title")
    private val hudCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    init {
        hudCam.position.set(Constants.WIDTH / 2f, -100f, 0f)
        hudCam.update()
        camera.position.set(0f, 100f, 0f)
        camera.update()
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            if (Gdx.input.justTouched()) {
                ignoreInput = true
                context.gsm.push(TransitionState(context, LevelSelectState(context)))
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                ignoreInput = true
                context.gsm.push(TransitionState(context, LevelSelectState(context)))
            }
        }

        hudCam.position.set(hudCam.position.lerp(Constants.WIDTH / 2f, Constants.HEIGHT / 2f, 0f, 0.1f))
        hudCam.update()
        camera.update()
    }

    override fun render(sb: SpriteBatch) {
        clearScreen(76, 176, 219)
        sb.use {
            sb.projectionMatrix = hudCam.combined
            sb.draw(title, (Constants.WIDTH - title.regionWidth) / 2f, 60f)

            sb.projectionMatrix = camera.combined
        }
    }
}