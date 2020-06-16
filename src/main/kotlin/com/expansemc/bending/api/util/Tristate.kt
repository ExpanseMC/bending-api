package com.expansemc.bending.api.util

enum class Tristate(private val value: Boolean) {
    TRUE(true) {
        override fun and(other: Tristate): Tristate = if (other === TRUE || other === UNDEFINED) TRUE else FALSE

        override fun or(other: Tristate): Tristate = TRUE

        override fun not(): Tristate = FALSE
    },
    FALSE(false) {
        override fun and(other: Tristate): Tristate = FALSE

        override fun or(other: Tristate): Tristate = if (other === TRUE) TRUE else FALSE

        override fun not(): Tristate = TRUE
    },
    UNDEFINED(false) {
        override fun and(other: Tristate): Tristate = other

        override fun or(other: Tristate): Tristate = other

        override fun not(): Tristate = UNDEFINED
    };

    abstract infix fun and(other: Tristate): Tristate

    abstract infix fun or(other: Tristate): Tristate

    abstract operator fun not(): Tristate

    fun toBoolean(): Boolean = this.value
}

fun Boolean.toTristate(): Tristate = if (this) Tristate.TRUE else Tristate.FALSE