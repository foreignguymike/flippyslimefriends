package com.distraction.fs2.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Constants
import com.distraction.fs2.Context

abstract class GameState(protected val context: Context) {
    protected val pixel = context.getImage("pixel")
    var ignoreInput = false
    val touchPoint = Vector3()
    protected val camera = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    fun unprojectTouch(camera: Camera = this.camera) {
        touchPoint.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
        camera.unproject(touchPoint)
    }

    abstract fun update(dt: Float)
    abstract fun render(sb: SpriteBatch)
}