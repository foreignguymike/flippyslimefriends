package com.distraction.fs2.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.distraction.fs2.Constants
import com.distraction.fs2.FlippySlime2

class DesktopLauncherOld {
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.forceExit = false
        config.width = Constants.DESKTOP_WIDTH
        config.height = Constants.DESKTOP_HEIGHT
        LwjglApplication(FlippySlime2(), config)
    }
}
