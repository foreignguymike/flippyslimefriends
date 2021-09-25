package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.data.Area
import com.distraction.fs2.tilemap.data.GameColor
import kotlin.math.absoluteValue

class AreaSelectState(context: Context, private var currentIndex: Int = 0) : GameState(context) {

    private val width = Constants.WIDTH / 5f
    private val areaButtons = Area.values().mapIndexed { index, area ->
        AreaButton(context.getImage(area.text)).apply {
            setPosition(Constants.WIDTH / 2 + index * width, Constants.HEIGHT / 2)
        }
    }

    init {
        if (currentIndex != 0) {
            moveAreaButtons(false)
        }
    }

    private fun goToLevelSelect() {
        context.gsm.push(
            TransitionState(
                context,
                LevelSelectState(context, Area.values()[currentIndex])
            )
        )
    }

    private fun handleInput() {
        when {
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) -> {
                if (currentIndex < areaButtons.size - 1) currentIndex++
                moveAreaButtons()
            }
            Gdx.input.isKeyJustPressed(Input.Keys.LEFT) -> {
                if (currentIndex > 0) currentIndex--
                moveAreaButtons()
            }
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER) -> goToLevelSelect()
        }
    }

    private fun moveAreaButtons(lerp: Boolean = true) {
        areaButtons.forEachIndexed { index, areaButton ->
            if (lerp) {
                areaButton.lerpTo(
                    Constants.WIDTH / 2 + index * width - currentIndex * width,
                    Constants.HEIGHT / 2
                )
            } else {
                areaButton.setPosition(
                    Constants.WIDTH / 2 + index * width - currentIndex * width,
                    Constants.HEIGHT / 2
                )
            }
        }
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            handleInput()
        }
        areaButtons.forEachIndexed { index, areaButton ->
            areaButton.scale =
                1f / (1f + (Constants.WIDTH / 2 - areaButton.x()).absoluteValue / 100f)
            areaButton.update(dt)
        }
    }

    override fun render(sb: SpriteBatch) {
        clearScreen(GameColor.SKY_BLUE)
        sb.use {
            sb.projectionMatrix = camera.combined

            sb.resetColor()
            for (i in 0 until currentIndex) {
                areaButtons[i].render(sb)
            }
            for (i in areaButtons.size - 1 downTo currentIndex) {
                areaButtons[i].render(sb)
            }
        }
    }
}