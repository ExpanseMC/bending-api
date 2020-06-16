package com.expansemc.bending.api.util

private fun mod(a: Double, b: Double): Double {
    val mod = a % b
    return if (mod >= 0) mod else mod + b
}

private fun differenceModulo(a: Double, b: Double, c: Double): Double {
    return mod(mod(a, c) - mod(b, c), c)
}

private fun getProgressionLastElement(start: Double, end: Double, step: Double): Double = when {
    step > 0 -> if (start >= end) end else end - differenceModulo(end, start, step)
    step < 0 -> if (start <= end) end else end + differenceModulo(start, end, -step)
    else -> throw IllegalArgumentException("step is zero")
}

class DoubleProgression(start: Double, endInclusive: Double, val step: Double) : Iterable<Double> {
    init {
        require(step != 0.0) { "step must be non-zero" }
    }

    val first: Double = start

    val last: Double = getProgressionLastElement(start, endInclusive, step)

    override fun iterator(): DoubleIterator = InclusiveIterator(first, last, step)

    fun isEmpty(): Boolean = if (step > 0) first > last else first < last

    override fun equals(other: Any?): Boolean =
            other is DoubleProgression
                    && (isEmpty() && other.isEmpty() || first == other.first && last == other.last && step == other.step)

    override fun hashCode(): Int =
            if (isEmpty()) -1 else (31 * (31 * first.toInt() + last.toInt()) + step.toInt())

    override fun toString(): String = if (step > 0) "$first..$last step $step" else "$first downTo $last step ${-step}"

    private class InclusiveIterator(first: Double, last: Double, val step: Double) : DoubleIterator() {
        private val finalElement: Double = last
        private var hasNext: Boolean = if (step > 0) first <= last else first >= last
        private var next: Double = if (hasNext) first else finalElement

        override fun hasNext(): Boolean = hasNext

        override fun nextDouble(): Double {
            val value: Double = next
            if (value == finalElement) {
                if (!hasNext) throw NoSuchElementException()
                hasNext = false
            } else {
                next += step
            }
            return value
        }
    }
}

infix fun ClosedFloatingPointRange<Double>.step(step: Double): DoubleProgression =
        DoubleProgression(this.start, this.endInclusive, step)