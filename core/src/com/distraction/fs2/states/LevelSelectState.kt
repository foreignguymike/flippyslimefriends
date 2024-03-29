package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.data.Area
import com.distraction.fs2.tilemap.data.GameColor
import kotlin.math.abs

class LevelSelectState(
    context: Context,
    private val area: Area,
    private var level: Int = -1
) : GameState(context) {

    private val diamond = context.getImage("leveldiamondicon")
    private val diamondEmpty = context.getImage("leveldiamondemptyicon")
    private val numRows = 3
    private val numCols = 5
    private val pageSize = numRows * numCols
    private val widthPadding = 120f
    private val heightPadding = 60f
    private val cellWidth = (Constants.WIDTH - 2 * widthPadding) / numCols
    private val cellHeight = (Constants.HEIGHT - 2 * heightPadding) / numRows
    private var page = level / pageSize
    private var levelData = context.gameData.mapData[area]
        ?: throw IllegalArgumentException("invalid area $area")
    private var numLevels = levelData.size
    private val maxPages = MathUtils.ceil(1f * numLevels / pageSize)

    private val levels = Array(numLevels) {
        val page = it / pageSize
        val row = (it % pageSize) / numCols
        val col = it % numCols
        val x = widthPadding + col * cellWidth + cellWidth / 2 + Constants.WIDTH * page
        val y = Constants.HEIGHT - heightPadding - (row * cellHeight + cellHeight / 2)
        ImageButton(context.getImage("levelbutton"), x, y)
    }

    private val numberFont = NumberFont(context, true, NumberFont.NumberSize.LARGE)
    private val levelSelectedBorder = BreathingImage(
        context.getImage("levelselectedborder"),
        if (level < 0) 0f else levels[level].pos.x,
        if (level < 0) 0f else levels[level].pos.y,
        offset = 0.03f
    )
    private val levelSelectImage = context.getImage("levelselect")
    private val backButton =
        TextButton(context.getImage("back"), context.getImage("buttonbg"), Constants.WIDTH / 2, 20f)
    private val disableColor = Color(0.3f, 0.3f, 0.3f, 1f)
    private val staticCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    private val leftButton = BreathingImage(
        context.getImage("areaselectarrow"),
        50f, Constants.HEIGHT / 2, 10f
    ).apply { flipped = true }
    private val rightButton = BreathingImage(
        context.getImage("areaselectarrow"),
        Constants.WIDTH - 50f, Constants.HEIGHT / 2 - 5f, 10f
    )

    private val color = area.colorCopy()

    init {
        camera.position.set(Constants.WIDTH * page + Constants.WIDTH / 2, Constants.HEIGHT / 2, 0f)
        camera.update()

        rightButton.setPosition(
            Constants.WIDTH + if (page >= maxPages - 1) 50f else -50f,
            rightButton.pos.y
        )
        leftButton.setPosition(if (page == 0) -50f else 50f, leftButton.pos.y)
    }

    private fun incrementPage() {
        if (page < maxPages - 1) {
            page++
            updateNavButtons()
        }
    }

    private fun decrementPage() {
        if (page > 0) {
            page--
            updateNavButtons()
        }
    }

    private fun incrementLevel(amount: Int = 1) {
        if (level + amount < numLevels) {
            level += amount
            page = level / pageSize
            updateNavButtons()
            updateLevelSelectedBorder()
        }
    }

    private fun decrementLevel(amount: Int = 1) {
        if (level - amount >= 0) {
            level -= amount
            page = level / pageSize
            updateNavButtons()
            updateLevelSelectedBorder()
        }
    }

    private fun updateNavButtons() {
        if (page >= maxPages - 1) {
            rightButton.lerpTo(Constants.WIDTH + 50f, rightButton.pos.y, 8f)
        } else {
            rightButton.lerpTo(Constants.WIDTH - 50f, rightButton.pos.y, 8f)
        }
        if (page == 0) {
            leftButton.lerpTo(-50f, leftButton.pos.y, 8f)
        } else {
            leftButton.lerpTo(50f, leftButton.pos.y, 8f)
        }
    }

    private fun updateLevelSelectedBorder() {
        levelSelectedBorder.setPosition(
            if (level < 0) 0f else levels[level].pos.x,
            if (level < 0) 0f else levels[level].pos.y
        )
    }

    private fun getCamPosition() = Constants.WIDTH * page + Constants.WIDTH / 2

    private fun back() {
        ignoreInput = true
        context.gsm.push(
            TransitionState(
                context,
                AreaSelectState(context, area.ordinal)
            )
        )
    }

    private fun goToLevel(level: Int) {
        if (level in 0 until numLevels) {
            this.level = level
            updateLevelSelectedBorder()
            ignoreInput = true
            context.gsm.push(TransitionState(context, PlayState(context, area, level)))
        }
    }

    private fun handleInput() {
        if (Gdx.input.justTouched()) {
            if (abs(getCamPosition() - camera.position.x) < 10) {
                unprojectTouch()
                levels.forEachIndexed { i, it ->
                    if (it.containsPoint(touchPoint.x, touchPoint.y) && i < numLevels) {
                        goToLevel(i)
                    }
                }
            }

            unprojectTouch(staticCam)
            if (backButton.containsPoint(touchPoint.x, touchPoint.y)) {
                back()
            }
            if (leftButton.containsPoint(touchPoint.x, touchPoint.y)) {
                decrementPage()
            } else if (rightButton.containsPoint(touchPoint.x, touchPoint.y)) {
                incrementPage()
            }
        }
        when {
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER) -> goToLevel(level)
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) -> {
                val amount = if (level % numCols == numCols - 1) {
                    pageSize - numCols + 1
                } else {
                    1
                }
                if (level + amount < numLevels) {
                    incrementLevel(amount)
                }
            }
            Gdx.input.isKeyJustPressed(Input.Keys.LEFT) -> {
                val amount = if (level % numCols == 0) {
                    pageSize - numCols + 1
                } else {
                    1
                }
                decrementLevel(amount)
            }
            Gdx.input.isKeyJustPressed(Input.Keys.DOWN) -> {
                if (level % pageSize < pageSize - numCols) {
                    incrementLevel(numCols)
                }
            }
            Gdx.input.isKeyJustPressed(Input.Keys.UP) -> {
                if (level % pageSize >= numCols) {
                    decrementLevel(numCols)
                }
            }
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) -> back()
        }
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            handleInput()
        }
        camera.position.set(
            camera.position.lerp(
                getCamPosition(),
                Constants.HEIGHT / 2,
                0f,
                8f * dt
            )
        )
        camera.update()
        levelSelectedBorder.update(dt)
        leftButton.update(dt)
        rightButton.update(dt)
    }

    override fun render(sb: SpriteBatch) {
        clearScreen(color)
        sb.use {
            sb.projectionMatrix = staticCam.combined
            sb.color = GameColor.MIDNIGHT_BLUE
            sb.draw(pixel, 0f, 0f, Constants.WIDTH, 60f)
            sb.draw(pixel, 0f, Constants.HEIGHT - 60f, Constants.WIDTH, 60f)
            sb.resetColor()
            sb.draw(pixel, 0f, 56f, Constants.WIDTH, 1f)
            sb.draw(pixel, 0f, Constants.HEIGHT - 58f, Constants.WIDTH, 1f)

            sb.draw(
                levelSelectImage,
                (Constants.WIDTH - levelSelectImage.regionWidth) / 2,
                Constants.HEIGHT - levelSelectImage.regionHeight - 8
            )
            backButton.render(sb)

            sb.projectionMatrix = camera.combined
            levels.forEachIndexed { i, it ->
                sb.resetColor()
                val best = context.scoreHandler.getScores(area)[i]
                if (best == 0) {
                    sb.color = disableColor
                }
                it.render(sb)
                numberFont.num = i + 1
                numberFont.render(sb, it.pos.x, it.pos.y - 5)
                sb.resetColor()
                sb.draw(
                    if (best > 0 && best <= levelData[i].goal) {
                        diamond
                    } else {
                        diamondEmpty
                    },
                    it.pos.x - diamond.regionWidth / 2,
                    it.pos.y - diamond.regionHeight / 2 + 13
                )
                if (level == i) {
                    levelSelectedBorder.render(sb)
                }
            }

            sb.projectionMatrix = staticCam.combined
            leftButton.render(sb)
            rightButton.render(sb)
        }
    }
}