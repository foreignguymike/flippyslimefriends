package com.distraction.fs2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import kotlin.math.absoluteValue
import kotlin.math.sqrt

class Utils {
    companion object {
        fun max(f1: Float, f2: Float) = if (f2 > f1) f2 else f1
        fun dist(x1: Float, y1: Float, x2: Float, y2: Float) =
            sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
    }
}

/**
 * Actual mod operation. Kotlin doesn't have this...
 */
fun Int.pmod(other: Int): Int {
    var p = this.rem(other)
    if (p < 0) {
        p += other
    }
    return p
}

/**
 * Map in place
 */
fun <T> MutableList<T>.mapInPlace(mapper: (T) -> (T)) {
    forEachIndexed { index, value ->
        this[index] = mapper(value)
    }
}

fun AssetManager.getAtlas(): TextureAtlas = get("fs2.atlas", TextureAtlas::class.java)

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

fun SpriteBatch.drawAlpha(textureRegion: TextureRegion, x: Float, y: Float, alpha: Float) {
    val currentAlpha = color.a
    setColor(color.r, color.g, color.b, alpha)
    draw(textureRegion, x, y)
    setColor(color.r, color.g, color.b, currentAlpha)
}

fun SpriteBatch.drawPadded(
    textureRegion: TextureRegion,
    x: Float,
    y: Float,
    padding: Float = 0.01f
) {
    draw(
        textureRegion,
        x,
        y,
        textureRegion.regionWidth + padding,
        textureRegion.regionHeight + padding
    )
}

fun SpriteBatch.drawCentered(
    image: TextureRegion,
    x: Float,
    y: Float,
    width: Float = image.regionWidth.toFloat(),
    height: Float = image.regionHeight.toFloat()
) =
    draw(image, x - image.regionWidth / 2, y - image.regionHeight / 2, width, height)


fun SpriteBatch.drawHFlip(
    textureRegion: TextureRegion,
    x: Float,
    y: Float,
    w: Float = textureRegion.regionWidth.toFloat(),
    h: Float = textureRegion.regionHeight.toFloat()
) {
    draw(textureRegion, x, y, -w, h)
}

fun SpriteBatch.drawVFlip(textureRegion: TextureRegion, x: Float, y: Float) {
    draw(
        textureRegion,
        x,
        y,
        textureRegion.regionWidth.toFloat(),
        -textureRegion.regionHeight.toFloat()
    )
}

fun SpriteBatch.drawVHFlip(textureRegion: TextureRegion, x: Float, y: Float) {
    draw(
        textureRegion,
        x,
        y,
        -textureRegion.regionWidth.toFloat(),
        -textureRegion.regionHeight.toFloat()
    )
}

fun SpriteBatch.drawRotated(
    image: TextureRegion,
    x: Float,
    y: Float,
    degrees: Float,
    originx: Float = image.regionWidth / 2f,
    originy: Float = image.regionHeight / 2f,
    scale: Float = 1f
) {
    draw(
        image,
        x - image.regionWidth / 2f,
        y - image.regionHeight / 2f,
        originx,
        originy,
        1f * image.regionWidth,
        1f * image.regionHeight,
        scale,
        scale,
        degrees
    )
}

fun SpriteBatch.resetColor() {
    setColor(1f, 1f, 1f, 1f)
}

fun SpriteBatch.setColor(color: Color, a: Float) {
    setColor(color.r, color.g, color.b, a)
}

fun SpriteBatch.setAlpha(a: Float) {
    val c = color
    setColor(c.r, c.g, c.b, a)
}

fun clearScreen(r: Int = 0, g: Int = 0, b: Int = 0, a: Int = 0) {
    Gdx.gl.glClearColor(r / 255f, g / 255f, b / 255f, a / 255f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

fun clearScreen(color: Color) {
    Gdx.gl.glClearColor(color.r, color.g, color.b, color.a)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

fun Vector3.lerp(x: Float, y: Float, z: Float, amount: Float = 0.1f): Vector3 {
    this.x += amount * (x - this.x)
    this.y += amount * (y - this.y)
    this.z += amount * (z - this.z)
    return this
}

fun Vector2.diff(v2: Vector2) = (this.x - v2.x).absoluteValue + (this.y - v2.y).absoluteValue

fun Rectangle.contains(v: Vector3) = contains(v.x, v.y)

/**
 * Move this toward pdest at dist amount.
 * The dist is applied to x and y individually.
 */
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

/**
 * Move this toward pdest at dist amount.
 * The dist is applied to the direction of pdest.
 */
fun Vector3.moveToLinear(pdest: Vector3, dist: Float) {
    val dx = pdest.x - x
    val dy = pdest.y - y
    val totalDist = sqrt(dx * dx + dy * dy)
    val tx = (totalDist - dist) * (pdest.x - x) / totalDist
    val ty = (totalDist - dist) * (pdest.y - y) / totalDist
    if (x < pdest.x) {
        x += tx
        if (x > pdest.x) {
            x = pdest.x
        }
    }
    if (x > pdest.x) {
        x += tx
        if (x < pdest.x) {
            x = pdest.x
        }
    }
    if (y < pdest.y) {
        y += ty
        if (y > pdest.y) {
            y = pdest.y
        }
    }
    if (y > pdest.y) {
        y += ty
        if (y < pdest.y) {
            y = pdest.y
        }
    }
}

fun Float.map(A: Float, B: Float, C: Float, D: Float) = (this - A) / (B - A) * (D - C) + C