package com.distraction.fs2

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.distraction.fs2.states.GSM
import com.distraction.fs2.states.PlayState
import com.distraction.fs2.states.TitleState

class FlippySlime2 : ApplicationAdapter() {
    private lateinit var context: Context
    private lateinit var sb: SpriteBatch
    private lateinit var gsm: GSM

    override fun create() {
        context = Context()
        gsm = context.gsm
        sb = SpriteBatch()
        gsm.push(TitleState(context))
    }

    override fun render() {
        clearScreen()
        gsm.update(Gdx.graphics.deltaTime)
        gsm.render(sb)
    }

    override fun dispose() {
        sb.dispose()
        context.assets.dispose()
    }
}
