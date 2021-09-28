package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch

fun Char.intValue(): Int = if (this !in '0'..'9') throw NumberFormatException() else toInt() - '0'.toInt()

class NumberFont(context: Context, private var centerAlign: Boolean = false) {
    private val images = Array(10) {
        context.getImage(it.toString())
    }
    private val nan = context.getImage("-")
    private var length = 0

    var num = 0
    set(value) {
        if (num < 0) {
            field = value
            length = nan.regionWidth
            return
        }
        val s = value.toString()
        length = 0
        for (c in s) {
            val n = c.intValue()
            length += images[n].regionWidth
        }
        field = value
    }

    fun render(sb: SpriteBatch, x: Float, y: Float, num: Int = this.num) {
        if (num < 0) {
            if (centerAlign) {
                sb.draw(nan, x - length / 2, y - nan.regionHeight / 2)
            } else {
                sb.draw(nan, x, y - nan.regionHeight / 2)
            }
            return
        }
        val s = num.toString()
        var offset = 0
        if (centerAlign) {
            for (c in s) {
                val n = c.intValue()
                sb.draw(images[n], x + offset - length / 2, y - images[n].regionHeight / 2)
                offset += images[n].regionWidth
            }
        } else {
            for (c in s) {
                val n = c.intValue()
                sb.draw(images[n], x + offset, y - images[n].regionHeight / 2)
                offset += images[n].regionWidth
            }
        }
    }
}