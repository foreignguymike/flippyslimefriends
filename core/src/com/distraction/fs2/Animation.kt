package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animation(
        private val sprites: Array<TextureRegion>,
        private val delay: Float = 1f/60) {

    private var time = 0f
    private var frameIndex = 0
    private var playCount = 0

    fun reset() {
        time = 0f
        frameIndex = 0
        playCount = 0
    }

    fun update(dt: Float) {
        if (delay < 0) {
            return
        }
        time += dt
        while (time >= delay) {
            time -= delay
            frameIndex++
            if (frameIndex >= sprites.size) {
                frameIndex = 0
                playCount++
            }
        }
    }

    fun getImage() = sprites[frameIndex]

    fun hasPlayedOnce() = playCount > 0

}