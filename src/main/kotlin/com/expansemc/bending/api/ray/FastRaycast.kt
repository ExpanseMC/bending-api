package com.expansemc.bending.api.ray

import com.expansemc.bending.api.util.getNearbyEntities
import com.expansemc.bending.api.util.getNearbyLocations
import com.expansemc.bending.api.util.isNearDiagonalWall
import com.expansemc.bending.api.util.times
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

data class FastRaycast(
    val origin: Location,
    val direction: Vector,
    val range: Double,
    val speed: Double,
    val checkDiagonals: Boolean
) {

    val speedFactor: Double = this.speed * (50 / 1000.0)

    val rangeSquared: Double = this.range * this.range

    var location: Location = this.origin.clone()

    val step: Vector = this.direction * this.speedFactor

    var travelDistance: Double = 0.0

    val travelStep: Double = step.length()

    var isDone: Boolean = false

    inline fun progress(block: FastRaycast.(Location) -> Boolean): Boolean {
        if (this.range > 0 && this.location.distanceSquared(this.origin) > this.rangeSquared) {
            this.isDone = true
            return false
        }

        if (this.checkDiagonals && this.location.isNearDiagonalWall(this.direction)) {
            this.isDone = true
            return false
        }

        val result: Boolean = this.block(this.location)
        if (!result) {
            this.isDone = true
            return false
        }

        advance(this.location)
        this.travelDistance += this.travelStep

        return true
    }

    fun advance(location: Location) {
        location.add(this.step)
    }

    inline fun affectLocations(source: Player, affected: MutableCollection<Location>, radius: Double, affect: (test: Location) -> Boolean) {
        for (test: Location in this.location.getNearbyLocations(radius)) {
            if (test in affected) {
                // This block has already been affected.
                continue
            }

            // TODO: block protection

            if (affect(test)) {
                // If the block was successfully affected, add it to the collection.
                affected += test
            }
        }
    }

    inline fun affectEntities(source: Player, affected: MutableCollection<Entity>, radius: Double, affect: (test: Entity) -> Boolean) {
        for (test: Entity in this.location.getNearbyEntities(radius)) {
            if (test in affected) {
                // This entity has already been affected.
                continue
            }

            // TODO: pvp protection

            if (affect(test)) {
                // If the entity was successfully affected, add it to the collection.
                affected += test
            }
        }
    }

    @JvmOverloads
    fun damageEntity(test: Entity, amount: Double, source: Entity? = null): Boolean {
        if (amount <= 0.0 || test !is LivingEntity) return false

        test.damage(amount, source)
        return true
    }

    fun spawnParticle(particle: Particle, count: Int) {
        spawnParticle(particle, count, 0.0)
    }

    fun spawnParticle(particle: Particle, count: Int, offset: Double) {
        spawnParticle(particle, count, offset, offset, offset)
    }

    fun spawnParticle(particle: Particle, count: Int, offsetX: Double, offsetY: Double, offsetZ: Double) {
        this.location.world!!.spawnParticle(particle, this.location, count, offsetX, offsetY, offsetZ, 0.0, null, true)
    }

    fun playSound(sound: Sound, volume: Float, pitch: Float) {
        this.location.world!!.playSound(this.location, sound, volume, pitch)
    }
}

/**
 * @return True if any of the rays succeeded
 */
inline fun Iterable<FastRaycast>.progressAll(block: FastRaycast.(current: Location) -> Boolean): Boolean =
    this.iterator().progressAll(block)

/**
 * @return True if any of the rays succeeded
 */
inline fun Array<FastRaycast>.progressAll(block: FastRaycast.(current: Location) -> Boolean): Boolean =
    this.iterator().progressAll(block)

/**
 * @return True if any of the rays succeeded
 */
inline fun Iterator<FastRaycast>.progressAll(block: FastRaycast.(current: Location) -> Boolean): Boolean {
    var successful = false
    for (projectile: FastRaycast in this) {
        if (projectile.isDone) {
            continue
        }

        val result: Boolean = projectile.progress { location: Location ->
            this.block(location)
        }

        if (result) {
            successful = true
        }
    }
    return successful
}