package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.distraction.fs2.*
import com.distraction.fs2.states.MoveListener
import com.distraction.fs2.tilemap.Tile
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.TileObject
import com.distraction.fs2.tilemap.tileobjects.Player.Direction.*

class Player(context: Context, tileMap: TileMap, private val moveListener: MoveListener?) : TileObject(context, tileMap) {

    enum class Direction {
        UP,
        RIGHT,
        LEFT,
        DOWN
    }

    private val animationSet = AnimationSet()

    private val speed = 32f * 2
    private val jumpHeight = 20f
    private var totalDist = 0f
    private var moving = false
    private var sliding = false
    private var superjump = false
    var teleporting = false
    private var justTeleported = false
    private var teleportSpeed = 0f
    private var direction = RIGHT

    init {
        setTile(tileMap.levelData.startRow, tileMap.levelData.startCol)
        p.z = 4f
        pdest.set(p)

        animationSet.addAnimation("idle", Animation(context.assets.getAtlas().findRegion("playeridle").split(16, 18)[0], 1f / 2f))
        animationSet.addAnimation("idler", Animation(context.assets.getAtlas().findRegion("playeridler").split(16, 18)[0], 1f / 2f))
        animationSet.addAnimation("jump", Animation(context.assets.getAtlas().findRegion("playerjump").split(13, 18)[0], -1f))
        animationSet.addAnimation("jumpr", Animation(context.assets.getAtlas().findRegion("playerjumpr").split(13, 18)[0], -1f))
        animationSet.addAnimation("crouch", Animation(context.assets.getAtlas().findRegion("playercrouch").split(16, 18)[0], 1f / 10f))
        animationSet.addAnimation("crouchr", Animation(context.assets.getAtlas().findRegion("playercrouchr").split(16, 18)[0], 1f / 10f))

        animationSet.setAnimation("idle")
    }

    override fun setTile(row: Int, col: Int) {
        super.setTile(row, col)
        tileMap.getTile(row, col).toggleActive()
    }

    fun moveTile(rowdx: Int, coldx: Int) {
        if (moving) return
        if (!superjump && !tileMap.isValidTile(row + rowdx, col + coldx)) return
        if (!teleporting) {
            when {
                coldx > 0 -> direction = RIGHT
                coldx < 0 -> direction = LEFT
                rowdx > 0 -> direction = DOWN
                rowdx < 0 -> direction = UP
            }
        }
        row += rowdx
        col += coldx
        tileMap.toPosition(row, col, pdest)
        totalDist = getRemainingDistance()
        moving = true
        justTeleported = false
    }

    private fun getRemainingDistance() = Utils.dist(pdest.x, pdest.y, p.x, p.y)

    private fun atDestination() = p.x == pdest.x && p.y == pdest.y

    private fun addTileLight() {
        if (tileMap.getTile(row, col).active) {
            tileMap.otherObjects.add(TileLight(context, tileMap, row, col))
        }
    }

    private fun handleJustMoved(tile: Tile) {
        if (moving) {
            moveListener?.onMoved()
            if (!tileMap.isFinished()) {
                tile.toggleActive()
                moveListener?.onToggled()
            }
            sliding = false
            superjump = false
            moving = false
            if (atDestination()) {
                teleporting = false
            }
            addTileLight()
        }
    }

    private fun handleTileObjects(tile: Tile) {
        tile.objects.forEach {
            when {
                it is Arrow -> {
                    sliding = true
                    direction = it.direction
                }
                it is SuperJump -> {
                    superjump = true
                }
                it is Teleport && !justTeleported -> {
                    teleporting = true
                    if (it.row == row && it.col == col) {
                        teleportSpeed = Utils.max(Utils.abs(p.y - tileMap.toPosition(it.row2)), Utils.abs(p.x - tileMap.toPosition(it.col2))) * 1.5f
                        moveTile(it.row2 - it.row, it.col2 - it.col)
                    } else {
                        teleportSpeed = Utils.max(Utils.abs(p.y - tileMap.toPosition(it.row)), Utils.abs(p.x - tileMap.toPosition(it.col))) * 1.5f
                        moveTile(it.row - it.row2, it.col - it.col2)
                    }
                    justTeleported = true
                }
            }
        }

        if (sliding || superjump) {
            val dist2 = if (superjump) 2 else 1
            var r = 0
            var c = 0
            when (direction) {
                UP -> r = -dist2
                LEFT -> c = -dist2
                RIGHT -> c = dist2
                DOWN -> r = dist2
            }
            moving = false
            if (superjump) {
                sliding = false
            }
            moveTile(r, c)
        }
    }

    private fun updateBounceHeight() {
        if (sliding) {
            p.z = 4f
        } else {
            p.z = 4f + (jumpHeight * (if (superjump) 2 else 1) * MathUtils.sin(3.14f * getRemainingDistance() / totalDist))
        }
    }

    private fun updateAnimations(dt: Float) {
        if (sliding) {
            animationSet.setAnimation(if (direction == RIGHT || direction == DOWN) "crouch" else "crouchr")
        } else if (p.x == pdest.x && p.y == pdest.y) {
            if ((animationSet.currentAnimationKey.equals("jump") || animationSet.currentAnimationKey.equals("jumpr"))) {
                animationSet.setAnimation(if (direction == RIGHT || direction == DOWN) "crouch" else "crouchr")
            } else if (animationSet.currentAnimation!!.hasPlayedOnce()) {
                animationSet.setAnimation(if (direction == RIGHT || direction == DOWN) "idle" else "idler")
            }
        } else {
            if ((animationSet.currentAnimationKey.equals("idle") || animationSet.currentAnimationKey.equals("idler"))) {
                animationSet.setAnimation(if (direction == RIGHT || direction == DOWN) "crouch" else "crouchr")
            } else {
                animationSet.setAnimation(if (direction == RIGHT || direction == DOWN) "jump" else "jumpr")
            }
        }
        animationSet.update(dt)
    }

    override fun update(dt: Float) {
        moveToDest((if (teleporting && justTeleported) teleportSpeed else speed) * dt * (if (sliding) 4f else if (superjump) 2f else 1f))

        if (atDestination()) {
            if (!tileMap.contains(row, col)) {
                moveListener?.onIllegal()
                return
            }
            val tile = tileMap.getTile(row, col)

            handleJustMoved(tile)
            handleTileObjects(tile)
        }

        updateBounceHeight()
        updateAnimations(dt)
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, pp)
        if (!teleporting) {
            if (direction == RIGHT || direction == UP) {
                sb.draw(animationSet.getImage(), pp.x - animationSet.getImage()!!.regionWidth / 2, pp.y - animationSet.getImage()!!.regionHeight / 2 + p.z)
            } else {
                sb.draw(
                        animationSet.getImage(),
                        pp.x + animationSet.getImage()!!.regionWidth / 2,
                        pp.y - animationSet.getImage()!!.regionHeight / 2 + p.z,
                        -animationSet.getImage()!!.regionWidth * 1f,
                        animationSet.getImage()!!.regionHeight * 1f)
            }
        }
    }
}