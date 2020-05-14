package com.distraction.fs2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import kotlin.math.sqrt

class Utils {
    companion object {
        fun abs(f: Float) = if (f < 0) f * -1 else f
        fun dist(x1: Float, y1: Float, x2: Float, y2: Float) = sqrt(1.0 * (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toFloat()
        fun max(f1: Float, f2: Float) = if (f2 > f1) f2 else f1
    }
}

fun AssetManager.getAtlas(): TextureAtlas = get("fs2.atlas", TextureAtlas::class.java)

fun log(str: Any) = Gdx.app.log("FS2", str.toString())

inline fun SpriteBatch.use(action: () -> Unit) {
    begin()
    action()
    end()
}

fun SpriteBatch.draw(textureRegion: TextureRegion, v: Vector3) {
    draw(textureRegion, v.x, v.y)
}

fun SpriteBatch.draw(textureRegion: TextureRegion, v: Vector3, w: Float, h: Float) {
    draw(textureRegion, v.x, v.y, w, h)
}

fun SpriteBatch.drawPadded(textureRegion: TextureRegion, x: Float, y: Float, padding: Float = 0.01f) {
    draw(textureRegion, x, y, textureRegion.regionWidth + padding, textureRegion.regionHeight + padding)
}

fun SpriteBatch.drawHFlip(textureRegion: TextureRegion, x: Float, y: Float) {
    draw(textureRegion, x, y, -textureRegion.regionWidth.toFloat(), textureRegion.regionHeight.toFloat())
}

fun SpriteBatch.drawVFlip(textureRegion: TextureRegion, x: Float, y: Float) {
    draw(textureRegion, x, y, textureRegion.regionWidth.toFloat(), -textureRegion.regionHeight.toFloat())
}

fun SpriteBatch.drawVHFlip(textureRegion: TextureRegion, x: Float, y: Float) {
    draw(textureRegion, x, y, -textureRegion.regionWidth.toFloat(), -textureRegion.regionHeight.toFloat())
}

fun SpriteBatch.drawRotated(textureRegion: TextureRegion, x: Float, y: Float, degrees: Float) {
    draw(textureRegion,
            x,
            y,
            textureRegion.regionWidth / 2f,
            textureRegion.regionHeight / 2f,
            1f * textureRegion.regionWidth,
            1f * textureRegion.regionHeight,
            1f,
            1f,
            degrees)
}

fun SpriteBatch.drawButton(button: Button, hflip: Boolean = false) {
    if (hflip) {
        draw(button.image, button.rect.x + button.image.regionWidth, button.rect.y, -button.rect.width, button.rect.height)
    } else {
        draw(button.image, button.rect.x, button.rect.y, button.rect.width, button.rect.height)
    }
}

// exclusive
infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to - step, step)
}

fun clearScreen(r: Int = 0, g: Int = 0, b: Int = 0, a: Int = 0) {
    Gdx.gl.glClearColor(r / 255f, g / 255f, b / 255f, a / 255f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

fun Vector3.lerp(x: Float, y: Float, z: Float, amount: Float): Vector3 {
    this.x += amount * (x - this.x)
    this.y += amount * (y - this.y)
    this.z += amount * (z - this.z)
    return this
}

fun Rectangle.contains(v: Vector3) = contains(v.x, v.y)

fun Vector3.moveTo(pdest: Vector3, dist: Float) {
    if (x < pdest.x) {
        x += dist
        if (x > pdest.x) {
            x = pdest.x
        }
    }
    if (x > pdest.x) {
        x -= dist
        if (x < pdest.x) {
            x = pdest.x
        }
    }
    if (y < pdest.y) {
        y += dist
        if (y > pdest.y) {
            y = pdest.y
        }
    }
    if (y > pdest.y) {
        y -= dist
        if (y < pdest.y) {
            y = pdest.y
        }
    }
}