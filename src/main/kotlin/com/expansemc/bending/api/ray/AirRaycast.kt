package com.expansemc.bending.api.ray

import com.google.common.collect.ImmutableSet
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.block.data.type.Door
import org.bukkit.block.data.type.Switch
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import kotlin.math.abs

object AirRaycast {
    @JvmStatic
    val DOORS: Collection<Material> = ImmutableSet.of(
        Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.IRON_DOOR,
        Material.JUNGLE_DOOR, Material.SPRUCE_DOOR, Material.OAK_DOOR
    )

    @JvmStatic
    fun extinguishFlames(test: Location): Boolean {
        if (test.block.type != Material.FIRE) return false

        test.block.type = Material.AIR
        test.world!!.spawnParticle(Particle.SMOKE_NORMAL, test, 1)
        return true
    }

    @JvmStatic
    fun toggleDoor(test: Location): Boolean {
        if (test.block.type !in DOORS) return false

        val door: Door = test.block.blockData as Door
        door.isOpen = !door.isOpen
        test.block.blockData = door
        return true
    }

    @JvmStatic
    fun toggleLever(test: Location): Boolean {
        if (test.block.type != Material.LEVER) return false

        val lever: Switch = test.block.blockData as Switch
        lever.isPowered = !lever.isPowered
        test.block.blockData = lever
        return true
    }

    @JvmStatic
    fun pushEntity(ray: FastRaycast, source: Player, target: Entity, canPushSelf: Boolean, knockbackSelf: Double, knockbackOther: Double): Boolean {
        val isSelf: Boolean = source.uniqueId == target.uniqueId

        var knockback: Double = knockbackOther

        if (isSelf) {
            if (!canPushSelf) {
                // Ignore us.
                return false
            }
            knockback = knockbackSelf
        }

        knockback *= 1 - target.location.distance(ray.origin) / (2 * ray.range)

        if (target.location.clone().add(0.0, -0.5, 0.0).block.type.isSolid) {
            knockback *= 0.85
        }

        val max: Double = ray.speed / ray.speedFactor
        val push: Vector = ray.direction.clone()

        if (abs(push.y) > max && !isSelf) {
            push.y = if (push.y < 0) -max else max
        }

        push.normalize().multiply(knockback)

        if (abs(target.velocity.dot(push)) > knockback && target.velocity.angle(push) > Math.PI / 3) {
            push.normalize().add(target.velocity).multiply(knockback)
        }

        target.velocity = push
        return true
    }
}