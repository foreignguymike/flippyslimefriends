package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.tileobjects.Player

class TestState(context: Context, private val level: Int = 0) : GameState(context),
        Player.MoveListener,
        ButtonListener,
        TileMap.TileListener {

    private val tileMap = TileMap(context, this, level)

    private val player: Player = Player(context, tileMap, this)

    private val cameraOffset = Vector2(0f, 0f)

    private val pixel = context.assets.getAtlas().findRegion("pixel")
    private val pixelp = Vector3()
    private val hud = HUD(context, this)

    init {
        camera.position.set(player.isop.x, player.isop.y, 0f)
        camera.update()
    }

    override fun onMoved() {

    }

    override fun onIllegal() {

    }

    override fun onTileToggled(tileMap: TileMap) {
        if (tileMap.isFinished()) {
            context.gsm.pop()
            context.gsm.push(TestState(context, level + 1))
        }
    }

    private fun handleInput() {
        when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> player.moveTile(0, 1)
            Gdx.input.isKeyPressed(Input.Keys.LEFT) -> player.moveTile(0, -1)
            Gdx.input.isKeyPressed(Input.Keys.UP) -> player.moveTile(-1, 0)
            Gdx.input.isKeyPressed(Input.Keys.DOWN) -> player.moveTile(1, 0)
            Gdx.input.isKeyJustPressed(Input.Keys.R) -> onIllegal()
        }
    }

    override fun update(dt: Float) {
        handleInput()

        tileMap.update(dt)
        player.update(dt)
        camera.position.set(camera.position.lerp(player.isop.x + cameraOffset.x, player.isop.y + cameraOffset.y, 0f, 0.1f))
        camera.update()
        hud.update(dt)
    }

    override fun render(sb: SpriteBatch) {
        clearScreen(160, 230, 255, 255)
        sb.projectionMatrix = camera.combined
        sb.use {
            tileMap.render(sb)
            player.render(sb)
            tileMap.renderOther(sb)
            hud.render(sb)
        }
    }

    override fun onButtonPressed(type: ButtonListener.ButtonType) {
        when (type) {
            ButtonListener.ButtonType.UP -> player.moveTile(-1, 0)
            ButtonListener.ButtonType.LEFT -> player.moveTile(0, -1)
            ButtonListener.ButtonType.DOWN -> player.moveTile(1, 0)
            ButtonListener.ButtonType.RIGHT -> player.moveTile(0, 1)
            ButtonListener.ButtonType.RESTART -> onIllegal()
            ButtonListener.ButtonType.BACK -> {
            }
        }
    }
}