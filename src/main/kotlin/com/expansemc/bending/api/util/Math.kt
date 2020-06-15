package com.expansemc.bending.api.util

import org.bukkit.Location
import org.bukkit.util.Vector
import org.spongepowered.math.matrix.Matrix3d
import org.spongepowered.math.vector.Vector3d
import kotlin.math.cos
import kotlin.math.sin

/**
 * Transforms this vector based on the provided transformation matrix.
 *
 * @receiver The vector to transform
 * @param matrix The transformation matrix
 * @return The transformed vector
 */
fun Vector.transform(matrix: Matrix3d): Vector {
    this.x = matrix[0, 0] * this.x + matrix[0, 1] * this.y + matrix[0, 2] * this.z
    this.y = matrix[1, 0] * this.x + matrix[1, 1] * this.y + matrix[1, 2] * this.z
    this.z = matrix[2, 0] * this.x + matrix[2, 1] * this.y + matrix[2, 2] * this.z
    return this
}

/**
 * Gets the orthogonal vector of the given axis vector.
 *
 * @receiver The axis vector
 */
fun Vector.getOrthogonal(degrees: Double, length: Double): Vector {
    val orthogonal: Vector = Vector(y, -x, 0.0).normalize().multiply(length)
    val thirdAxis: Vector = this.clone().crossProduct(orthogonal).normalize().multiply(orthogonal.length())
    val angle: Double = Math.toRadians(degrees)
    return orthogonal.multiply(cos(angle)).add(thirdAxis.multiply(sin(angle)))
}

operator fun Location.plus(vec: Vector): Location = this.clone().add(vec)
operator fun Location.plus(loc: Location): Location = this.clone().add(loc)
operator fun Location.plus(loc: Vector3d): Location = this.clone().add(loc)

operator fun Location.minus(vec: Vector): Location = this.clone().subtract(vec)
operator fun Location.minus(loc: Location): Location = this.clone().subtract(loc)

operator fun Vector.unaryMinus(): Vector {
    val cloned = this.clone()
    cloned.x = -cloned.x
    cloned.y = -cloned.y
    cloned.z = -cloned.z
    return cloned
}

operator fun Vector.plus(vec: Vector): Vector = this.clone().add(vec)

operator fun Vector.minus(vec: Vector): Vector = this.clone().subtract(vec)

operator fun Vector.times(vec: Vector): Vector = this.clone().multiply(vec)
operator fun Vector.times(m: Int): Vector = this.clone().multiply(m)
operator fun Vector.times(m: Float): Vector = this.clone().multiply(m)
operator fun Vector.times(m: Double): Vector = this.clone().multiply(m)

operator fun Vector.div(vec: Vector): Vector = this.clone().divide(vec)

operator fun Vector.div(m: Int): Vector {
    val cloned = this.clone()
    cloned.x /= m
    cloned.y /= m
    cloned.z /= m
    return cloned
}

operator fun Vector.div(m: Float): Vector {
    val cloned = this.clone()
    cloned.x /= m
    cloned.y /= m
    cloned.z /= m
    return cloned
}

operator fun Vector.div(m: Double): Vector {
    val cloned = this.clone()
    cloned.x /= m
    cloned.y /= m
    cloned.z /= m
    return cloned
}