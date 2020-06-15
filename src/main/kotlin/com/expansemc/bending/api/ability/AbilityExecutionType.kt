package com.expansemc.bending.api.ability

import com.expansemc.bending.api.registry.*
import org.bukkit.NamespacedKey

interface AbilityExecutionType : CatalogType {
    interface Builder {
        fun key(key: NamespacedKey): Builder

        fun build(): AbilityExecutionType
    }

    companion object {
        @JvmStatic
        fun builder(): Builder = FactoryRegistry.instance.create()

        @JvmStatic
        operator fun get(key: NamespacedKey): AbilityExecutionType? = CatalogRegistry.instance[key]

        @JvmStatic
        val all: Collection<AbilityExecutionType>
            get() = CatalogRegistry.instance.getAllOf()
    }
}