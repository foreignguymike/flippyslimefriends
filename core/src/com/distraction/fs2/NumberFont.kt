package com.distraction.fs2

import com.badlogic.gdx.graphics.g2d.SpriteBatch

fun Char.intValue(): Int =
    if (this !in '0'..'9') throw NumberFormatException() else toInt() - '0'.toInt()

class NumberFont(
    context: Context,
    private var centerAlign: Boolean = false,
    size: NumberSize = NumberSize.MEDIUM
) {
    enum class NumberSize {
        MEDIUM,
        LARGE
    }

    private val images = Array(10) {
        when (size) {
            NumberSize.MEDIUM -> context.getImage("num2", it)
            NumberSize.LARGE -> context.getImage("num3", it)
        }
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
                if (c != '-') {
                    val n = c.intValue()
                    length += images[n].regionWidth + 1
                }
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
                offset += images[n].regionWidth + 1
            }
        } else {
            for (c in s) {
                val n = c.intValue()
                sb.draw(images[n], x + offset, y - images[n].regionHeight / 2)
                offset += images[n].regionWidth + 1
            }
        }
    }
}