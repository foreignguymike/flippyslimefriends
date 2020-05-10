package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.Direction
import com.distraction.fs2.tilemap.Tile
import com.distraction.fs2.tilemap.TileMap

class Player(context: Context, tileMap: TileMap, private val moveListener: MoveListener?) : TileObject(context, tileMap), Tile.TileMoveListener {

    interface MoveListener {
        fun onMoved()
        fun onToggled()
        fun onIllegal()
    }

    private val animationSet = AnimationSet()

    private val speed = TileMap.TILE_SIZE * 2
    private val jumpHeight = 20f
    private var totalDist = 0f
    private var moving = false
    private var sliding = false
    private var superjump = false
    var teleporting = false
    private var justTeleported = false
    private var teleportSpeed = 0f
    private var direction = Direction.RIGHT

    init {
        setPositionFromTile(tileMap.mapData.startRow, tileMap.mapData.startCol)
        p.z = 4f
        pdest.set(p)

        currentTile = tileMap.getTile(row, col)

        animationSet.addAnimation("idle", Animation(context.assets.getAtlas().findRegion("playeridle").split(16, 18)[0], 1f / 2f))
        animationSet.addAnimation("idler", Animation(context.assets.getAtlas().findRegion("playeridler").split(16, 18)[0], 1f / 2f))
        animationSet.addAnimation("jump", Animation(context.assets.getAtlas().findRegion("playerjump").split(13, 18)[0], -1f))
        animationSet.addAnimation("jumpr", Animation(context.assets.getAtlas().findRegion("playerjumpr").split(13, 18)[0], -1f))
        animationSet.addAnimation("crouch", Animation(context.assets.getAtlas().findRegion("playercrouch").split(16, 18)[0], 1f / 10f))
        animationSet.addAnimation("crouchr", Animation(context.assets.getAtlas().findRegion("playercrouchr").split(16, 18)[0], 1f / 10f))

        animationSet.setAnimation("idle")
    }

    override fun setPositionFromTile(row: Int, col: Int) {
        super.setPositionFromTile(row, col)
        tileMap.toggleTile(row, col)
    }

    // handle movement
    fun moveTile(rowdx: Int, coldx: Int) {
        // ignore movement while still moving to destination
        if (moving) return

        // ignore movement to invalid tiles
        if (!superjump && !tileMap.isValidTile(row + rowdx, col + coldx)) return

        // ignore while on moving tile
        if (currentTile?.moving == true) return

        // valid tiles
        if (!teleporting) {
            when {
                coldx > 0 -> direction = Direction.RIGHT
                coldx < 0 -> direction = Direction.LEFT
                rowdx > 0 -> direction = Direction.DOWN
                rowdx < 0 -> direction = Direction.UP
            }
        }

        // update
        row += rowdx
        col += coldx
        tileMap.toPosition(row, col, pdest)

        totalDist = getRemainingDistance()
        moving = true
        justTeleported = false

        currentTile?.moveListeners?.remove(this)
        tileMap.getTile(row, col)?.let { tile ->
            tile.lock = true
        }
    }

    private fun getRemainingDistance() = Utils.dist(pdest.x, pdest.y, p.x, p.y)

    private fun atDestination() = p.x == pdest.x && p.y == pdest.y

    private fun addTileLight() {
//        if (tileMap.getTile(row, col).active) {
//            tileMap.otherObjects.add(TileLight(context, tileMap, row, col))
//        }
    }

    private fun handleJustMoved(row: Int, col: Int) {
        if (moving) {
            moveListener?.onMoved()
            if (!tileMap.isFinished()) {
                tileMap.toggleTile(row, col)
                moveListener?.onToggled()
            }
            sliding = false
            superjump = false
            moving = false
            if (atDestination()) {
                teleporting = false
            }
            addTileLight()
            currentTile = tileMap.getTile(row, col)
            currentTile?.let { tile ->
                tile.lock = false
                tile.moveListeners.add(this)
            }
        }
    }

    private fun handleTileObjects(row: Int, col: Int) {
        tileMap.getTile(row, col)?.objects?.forEach {
            when {
                it is Arrow -> {
                    sliding = true
                    direction = it.direction
                }
                it is SuperJump -> {
                    superjump = true
                }
                it is Teleport && !justTeleported -> {
                    teleportSpeed = Utils.max(Utils.abs(p.y - tileMap.toPosition(it.row2)), Utils.abs(p.x - tileMap.toPosition(it.col2))) * 1.5f
                    moveTile(it.row2 - row, it.col2 - col)
                    teleporting = true
                    justTeleported = true
                }
            }
        }

        if (sliding || superjump) {
            val dist2 = if (superjump) 2 else 1
            var r = 0
            var c = 0
            when (direction) {
                Direction.UP -> r = -dist2
                Direction.LEFT -> c = -dist2
                Direction.RIGHT -> c = dist2
                Direction.DOWN -> r = dist2
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
            animationSet.setAnimation(if (direction == Direction.RIGHT || direction == Direction.DOWN) "crouch" else "crouchr")
        } else if (atDestination()) {
            if ((animationSet.currentAnimationKey == "jump" || animationSet.currentAnimationKey == "jumpr")) {
                animationSet.setAnimation(if (direction == Direction.RIGHT || direction == Direction.DOWN) "crouch" else "crouchr")
            } else if (animationSet.currentAnimation.hasPlayedOnce()) {
                animationSet.setAnimation(if (direction == Direction.RIGHT || direction == Direction.DOWN) "idle" else "idler")
            }
        } else {
            if ((animationSet.currentAnimationKey == "idle" || animationSet.currentAnimationKey == "idler")) {
                animationSet.setAnimation(if (direction == Direction.RIGHT || direction == Direction.DOWN) "crouch" else "crouchr")
            } else {
                animationSet.setAnimation(if (direction == Direction.RIGHT || direction == Direction.DOWN) "jump" else "jumpr")
            }
        }
        animationSet.update(dt)
    }

    override fun onTileEndMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
        super.setPositionFromTile(newRow, newCol)
        pdest.set(p)
    }

    override fun update(dt: Float) {
        currentTile?.let {
            if (!moving && it.moving) {
                p.set(it.p.x, it.p.y, p.z)
                pdest.set(p)
                updateAnimations(dt)
                return
            }
        }
        val dist = dt *
                (if (teleporting && justTeleported) teleportSpeed else speed) * // base speed
                (if (sliding) 4f else if (superjump) 2f else 1f)                // multiplier
        p.moveTo(pdest, dist)

        if (atDestination()) {
            if (currentTile?.isMovingTile() == true) {

            } else if (!tileMap.isValidTile(row, col)) {
                moveListener?.onIllegal()
                return
            }

            handleJustMoved(row, col)
            handleTileObjects(row, col)
        }

        updateBounceHeight()
        updateAnimations(dt)
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        if (!teleporting) {
            if (direction == Direction.RIGHT || direction == Direction.UP) {
                sb.draw(animationSet.getImage(), isop.x - animationSet.getImage().regionWidth / 2, isop.y - isoHeight / 2 + p.z + tileHeight3d)
            } else {
                sb.draw(
                        animationSet.getImage(),
                        isop.x + animationSet.getImage().regionWidth / 2,
                        isop.y - isoHeight / 2 + p.z + tileHeight3d,
                        -animationSet.getImage().regionWidth * 1f,
                        animationSet.getImage().regionHeight * 1f)
            }
        }
    }

}