package com.distraction.fs2.states

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.distraction.fs2.Constants
import com.distraction.fs2.Context

abstract class GameState(protected val context: Context) {
    var ignoreInput = false
    val touchPoint = Vector3()
    protected val camera = OrthographicCamera().apply {
        setToOrtho(false, Constants.WIDTH, Constants.HEIGHT)
    }

    abstract fun update(dt: Float)
    abstract fun render(sb: SpriteBatch)
}