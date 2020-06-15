package com.expansemc.bending.api.ability

import com.expansemc.bending.api.registry.FactoryRegistry
import com.expansemc.bending.api.registry.create

interface AbilityContext {

    operator fun <E: Any> get(key: AbilityContextKey<E>): E?

    fun <E: Any> require(key: AbilityContextKey<E>): E

    operator fun <E: Any> set(key: AbilityContextKey<E>, value: E)

    fun <E: Any> remove(key: AbilityContextKey<E>): E?

    operator fun contains(key: AbilityContextKey<*>): Boolean

    companion object {
        @JvmStatic
        fun empty(): AbilityContext = FactoryRegistry.instance.create()
    }
}