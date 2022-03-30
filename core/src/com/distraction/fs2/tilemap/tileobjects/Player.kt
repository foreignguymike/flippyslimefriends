package com.distraction.fs2.tilemap.tileobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.distraction.fs2.*
import com.distraction.fs2.tilemap.Tile
import com.distraction.fs2.tilemap.TileMap
import com.distraction.fs2.tilemap.data.Direction
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sin

class Player(
    context: Context,
    tileMap: TileMap,
    private val moveListener: MoveListener,
    startRow: Int,
    startCol: Int,
    bubbling: Boolean = false
) : TileObject(context, tileMap), Tile.TileMoveListener {

    interface MoveListener {
        fun onMoved()
        fun onIllegal()
    }

    var players = listOf<Player>()

    private val animationSet = AnimationSet()

    override var speed = TileMap.TILE_SIZE * 1.85f

    private val jumpHeight = 40f
    private var totalDist = 0f
    private var moving = false
    private var sliding = false
    private var superjump = false
    private var teleporting = false
    private var justTeleported = false
    private var teleportSpeed = 0f
    private var direction = Direction.RIGHT
    private var teleportTimer = 0f

    private var selected = false
    private var selectedTimer = 0f

    var bubbling = false
    var dropping = false

    private val pointerImage = context.getImage("slimepointer")
    private val bubble = BreathingImage(context.getImage("bubble"), interval = 2f, offset = 0.03f)
    private val bubblex = BreathingImage(context.getImage("bubbledropx"))
    private val bubbleo = BreathingImage(context.getImage("bubbledropo"))
    var canDrop = false

    init {
        this.bubbling = bubbling
        setPositionFromTile(startRow, startCol)
        p.z = BASELINE
        pdest.set(p)

        currentTile = tileMap.getTile(row, col)
        currentTile?.let { tile ->
            tile.lock = false
        }
        tileMap.map.forEach {
            if (it?.isMovingTile() == true) {
                it.moveListeners.add(this)
            }
        }

        animationSet.addAnimation(
            "idle",
            Animation(context.getImage("playeridle").split(SPRITE_WIDTH, SPRITE_HEIGHT)[0], 0.5f)
        )
        animationSet.addAnimation(
            "idler",
            Animation(context.getImage("playeridler").split(SPRITE_WIDTH, SPRITE_HEIGHT)[0], 0.5f)
        )
        animationSet.addAnimation(
            "jump",
            Animation(context.getImage("playerjump").split(SPRITE_WIDTH, SPRITE_HEIGHT)[0], 0.1f, 1)
        )
        animationSet.addAnimation(
            "jumpr",
            Animation(
                context.getImage("playerjumpr").split(SPRITE_WIDTH, SPRITE_HEIGHT)[0],
                0.1f,
                1
            )
        )
        animationSet.addAnimation(
            "crouch",
            Animation(context.getImage("playercrouch").split(SPRITE_WIDTH, SPRITE_HEIGHT)[0], 0.1f)
        )
        animationSet.addAnimation(
            "crouchr",
            Animation(context.getImage("playercrouchr").split(SPRITE_WIDTH, SPRITE_HEIGHT)[0], 0.1f)
        )

        animationSet.setAnimation("idle")
    }

    override fun setPositionFromTile(row: Int, col: Int) {
        super.setPositionFromTile(row, col)
        if (!bubbling) {
            tileMap.toggleTile(row, col)
        }
    }

    fun showSelected(selected: Boolean) {
        this.selected = selected
        selectedTimer = 0f
    }

    private fun updateCanDrop() {
        canDrop = !moving
                && bubbling
                && p.z == BASELINE + BUBBLE_HEIGHT
                && tileMap.isValidTile(row, col)
                && !isTileBlocked(row, col)
    }

    fun dropBubble() {
        if (canDrop && atDestination()) {
            tileMap.getTile(row, col)?.lock = true
//            currentTile?.lock = true
            dropping = true
        }
    }

    // handle movement
    fun moveTile(drow: Int, dcol: Int) {
        // ignore movement while still moving to destination
        if (moving) return

        // if in bubble, ignore everything, just move
        if (!bubbling) {

            // ignore movement to invalid tiles
            // only valid for manual movement, players can still slide off the tilemap
            if (!sliding && !superjump && !tileMap.isValidTile(row + drow, col + dcol)) return

            // ignore while on moving tile
            if (currentTile?.moving == true) return

            // ignore if the tile is blocked
            // but allow it if you're super jumping
            if (!superjump && isTileBlocked(row + drow, col + dcol)) {
                sliding = false
                return
            }
        } else {
            // wait until finish rising in bubble
            if (p.z < BASELINE + BUBBLE_HEIGHT) return

            // don't go far out of map bounds
            if (row + drow >= tileMap.numRows + 1 || row + drow < -1
                || col + dcol >= tileMap.numCols + 1 || col + dcol < -1
            ) {
                return
            }

            // cannot run into other floating slimes
            if (getPlayers(row + drow, col + dcol).any { it.bubbling }) return
        }

        // valid tiles start here

        // update direction
        if (!teleporting) {
            when {
                dcol > 0 -> direction = Direction.RIGHT
                dcol < 0 -> direction = Direction.LEFT
                drow > 0 -> direction = Direction.DOWN
                drow < 0 -> direction = Direction.UP
            }
        }

        // update tile position and set destination
        row += drow
        col += dcol
        tileMap.toPosition(row, col, pdest)

        totalDist = getRemainingDistance()
        moving = true
        justTeleported = false

        // unsub to the current tile's listener
        // will sub to the destination tile's listener later in handleJustMoved()
        // also lock the destination tile to prevent the tile from moving while the player is moving towards it
        tileMap.getTile(row, col)?.let { tile ->
            tile.lock = true
        }
    }

    /**
     * Tile is blocked or there is another player in the way.
     */
    private fun isTileBlocked(row: Int, col: Int) =
        tileMap.getTile(row, col)?.isBlocked() == true
                || getPlayers(row, col).any { it != this && !it.bubbling }

    private fun getPlayers(row: Int, col: Int) =
        players.filter { it.row == row && it.col == col }

    private fun getRemainingDistance() = Utils.dist(pdest.x, pdest.y, p.x, p.y)

    private fun atDestination() = p.x == pdest.x && p.y == pdest.y

    private fun resetMovement() {
        moving = false
        sliding = false
        superjump = false
        teleporting = false
    }

    /**
     * Function to handle that the player has just landed on a tile.
     */
    private fun handleJustMoved(row: Int, col: Int) {
        if (!bubbling) {
            // notify listeners
            moveListener.onMoved()
            tileMap.toggleTile(row, col)
        }

        // reset all movement flags
        resetMovement()

        // set the current tile
        // if the destination tile is locked, unlock it
        // the destination tile is locked if it's a moving tile
        // this is to prevent the tile from moving away while the player is still moving towards it
        currentTile = tileMap.getTile(row, col)
        currentTile?.let { tile ->
            tile.lock = false
        }
    }

    /**
     * If player has landed on a tile that contains tile objects,
     * this function will handle how the player will react to those objects.
     */
    private fun handleTileObjects(row: Int, col: Int) {
        tileMap.getTile(row, col)?.objects?.forEach {
            when {
                it is Bubble -> {
                    if (!it.bubbleBase.resetting) {
                        bubbling = true
                        it.bubbleBase.resetting = true
                    }
                }
                it is Arrow -> {
                    sliding = true
                    direction = it.direction
                }
                it is SuperJump -> {
                    superjump = true
                }
                it is Ice -> {
                    if (!dropping) sliding = true
                }
                it is Teleport && !justTeleported -> {
                    teleportTimer = 0f
                    teleportSpeed = Utils.max(
                        abs(p.y - tileMap.toPosition(it.row2)),
                        abs(p.x - tileMap.toPosition(it.col2))
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

    private fun updateBounceHeight(dt: Float) {
        when {
            superjump -> p.z = BASELINE + jumpHeight * 1.5f * getArc()
            sliding -> p.z = BASELINE
            dropping -> p.z = max(p.z - dt * BUBBLE_DROP_SPEED, BASELINE)
            bubbling -> {
                if (p.z < BASELINE + BUBBLE_HEIGHT) {
                    p.z += dt * BUBBLE_HEIGHT_SPEED
                    if (p.z > BASELINE + BUBBLE_HEIGHT) {
                        p.z = BASELINE + BUBBLE_HEIGHT
                        updateCanDrop()
                    }
                }
            }
            else -> p.z = BASELINE + jumpHeight * getArc()
        }
    }

    private fun getArc() = MathUtils.sin(3.14f * getRemainingDistance() / totalDist)

    private fun handleReachedDestination() {
        if (!bubbling) {
            // landed on illegal tile or another player
            if (!tileMap.isValidTile(row, col) || isTileBlocked(row, col)) {
                resetMovement()
                moveListener.onIllegal()
                return
            }
        }
        handleJustMoved(row, col)
        if (!bubbling) {
            handleTileObjects(row, col)
        }
        dropping = false
        players.forEach { it.updateCanDrop() }
    }

    private fun updateAnimations(dt: Float) {
        if (sliding) {
            animationSet.setAnimation(if (direction == Direction.RIGHT || direction == Direction.DOWN) "crouch" else "crouchr")
        } else if (dropping) {
            animationSet.setAnimation(if (direction == Direction.RIGHT || direction == Direction.DOWN) "jump" else "jumpr")
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

    override fun onTileStartMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
        if (row == oldRow && col == oldCol) {
            updateCanDrop()
        }
    }

    override fun onTileEndMove(tile: Tile, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int) {
        // stick player on moving tile if not bubbling and player is not already moving
        if (!moving && !bubbling) {
            if (currentTile == tile) {
                super.setPositionFromTile(newRow, newCol)
                pdest.set(p)
            }
        }
        if (row == newRow && col == newCol) {
            updateCanDrop()
        }
    }

    override fun update(dt: Float) {
        selectedTimer += dt

        // handle dropping
        if (dropping && p.z == BASELINE) {
            bubbling = false
            handleReachedDestination()
        }

        // handle arrows while blocks
        if (atDestination() &&
            tileMap.getTile(row, col)?.objects?.any { it is Arrow } == true && !bubbling
        ) {
            handleTileObjects(row, col)
        }

        // stick the player on moving tiles if player is not in bubble or not moving
        if (!bubbling) {
            currentTile?.let {
                if (!moving && it.moving) {
                    p.set(it.p.x, it.p.y, p.z)
                    pdest.set(p)
                    updateAnimations(dt)
                    return
                }
            }
        }

        // move towards destination by dist amount
        if (!atDestination()) {
            val dist = dt * (if (teleporting && justTeleported) teleportSpeed else speed)
            val multiplier =
                if (superjump) SUPER_JUMP_MULTIPLIER else if (sliding) SLIDING_MULTIPLIER else 1f
            if (teleporting) {
                teleportTimer += dt
                if (teleportTimer > TELEPORT_TIME_LIMIT) {
                    p.x = pdest.x
                    p.y = pdest.y
                } else {
                    p.moveToLinear(pdest, dist * multiplier)
                }
            } else {
                p.moveTo(pdest, dist * multiplier)
            }
        }

        // handle logic for player just finished moving (moving && atDestination())
        // if the player has reached destination
        if (atDestination() && moving) {
            handleReachedDestination()
        }

        updateBounceHeight(dt)
        updateAnimations(dt)
        if (bubbling) {
            bubble.update(dt)
            bubbleo.update(dt)
            bubblex.update(dt)
        }
    }

    override fun render(sb: SpriteBatch) {
        tileMap.toIsometric(p.x, p.y, isop)
        sb.resetColor()
        if (!teleporting) {
            if (bubbling) {
                if (!dropping) {
                    bubble.setPosition(isop.x, isop.y + p.z + 10)
                    bubble.render(sb)
                }
                if (p.z == BASELINE + BUBBLE_HEIGHT) {
                    if (canDrop) {
                        bubbleo.setPosition(isop.x, isop.y)
                        bubbleo.render(sb)
                    } else {
                        bubblex.setPosition(isop.x, isop.y)
                        bubblex.render(sb)
                    }
                }
            }
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
            if (selected && selectedTimer < 3 && (selectedTimer * 10).toInt() % 5 < 3) {
                sb.draw(
                    pointerImage,
                    isop.x - pointerImage.regionWidth / 2,
                    isop.y + p.z - 20f + 2 * sin(3 * selectedTimer)
                )
            }
        }
    }

    companion object {
        const val SPRITE_WIDTH = 30
        const val SPRITE_HEIGHT = 30
        const val BASELINE = -3f
        const val BUBBLE_HEIGHT = 40f
        const val BUBBLE_HEIGHT_SPEED = 50f
        const val BUBBLE_DROP_SPEED = 300f
        const val TELEPORT_TIME_LIMIT = 0.75f

        const val SUPER_JUMP_MULTIPLIER = 2f
        const val SLIDING_MULTIPLIER = 2.5f
    }

}