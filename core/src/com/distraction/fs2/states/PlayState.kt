package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.Area
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.TileMapData
import com.distraction.fs2.tilemap.tileobjects.Player

class PlayState(context: Context, private val area: Area, private val level: Int) :
    GameState(context), Player.MoveListener,
    ButtonListener, TileMap.TileListener {

    private val tileMap = TileMap(context, this, area, level - 1)
    private val players = tileMap.mapData.playerPositions.map {
        Player(context, tileMap, this, it.row, it.col)
    }
    private val player = players.first()
    private val bg = Background(context)
    private val bgCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    private val hud = HUD(context, this)
    private val cameraOffset = Vector2(0f, 0f)

    init {
        camera.position.set(-100f, player.isop.y + cameraOffset.y, 0f)
        camera.update()

        hud.setGoal(TileMapData.levelData[level - 1].goal)
        hud.setBest(context.scoreHandler.scores[level - 1])
    }

    override fun onMoved() {
        if (!tileMap.isFinished()) {
            hud.incrementMoves()
        }
    }

    override fun onTileToggled(tileMap: TileMap) {
        if (tileMap.isFinished()) {
            ignoreInput = true
            if (hud.getBest() == 0 || hud.getMoves() < hud.getBest()) {
                context.scoreHandler.saveScore(level - 1, hud.getMoves())
            }
            context.gsm.push(LevelFinishState(context, area, level, hud.getMoves(), hud.getBest()))
        }
    }

    override fun onIllegal() {
        if (!tileMap.isFinished() && !ignoreInput) {
            ignoreInput = true
            context.gsm.push(TransitionState(context, PlayState(context, area, level)))
        }
    }

    private fun back() {
        if (!ignoreInput) {
            ignoreInput = true
            context.gsm.push(
                TransitionState(
                    context,
                    LevelSelectState(context, area, (level - 1) / LevelSelectState.LEVELS_PER_PAGE)
                )
            )
        }
    }

    override fun onButtonPressed(type: ButtonListener.ButtonType) {
        when (type) {
            ButtonListener.ButtonType.UP -> player.moveTile(-1, 0)
            ButtonListener.ButtonType.LEFT -> player.moveTile(0, -1)
            ButtonListener.ButtonType.DOWN -> player.moveTile(1, 0)
            ButtonListener.ButtonType.RIGHT -> player.moveTile(0, 1)
            ButtonListener.ButtonType.RESTART -> onIllegal()
            ButtonListener.ButtonType.BACK -> back()
        }
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            hud.update(dt)
            when {
                Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> player.moveTile(0, 1)
                Gdx.input.isKeyPressed(Input.Keys.LEFT) -> player.moveTile(0, -1)
                Gdx.input.isKeyPressed(Input.Keys.UP) -> player.moveTile(-1, 0)
                Gdx.input.isKeyPressed(Input.Keys.DOWN) -> player.moveTile(1, 0)
                Gdx.input.isKeyJustPressed(Input.Keys.R) -> onIllegal()
            }
        }

        player.update(dt)

//        if (player.teleporting) {
//            tileMap.toIsometric(player.pdest.x, player.pdest.y, tp)
//            camera.position.set(camera.position.lerp(tp.x + cameraOffset.x, tp.y + cameraOffset.y, 0f, 0.1f))
//        } else {
        camera.position.set(
            camera.position.lerp(
                player.isop.x + cameraOffset.x,
                player.isop.y + cameraOffset.y,
                0f,
                0.1f
            )
        )
//        }
        camera.update()

        bg.update(dt)
        tileMap.update(dt)
    }

    override fun render(sb: SpriteBatch) {
        sb.use {
            sb.projectionMatrix = bgCam.combined
            bg.render(sb)

            sb.projectionMatrix = camera.combined
            tileMap.render(sb)
            player.render(sb)
            tileMap.renderOther(sb)

            hud.render(sb)
        }
    }
}