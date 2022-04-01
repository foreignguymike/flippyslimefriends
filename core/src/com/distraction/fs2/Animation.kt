package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animation(
    private val sprites: Array<TextureRegion>,
    private val delay: Float = 1f / 60,
    private val repeatCount: Int = -1
) {

    private var time = 0f
    private var frameIndex = 0
    private var playCount = 0

    fun currentFrame() = frameIndex

    fun reset() {
        time = 0f
        frameIndex = 0
        playCount = 0
    }

    fun update(dt: Float) {
        if (playCount == repeatCount) {
            return
        }
        if (delay < 0) {
            return
        }
        time += dt
        while (time >= delay) {
            time -= delay
            frameIndex++
            if (frameIndex >= sprites.size) {
                playCount++
                frameIndex = when {
                    playCount != repeatCount -> 0
                    else -> sprites.size - 1
                }
            }
        }
    }

    fun getImage() = sprites[frameIndex]

    fun hasPlayedOnce() = playCount > 0

}