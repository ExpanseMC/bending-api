package com.expansemc.bending.api.ability

import com.expansemc.bending.api.bender.Bender
import com.expansemc.bending.api.collision.CollisionRegion
import com.expansemc.bending.api.collision.CollisionResult
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

/**
 * An ability execution in progress.
 */
interface AbilityTask {
    /**
     * Gets the bender that executed the ability.
     *
     * @return The bender
     */
    val bender: Bender

    /**
     * Gets the player that executed the ability.
     *
     * @return The player
     */
    val player: Player

    /**
     * Gets the ability that is executing.
     *
     * @return The ability
     */
    val ability: Ability

    /**
     * Gets the type of ability being executed.
     *
     * @return The type of ability
     */
    val type: AbilityType

    /**
     * Gets the currently executing ability's execution context.
     *
     * @return The ability execution context
     */
    val context: AbilityContext

    /**
     * Gets the way that the ability was executed.
     *
     * @return The ability execution type
     */
    val executionType: AbilityExecutionType

    /**
     * Gets and sets the underlying scheduler task being ran.
     *
     * @return The underlying task, if available
     */
    var currentTask: BukkitTask?

    /**
     * Gets and sets the region used to check ability collision.
     *
     * @return The ability collision region
     */
    var collisionRegion: CollisionRegion?

    /**
     * Runs collision checks across all currently executing abilities.
     *
     * @return Whether this ability won the collision check
     */
    fun collide(): CollisionResult

    /**
     * Gets whether the underlying scheduler task has finished.
     *
     * @return True if the underlying task has finished
     */
    val isDone: Boolean

    /**
     * Gets whether this ability has been cancelled.
     *
     * @return True if the ability has been cancelled
     */
    val isCancelled: Boolean

    /**
     * Cancels this ability execution.
     */
    fun cancel()
}