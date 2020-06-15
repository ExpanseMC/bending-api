package com.expansemc.bending.api.util

import java.util.*

class HotbarList<T> : Iterable<T?> {
    @Suppress("UNCHECKED_CAST")
    private val elements: Array<T?> = arrayOfNulls<Any>(9) as Array<T?>

    operator fun get(index: Int): T? {
        check(index in 0..9) { "Valid hotbar indexes are from 0 to 9" }
        return elements[index]
    }

    operator fun set(index: Int, value: T?) {
        check(index in 0..9) { "Valid hotbar indexes are from 0 to 9" }
        elements[index] = value
    }

    fun clear() = elements.clear()

    override fun iterator(): Iterator<T?> = HotbarListIterator(elements)

    private class HotbarListIterator<E>(private val elements: Array<E>) : Iterator<E?> {
        private var index = 0

        override fun hasNext(): Boolean {
            return index < elements.size
        }

        override fun next(): E? {
            if (index >= elements.size) {
                throw NoSuchElementException("reached end of hotbar")
            }
            return elements[index++]
        }
    }
}