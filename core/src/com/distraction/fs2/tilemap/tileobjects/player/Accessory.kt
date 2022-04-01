package com.distraction.fs2.tilemap.tileobjects.player

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.distraction.fs2.drawHFlip

abstract class Accessory(val player: Player) {
    protected val offset = Vector2(0f, 15f)
    protected val animationSet get() = player.playerRenderer.animationSet
    protected val isop get() = player.isop
    open fun update(dt: Float) {}
    open fun renderBehind(sb: SpriteBatch) {}
    open fun renderFront(sb: SpriteBatch) {}

    protected fun normalRender(
        sb: SpriteBatch,
        image: TextureRegion,
        right: Boolean = player.right
    ) {
        if (right) {
            sb.draw(image, isop.x + offset.x, isop.y + player.p.z + offset.y)
        } else {
            sb.drawHFlip(image, isop.x - offset.x, isop.y + player.p.z + offset.y)
        }
    }
}

class HeadBubble(player: Player) : Accessory(player) {

    val image = player.context.getImage("head_bubble")

    init {
        offset.x = -10f
    }

    override fun update(dt: Float) {
        when (animationSet.currentAnimationKey) {
            Player.IDLE, Player.IDLER -> offset.y =
                if (animationSet.currentAnimation.currentFrame() == 0) 18f else 17f
            Player.CROUCH, Player.CROUCHR -> offset.y = 12f
            Player.JUMP, Player.JUMPR -> offset.y = 22f
        }
    }

    override fun renderBehind(sb: SpriteBatch) {
        normalRender(sb, image)
    }
}

class SantaHat(player: Player) : Accessory(player) {
    private val image = player.context.getImage("santa_hat")
    private val imageR = player.context.getImage("santa_hat_r")

    init {
        offset.x = -17f
    }

    override fun update(dt: Float) {
        when (animationSet.currentAnimationKey) {
            Player.IDLE, Player.IDLER -> offset.y =
                if (animationSet.currentAnimation.currentFrame() == 0) 14f else 13f
            Player.CROUCH, Player.CROUCHR -> offset.y = 10f
            Player.JUMP, Player.JUMPR -> offset.y = 18f
        }
    }

    override fun renderBehind(sb: SpriteBatch) {
        if (!player.forward) normalRender(sb, imageR)
    }

    override fun renderFront(sb: SpriteBatch) {
        if (player.forward) normalRender(sb, image)
    }
}

class Fish(player: Player) : Accessory(player) {
    val image = player.context.getImage("fish")
    var time = 0f
    var right = false
    var cosx = 0f
    var siny = 0f
    var flipping = 0f

    override fun update(dt: Float) {
        time += dt
        cosx = MathUtils.cos(2 * time)
        siny = MathUtils.sin(2 * time)
        flipping = MathUtils.cos(2 * time + MathUtils.PI / 2f)
        offset.x = 30 * cosx
        offset.y = 10 * siny + 7
        right = siny < 0f
    }

    override fun renderBehind(sb: SpriteBatch) {
//        if (player.forward && !right) {
//            sb.drawHFlip(image, isop.x + offset.x + 12, isop.y + player.p.z + offset.y)
//        } else if (!player.forward && right) {
//            sb.draw(image, isop.x + offset.x, isop.y + player.p.z + offset.y)
//        }

        // this looks kinda better
        if (player.forward && !right || !player.forward && right) {
            sb.draw(
                image,
                isop.x + offset.x,
                isop.y + player.p.z + offset.y,
                image.regionWidth * flipping,
                image.regionHeight * 1f
            )
        }
    }

    override fun renderFront(sb: SpriteBatch) {
//        if (player.forward && right) {
//            sb.draw(image, isop.x + offset.x, isop.y + player.p.z + offset.y)
//        } else if (!player.forward && !right) {
//            sb.drawHFlip(image, isop.x + offset.x + 12, isop.y + player.p.z + offset.y)
//        }

        // this looks kinda better
        if (player.forward && right || !player.forward && !right) {
            sb.draw(
                image,
                isop.x + offset.x,
                isop.y + player.p.z + offset.y,
                image.regionWidth * flipping,
                image.regionHeight * 1f
            )
        }
    }
}