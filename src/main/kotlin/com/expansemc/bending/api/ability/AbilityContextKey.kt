package com.expansemc.bending.api.ability

import com.expansemc.bending.api.registry.CatalogType
import com.expansemc.bending.api.registry.FactoryRegistry
import com.expansemc.bending.api.registry.create
import com.expansemc.bending.api.util.typeToken
import com.google.common.reflect.TypeToken
import org.bukkit.NamespacedKey

interface AbilityContextKey<out E : Any> : CatalogType {
    val allowedType: TypeToken<out E>

    interface Builder<E : Any> {
        fun key(key: NamespacedKey): Builder<E>

        fun type(type: TypeToken<E>): Builder<E>

        fun build(): AbilityContextKey<E>
    }

    companion object {
        @JvmStatic
        fun <E : Any> builder(type: TypeToken<E>): Builder<E> =
            FactoryRegistry.instance.create<Builder<E>>().type(type)

        inline fun <reified E : Any> builder(): Builder<E> =
            FactoryRegistry.instance.create<Builder<E>>().type(typeToken())
    }
}