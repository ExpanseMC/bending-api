package com.expansemc.bending.api.util

import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import org.spongepowered.math.vector.Vector3d
import java.util.*

fun Location.getNearbyLocations(radius: Double): Collection<Location> {
    val result: MutableList<Location> = ArrayList()

    val originX: Int = this.blockX
    val originY: Int = this.blockY
    val originZ: Int = this.blockZ

    val r = (radius * 4).toInt()
    val radiusSquared = radius * radius

    for (x in originX - r..originX + r) {
        for (y in originY - r..originY + r) {
            for (z in originZ - r..originZ + r) {
                val location = Location(this.world, x.toDouble(), y.toDouble(), z.toDouble())

                if (location.distanceSquared(this) <= radiusSquared) {
                    result.add(location)
                }
            }
        }
    }

    return result
}


fun Location.getNearbyEntities(radius: Double): Collection<Entity> {
    return this.world!!.getNearbyEntities(this, radius, radius, radius) { entity: Entity ->
        !(entity.isDead || entity is Player && entity.gameMode == GameMode.SPECTATOR)
    }
}

fun Location.isNearDiagonalWall(direction: Vector): Boolean {
    val isSolidX: Boolean = this.block.getRelative(direction.x.toBlockFaceAxisX()).type.isSolid
    val isSolidY: Boolean = this.block.getRelative(direction.x.toBlockFaceAxisY()).type.isSolid
    val isSolidZ: Boolean = this.block.getRelative(direction.x.toBlockFaceAxisZ()).type.isSolid

    val xz: Boolean = isSolidX && isSolidZ
    val xy: Boolean = isSolidX && isSolidY
    val yz: Boolean = isSolidY && isSolidZ

    return xz || xy || yz
}

private fun Double.toBlockFaceAxisX(): BlockFace = when {
    this > 0 -> BlockFace.EAST
    this < 0 -> BlockFace.WEST
    else -> BlockFace.SELF
}

private fun Double.toBlockFaceAxisY(): BlockFace = when {
    this > 0 -> BlockFace.UP
    this < 0 -> BlockFace.DOWN
    else -> BlockFace.SELF
}

private fun Double.toBlockFaceAxisZ(): BlockFace = when {
    this > 0 -> BlockFace.SOUTH
    this < 0 -> BlockFace.NORTH
    else -> BlockFace.SELF
}

@JvmOverloads
inline fun Player.getTargetLocation(
    range: Double, checkDiagonals: Boolean = true,
    notSolid: (Material) -> Boolean = Material.AIR::equals
): Location {
    val location: Location = this.eyeLocation
    val increment: Vector = location.direction.normalize().multiply(0.2)

    var i = 0.0
    while (i < range - 1) {
        location.add(increment)

        if (checkDiagonals && location.isNearDiagonalWall(increment)) {
            location.subtract(increment)
            break
        }

        if (!notSolid(location.block.type)) {
            location.subtract(increment)
            break
        }

        i += 0.2
    }

    return location
}

fun Location.add(vec: Vector3d): Location = this.add(vec.x, vec.y, vec.z)

val Location.floor: Location
    get() {
        val floor = this.clone()
        while (floor.block.type == Material.AIR && floor.y > 0.0) {
            floor.y--
        }
        if (floor.y < 0.0) {
            floor.y = 0.0
        }
        return floor
    }

fun Location.spawnParticle(particle: Particle, count: Int) {
    this.spawnParticle(particle, count, 0.0, 0.0, 0.0)
}

fun Location.spawnParticle(particle: Particle, count: Int, offsetX: Double, offsetY: Double, offsetZ: Double) {
    this.spawnParticle(particle, count, offsetX, offsetY, offsetZ, 0.0)
}

fun Location.spawnParticle(particle: Particle, count: Int, offsetX: Double, offsetY: Double, offsetZ: Double, extra: Double) {
    this.spawnParticle(particle, count, offsetX, offsetY, offsetZ, extra, null)
}

fun Location.spawnParticle(particle: Particle, count: Int, offsetX: Double, offsetY: Double, offsetZ: Double, extra: Double, data: Any?) {
    this.spawnParticle(particle, count, offsetX, offsetY, offsetZ, extra, data, false)
}

fun Location.spawnParticle(
    particle: Particle,
    count: Int,
    offsetX: Double,
    offsetY: Double,
    offsetZ: Double,
    extra: Double,
    data: Any?,
    force: Boolean
) {
    this.world!!.spawnParticle(particle, this, count, offsetX, offsetY, offsetZ, extra, data, force)
}

fun Location.playSound(sound: Sound, volume: Float, pitch: Float) {
    this.world!!.playSound(this, sound, volume, pitch)
}

fun Location.playSound(sound: Sound, category: SoundCategory, volume: Float, pitch: Float) {
    this.world!!.playSound(this, sound, category, volume, pitch)
}