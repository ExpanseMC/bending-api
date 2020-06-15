package com.expansemc.bending.api.collision

import com.expansemc.bending.api.ability.AbilityType
import org.spongepowered.math.vector.Vector3d

sealed class CollisionRegion {

    abstract val type: AbilityType

    abstract val priority: Int

    abstract fun intersects(other: CollisionRegion): Boolean

    data class Point(
        val points: Collection<Vector3d>,
        override val type: AbilityType,
        override val priority: Int
    ) : CollisionRegion() {

        override fun intersects(other: CollisionRegion): Boolean = when (other) {
            is Point -> this.points.any { first ->
                other.points.any { second ->
                    first.distanceSquared(second) < 1.0
                }
            }
            is Sphere -> {
                this.points.any { point ->
                    val distanceSq = point.distanceSquared(other.origin)
                    distanceSq < other.maxRadiusSq && distanceSq >= other.minRadiusSq
                }
            }
        }
    }

    data class Sphere(
        val origin: Vector3d,
        val minRadius: Double,
        val maxRadius: Double,
        override val type: AbilityType,
        override val priority: Int
    ) : CollisionRegion() {

        internal val minRadiusSq: Double = this.minRadius * this.minRadius

        internal val maxRadiusSq: Double = this.maxRadius * this.maxRadius

        override fun intersects(other: CollisionRegion): Boolean = when (other) {
            is Point -> {
                other.points.any { point ->
                    val distanceSq = this.origin.distanceSquared(point)
                    distanceSq < this.maxRadiusSq && distanceSq >= this.minRadiusSq
                }
            }
            is Sphere -> {
                val distanceSq = this.origin.distanceSquared(other.origin)
                (distanceSq < this.maxRadiusSq && distanceSq >= this.minRadiusSq)
                        || (distanceSq < other.maxRadiusSq && distanceSq >= other.minRadiusSq)
            }
        }
    }
}