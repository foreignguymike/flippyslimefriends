package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.Tile
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.data.Direction
import kotlin.math.absoluteValue

class Player(
    context: Context,
    tileMap: TileMap,
    private val moveListener: MoveListener,
    startRow: Int,
    startCol: Int
) : TileObject(context, tileMap), Tile.TileMoveListener {

    interface MoveListener {
        fun onMoved()
        fun onIllegal()
    }

    private val animationSet = AnimationSet()

    override var speed = TileMap.TILE_SIZE * 2

    private val jumpHeight = 20f
    private var totalDist = 0f
    private var moving = false
    private var sliding = false
    private var superjump = false
    private var teleporting = false
    private var justTeleported = false
    private var teleportSpeed = 0f
    private var direction = Direction.RIGHT

    init {
        setPositionFromTile(startRow, startCol)
        p.z = 4f
        pdest.set(p)

        currentTile = tileMap.getTile(row, col)

        animationSet.addAnimation(
            "idle",
            Animation(context.getImage("playeridle").split(16, 18)[0], 1f / 2f)
        )
        animationSet.addAnimation(
            "idler",
            Animation(context.getImage("playeridler").split(16, 18)[0], 1f / 2f)
        )
        animationSet.addAnimation(
            "jump",
            Animation(context.getImage("playerjump").split(13, 18)[0], -1f)
        )
        animationSet.addAnimation(
            "jumpr",
            Animation(context.getImage("playerjumpr").split(13, 18)[0], -1f)
        )
        animationSet.addAnimation(
            "crouch",
            Animation(context.getImage("playercrouch").split(16, 18)[0], 1f / 10f)
        )
        animationSet.addAnimation(
            "crouchr",
            Animation(context.getImage("playercrouchr").split(16, 18)[0], 1f / 10f)
        )

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
        // only valid for manual movement, players can still slide off the tilemap
        if (!sliding && !superjump && !tileMap.isValidTile(row + rowdx, col + coldx)) return

        // ignore while on moving tile
        if (currentTile?.moving == true) return

        // ignore if the tile is blocked
        // but allow it if you're super jumping
        if (tileMap.getTile(row + rowdx, col + coldx)?.isBlocked() == true && !superjump) {
            sliding = false
            return
        }

        // valid tiles start here

        // update direction
        if (!teleporting) {
            when {
                coldx > 0 -> direction = Direction.RIGHT
                coldx < 0 -> direction = Direction.LEFT
                rowdx > 0 -> direction = Direction.DOWN
                rowdx < 0 -> direction = Direction.UP
            }
        }

        // update tile position and set destination
        row += rowdx
        col += coldx
        tileMap.toPosition(row, col, pdest)

        totalDist = getRemainingDistance()
        moving = true
        justTeleported = false

        // unsub to the current tile's listener
        // will sub to the destination tile's listener later in handleJustMoved()
        // also lock the destination tile to prevent the tile from moving while the player is moving towards it
        currentTile?.moveListeners?.remove(this)
        tileMap.getTile(row, col)?.let { tile ->
            tile.lock = true
        }
    }

    private fun getRemainingDistance() = Utils.dist(pdest.x, pdest.y, p.x, p.y)

    private fun atDestination() = p.x == pdest.x && p.y == pdest.y

    /**
     * Function to handle that the player has just landed on a tile.
     */
    private fun handleJustMoved(row: Int, col: Int) {
        // notify listeners
        moveListener.onMoved()

        // if the map isn't finished, toggle the tile
        if (!tileMap.isFinished()) {
            tileMap.toggleTile(row, col)
        }

        // reset all movement flags
        moving = false
        sliding = false
        superjump = false
        teleporting = false

        // set the current tile
        // if the destination tile is locked, unlock it
        // the destination tile is locked if it's a moving tile
        // this is to prevent the tile from moving away while the player is still moving towards it
        currentTile = tileMap.getTile(row, col)
        currentTile?.let { tile ->
            tile.lock = false
            tile.moveListeners.add(this)
        }
    }

    /**
     * If player has landed on a tile that contains tile objects,
     * this function will handle how the player will react to those objects.
     */
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
                it is Ice -> {
                    sliding = true
                }
                it is Teleport && !justTeleported -> {
                    teleportSpeed = Utils.max(
                        (p.y - tileMap.toPosition(it.row2)).absoluteValue,
                        (p.x - tileMap.toPosition(it.col2)).absoluteValue
                    ) * 1.5f
                    moveTile(it.row2 - row, it.col2 - col)
                    teleporting = true
                    justTeleported = true
                }
            }
        }

        // making these mutually exclusive
        if (superjump) sliding = false

        // set tile to move to for sliding and superjump, teleport is handled up there
        if (sliding || superjump) {
            var rx = 0
            var cx = 0
            val dist2 = if (superjump) 2 else 1
            when (direction) {
                Direction.UP -> rx = -dist2
                Direction.LEFT -> cx = -dist2
                Direction.RIGHT -> cx = dist2
                Direction.DOWN -> rx = dist2
            }
            moving = false
            moveTile(rx, cx)
        }
    }

    private fun updateBounceHeight() {
        when {
            superjump -> p.z = 4f + jumpHeight * 1.5f * getArc()
            sliding -> p.z = 4f
            else -> p.z = 4f + jumpHeight * getArc()
        }
    }

    private fun getArc() = MathUtils.sin(3.14f * getRemainingDistance() / totalDist)

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
        // if player is on a moving tile and the player is currently not moving
        // match the player's position with tile's position
        currentTile?.let {
            if (!moving && it.moving) {
                p.set(it.p.x, it.p.y, p.z)
                pdest.set(p)
                updateAnimations(dt)
                return
            }
        }

        // if player is not at destination, move player's position towards destination by dist amount
        if (!atDestination()) {
            val dist = dt *
                    (if (teleporting && justTeleported) teleportSpeed else speed) * // base speed
                    (if (superjump) 2f else if (sliding) 3f else 1f)                // multiplier
            p.moveTo(pdest, dist)
        }

        // if the player has reached destination
        if (atDestination()) {

            // landed on illegal tile
            if (!tileMap.isValidTile(row, col)) {
                moveListener.onIllegal()
                return
            }

            // handle logic for player just finished moving (moving && atDestination())
            if (moving) {
                handleJustMoved(row, col)
            }

            // handle any tile objects
            handleTileObjects(row, col)
        }

        updateBounceHeight()
        updateAnimations(dt)
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        if (!teleporting) {
            if (direction == Direction.RIGHT || direction == Direction.UP) {
                sb.draw(
                    animationSet.getImage(),
                    isop.x - animationSet.getImage().regionWidth / 2,
                    isop.y + p.z
                )
            } else {
                sb.draw(
                    animationSet.getImage(),
                    isop.x + animationSet.getImage().regionWidth / 2,
                    isop.y + p.z,
                    -animationSet.getImage().regionWidth * 1f,
                    animationSet.getImage().regionHeight * 1f
                )
            }
        }
    }

}