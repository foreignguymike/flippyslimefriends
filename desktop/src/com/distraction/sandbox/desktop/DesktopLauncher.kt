package com.distraction.sandbox.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.distraction.fs2.Constants
import com.distraction.fs2.FlippySlime2

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = Constants.DESKTOP_WIDTH
        config.height = Constants.DESKTOP_HEIGHT
        LwjglApplication(FlippySlime2(), config)
    }
}
