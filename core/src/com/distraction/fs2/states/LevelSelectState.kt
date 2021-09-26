package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.data.Area

class LevelSelectState(
    context: Context,
    private val area: Area,
    currentLevel: Int = -1
) : GameState(context) {

    private val levelCheck = context.getImage("levelcheck")
    private val numRows = 3
    private val numCols = 6
    private val pageSize = numRows * numCols
    private val widthPadding = 120f
    private val heightPadding = 60f
    private val cellWidth = (Constants.WIDTH - 2 * widthPadding) / numCols
    private val cellHeight = (Constants.HEIGHT - 2 * heightPadding) / numRows
    private var page = currentLevel / pageSize
    private var levelData = context.gameData.mapData[area]
        ?: throw IllegalArgumentException("invalid area $area")
    private var numLevels = levelData.size
    private val maxPages = numLevels / pageSize

    private val levels = Array(numLevels) {
        val page = it / pageSize
        val row = it / numCols
        val col = it % numCols
        //val x = col * (levelIcon.regionWidth + 5f) + 38 + Constants.WIDTH * page
        //val y = Constants.HEIGHT - row * (levelIcon.regionHeight + 5f) - 50
        val x =
            widthPadding + col * cellWidth + cellWidth / 2 + Constants.WIDTH * page
        val y = Constants.HEIGHT - heightPadding - (row * cellHeight + cellHeight / 2)
        ImageButton(context.getImage("levelicon"), x, y)
    }

    private val numberFont = NumberFont(context, true)
    private val levelSelectImage = context.getImage("levelselect")
    private val backButton = ImageButton(context.getImage("back"), Constants.WIDTH / 2, 20f)
    private val disableColor = Color(0.3f, 0.3f, 0.3f, 1f)
    private val staticCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    private val leftButton = ImageButton(
        context.getImage("areaselectarrow"),
        50f, Constants.HEIGHT / 2, 10f
    )
    private val rightButton = ImageButton(
        context.getImage("areaselectarrow"),
        Constants.WIDTH - 50f, Constants.HEIGHT / 2 - 5f, 10f
    )

    init {
        camera.position.set(Constants.WIDTH * page + Constants.WIDTH / 2, Constants.HEIGHT / 2, 0f)
        camera.update()
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            if (Gdx.input.justTouched()) {
                unprojectTouch()
                levels.forEachIndexed { i, it ->
                    if (it.containsPoint(touchPoint.x, touchPoint.y) && i < numLevels) {
                        ignoreInput = true
                        context.gsm.push(TransitionState(context, PlayState(context, area, i + 1)))
                        return@forEachIndexed
                    }
                }

                touchPoint.set(1f * Gdx.input.x, 1f * Gdx.input.y, 0f)
                staticCam.unproject(touchPoint)
                if (backButton.containsPoint(touchPoint.x, touchPoint.y)) {
                    ignoreInput = true
                    context.gsm.push(
                        TransitionState(
                            context,
                            AreaSelectState(context, area.ordinal)
                        )
                    )
                }
                if (leftButton.containsPoint(touchPoint.x, touchPoint.y)) {
                    if (page > 0) {
                        page--
                    }
                } else if (rightButton.containsPoint(touchPoint.x, touchPoint.y)) {
                    if (page < maxPages - 1) {
                        page++
                    }
                }
            }
        }
        camera.position.set(
            camera.position.lerp(
                Constants.WIDTH * page + Constants.WIDTH / 2,
                Constants.HEIGHT / 2,
                0f,
                0.3f
            )
        )
        camera.update()
    }

    override fun render(sb: SpriteBatch) {
        clearScreen(76, 176, 219)
        sb.use {
            sb.projectionMatrix = staticCam.combined
            sb.draw(
                levelSelectImage,
                (Constants.WIDTH - levelSelectImage.regionWidth) / 2,
                Constants.HEIGHT - levelSelectImage.regionHeight - 8
            )
            backButton.render(sb)

            if (page > 0) leftButton.render(sb)
            if (page < maxPages - 1) rightButton.render(sb)

            sb.projectionMatrix = camera.combined
            levels.forEachIndexed { i, it ->
                val c = sb.color
                val best = context.scoreHandler.getScores(area)[i]
                if (best == 0) {
                    sb.color = disableColor
                }
                it.render(sb)
                numberFont.num = i + 1
                numberFont.render(
                    sb,
                    it.pos.x,
                    it.pos.y
                )
                sb.color = c
                if (best > 0 && best <= levelData[i].target) {
                    sb.draw(
                        levelCheck,
                        it.pos.x - levelCheck.regionWidth / 2,
                        it.pos.y - levelCheck.regionHeight / 2 + 14
                    )
                }
            }
        }
    }
}