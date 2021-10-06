package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.data.Area
import com.distraction.fs2.tilemap.tileobjects.Player

class PlayState(context: Context, private val area: Area, private val level: Int) :
    GameState(context), Player.MoveListener,
    ButtonListener, TileMap.TileListener {

    private val tileMap = TileMap(context, this, area, level)

    private val players = tileMap.mapData.playerPositions.map {
        Player(context, tileMap, this, it.row, it.col)
    }.also { players -> players.forEach { it.players = players } }
    private var playerIndex = 0
    set(value) {
        field = value
        player = players[playerIndex]
    }
    private var player = players[0]
    private val sortedPlayers = players.toMutableList()

    private val bg = Background(context)
    private val bgCam = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    private val hud = HUD(context, level, this)
    private val cameraOffset = Vector2(0f, HUD.HEIGHT / 2f)

    init {
        camera.position.set(-100f, player.isop.y + cameraOffset.y, 0f)
        camera.update()

        hud.setTarget(tileMap.mapData.target)
        context.scoreHandler.scores[area]?.let {
            hud.setBest(it[level])
        }
    }

    override fun onMoved() {
        if (!tileMap.isFinished()) {
            hud.incrementMoves()
        }
    }

    override fun onTileToggled(tileMap: TileMap) {
        if (tileMap.isFinished()) {
            ignoreInput = true
            hud.hideInfo = true
            if (hud.getBest() < 0 || hud.getMoves() < hud.getBest()) {
                context.scoreHandler.saveScore(area, level, hud.getMoves())
                hud.setBest(hud.getMoves())
            }
            context.gsm.push(
                LevelFinishState(
                    context,
                    area,
                    level,
                    hud.getMoves(),
                    hud.getBest(),
                    hud.getGoal()
                )
            )
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
                    LevelSelectState(context, area, level)
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

    private fun handleInput() {
        unprojectTouch()
        hud.handleInput()
        when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> player.moveTile(0, 1)
            Gdx.input.isKeyPressed(Input.Keys.LEFT) -> player.moveTile(0, -1)
            Gdx.input.isKeyPressed(Input.Keys.UP) -> player.moveTile(-1, 0)
            Gdx.input.isKeyPressed(Input.Keys.DOWN) -> player.moveTile(1, 0)
            Gdx.input.isKeyJustPressed(Input.Keys.R) -> onIllegal()
            Gdx.input.isKeyJustPressed(Input.Keys.A) -> playerIndex = (playerIndex - 1).pmod(players.size)
            Gdx.input.isKeyJustPressed(Input.Keys.D) -> playerIndex = (playerIndex + 1) % players.size
        }
    }

    override fun update(dt: Float) {
        if (!ignoreInput) {
            handleInput()
        }

        players.forEach { it.update(dt) }
        sortedPlayers.sortByDescending { it.isop.y }

        camera.position.set(
            camera.position.lerp(
                player.isop.x + cameraOffset.x,
                player.isop.y + cameraOffset.y,
                0f,
                0.1f
            )
        )
        camera.update()

        bg.update(dt)
        tileMap.update(dt)
        hud.update()
    }

    override fun render(sb: SpriteBatch) {
        sb.use {
            sb.projectionMatrix = bgCam.combined
            bg.render(sb)

            sb.projectionMatrix = camera.combined
            tileMap.render(sb)
            sortedPlayers.forEach { it.render(sb) }
            tileMap.renderOther(sb)

            hud.render(sb)
        }
    }
}