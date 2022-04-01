package com.distraction.fs2.tilemap.tileobjects.player

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.distraction.fs2.drawHFlip

abstract class Accessory(val player: Player) {
    protected val offset = Vector2(0f, 15f)
    protected val animationSet get() = player.playerRenderer.animationSet
    open fun update(dt: Float) {}
    open fun renderBehind(sb: SpriteBatch) {}
    open fun renderFront(sb: SpriteBatch) {}
}

class HeadBubble(player: Player) : Accessory(player) {

    val image = player.context.getImage("head_bubble")

    init { offset.x = -10f }

    override fun update(dt: Float) {
        when (animationSet.currentAnimationKey) {
            Player.IDLE, Player.IDLER -> offset.y =
                if (animationSet.currentAnimation.currentFrame() == 0) 18f else 17f
            Player.CROUCH, Player.CROUCHR -> offset.y = 12f
            Player.JUMP, Player.JUMPR -> offset.y = 22f
        }
    }

    override fun renderBehind(sb: SpriteBatch) {
        if (player.right) {
            sb.draw(image, player.isop.x + offset.x, player.isop.y + player.p.z + offset.y)
        } else {
            sb.drawHFlip(image, player.isop.x - offset.x, player.isop.y + player.p.z + offset.y)
        }
    }
}

class SantaHat(player: Player) : Accessory(player) {
    val image = player.context.getImage("santa_hat")
    val imageR = player.context.getImage("santa_hat_r")

    init { offset.x = -17f }

    override fun update(dt: Float) {
        when (animationSet.currentAnimationKey) {
            Player.IDLE, Player.IDLER -> offset.y =
                if (animationSet.currentAnimation.currentFrame() == 0) 14f else 13f
            Player.CROUCH, Player.CROUCHR -> offset.y = 12f
            Player.JUMP, Player.JUMPR -> offset.y = 18f
        }
    }

    override fun renderBehind(sb: SpriteBatch) {
        if (!player.forward) {
            if (player.right) {
                sb.draw(imageR, player.isop.x + offset.x, player.isop.y + player.p.z + offset.y)
            } else {
                sb.drawHFlip(imageR, player.isop.x - offset.x, player.isop.y + player.p.z + offset.y)
            }
        }
    }

    override fun renderFront(sb: SpriteBatch) {
        if (player.forward) {
            if (player.right) {
                sb.draw(image, player.isop.x + offset.x, player.isop.y + player.p.z + offset.y)
            } else {
                sb.drawHFlip(image, player.isop.x - offset.x, player.isop.y + player.p.z + offset.y)
            }
        }
    }
}