package com.distraction.fs2

import com.badlogic.gdx.assets.AssetManager
import com.distraction.fs2.states.GSM

class Context {
    lateinit var assets: AssetManager
    lateinit var gsm: GSM
    lateinit var scoreHandler: ScoreHandler
}