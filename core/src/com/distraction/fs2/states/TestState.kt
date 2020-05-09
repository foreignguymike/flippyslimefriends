package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.tileobjects.Player

class TestState(context: Context) : GameState(context), Player.MoveListener {

    private val tileMap = TileMap(context, 0)

    private val player: Player = Player(context, tileMap, this)

    private val cameraOffset = Vector2(0f, 0f)

    private val pixel = context.assets.getAtlas().findRegion("pixel")
    private val pixelp = Vector3()

    init {
        camera.translate(-100f, -100f)
        camera.update()
    }

    override fun onMoved() {

    }

    override fun onToggled() {

    }

    override fun onIllegal() {

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
    }

    override fun render(sb: SpriteBatch) {
        sb.projectionMatrix = camera.combined
        sb.use {
            tileMap.render(sb)
            player.render(sb)

            tileMap.toIsometric(60f, 62f, pixelp)
            sb.color = Color.RED
            sb.draw(pixel, pixelp, 4f, 4f)
            sb.color = Color.WHITE
        }
    }
}