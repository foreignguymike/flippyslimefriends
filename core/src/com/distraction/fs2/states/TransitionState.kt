package com.distraction.fs2.states

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.Constants
import com.distraction.fs2.Context
import com.distraction.fs2.getAtlas
import com.distraction.fs2.use

class TransitionState(context: Context, private val nextState: GameState, private val numPop: Int = 1) : GameState(context) {

    private val duration = 0.5f
    private var time = 0f
    private var next = false

    init {
        context.gsm.depth++
    }

    override fun update(dt: Float) {
        time += dt
        if (!next && time > duration / 2) {
            next = true
            nextState.ignoreInput = true
            for (i in 0 until numPop) {
                context.gsm.pop()
            }
            context.gsm.depth -= numPop - 1
            context.gsm.replace(nextState)
            context.gsm.push(this)
        }
        if (time > duration) {
            context.gsm.depth--
            context.gsm.pop()
            nextState.ignoreInput = false
        }
    }

    override fun render(sb: SpriteBatch) {
        val interp = time / duration
        val perc = if (interp < 0.5f) interp * 2f else 1f - (time - duration / 2) / duration * 2
        val c = sb.color
        sb.color = Color.BLACK
        sb.projectionMatrix = camera.combined
        sb.use {
            sb.draw(pixel, 0f, Constants.HEIGHT, 1f * Constants.WIDTH, -perc * Constants.HEIGHT / 2)
            sb.draw(pixel, 0f, 0f, 1f * Constants.WIDTH, perc * Constants.HEIGHT / 2)
        }
        sb.color = c
    }
}