package com.expansemc.bending.api.element

import com.expansemc.bending.api.registry.*
import org.bukkit.ChatColor
import org.bukkit.NamespacedKey

interface Element : NamedCatalogType {
    val color: ChatColor

    interface Builder {
        fun key(key: NamespacedKey): Builder

        fun name(name: String): Builder

        fun color(color: ChatColor): Builder

        fun build(): Element
    }

    companion object {
        @JvmStatic
        fun builder(): Builder = FactoryRegistry.instance.create()

        @JvmStatic
        operator fun get(key: NamespacedKey): Element? = CatalogRegistry.instance[key]

        @JvmStatic
        val all: Collection<Element>
            get() = CatalogRegistry.instance.getAllOf()
    }
}