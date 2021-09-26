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
    private val leftArrow = ImageButton(
        context.getImage("areaselectarrow"),
        50f,
        Constants.HEIGHT / 4,
        20f
    ).apply { flipped = true }
    private val rightArrow = ImageButton(
        context.getImage("areaselectarrow"),
        Constants.WIDTH - 50f,
        Constants.HEIGHT / 4,
        20f
    )

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

    private fun moveLeft() {
        if (currentIndex > 0) currentIndex--
        moveAreaButtons()
    }

    private fun moveRight() {
        if (currentIndex < areaButtons.size - 1) currentIndex++
        moveAreaButtons()
    }

    private fun handleInput() {
        when {
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) -> moveRight()
            Gdx.input.isKeyJustPressed(Input.Keys.LEFT) -> moveLeft()
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER) -> goToLevelSelect()
            Gdx.input.justTouched() -> {
                unprojectTouch()
                when {
                    leftArrow.containsPoint(touchPoint.x, touchPoint.y) -> moveLeft()
                    rightArrow.containsPoint(touchPoint.x, touchPoint.y) -> moveRight()
                    areaButtons[currentIndex].containsPoint(touchPoint.x, touchPoint.y) ->
                        goToLevelSelect()
                    // maybe
//                    else -> {
//                        val index = areaButtons.indexOfFirst {
//                            it.containsPoint(touchPoint.x, touchPoint.y)
//                        }
//                        if (index >= 0) {
//                            if (index == currentIndex) {
//                                goToLevelSelect()
//                            } else {
//                                currentIndex = index
//                                moveAreaButtons()
//                            }
//                        }
//                    }
                }
            }
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
                1f / (1f + (Constants.WIDTH / 2 - areaButton.pos.x).absoluteValue / 100f)
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
            leftArrow.render(sb)
            rightArrow.render(sb)
        }
    }
}