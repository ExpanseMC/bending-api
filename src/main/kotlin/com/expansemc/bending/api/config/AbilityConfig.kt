package com.expansemc.bending.api.config

import com.expansemc.bending.api.ability.Ability
import com.expansemc.bending.api.ability.AbilityType
import com.expansemc.bending.api.registry.*
import com.expansemc.bending.api.util.NamespacedKeys
import ninja.leaping.configurate.ConfigurationNode
import org.bukkit.NamespacedKey

/**
 * Provides a mapping of [AbilityType]s to [ConfigurationNode]s.
 */
interface AbilityConfig : CatalogType {

    /**
     * Gets a [ConfigurationNode] used to construct an [Ability] from the provided [AbilityType].
     *
     * @param type The provided ability type
     * @return The configuration node, if available
     */
    fun provide(type: AbilityType): ConfigurationNode?

    operator fun contains(type: AbilityType): Boolean = provide(type) != null

    fun load(type: AbilityType): Ability? = provide(type)?.let(type::load)

    interface Builder {
        fun key(key: NamespacedKey): Builder

        fun provider(provider: (AbilityType) -> ConfigurationNode?): Builder

        fun build(): AbilityConfig
    }

    companion object {
        @JvmStatic
        fun builder(): Builder = FactoryRegistry.instance.create()

        @JvmStatic
        operator fun get(key: NamespacedKey): AbilityConfig? = CatalogRegistry.instance[key]

        val default: AbilityConfig? = CatalogRegistry.instance[NamespacedKeys.bending("default")]

        @JvmStatic
        val all: Collection<AbilityConfig>
            get() = CatalogRegistry.instance.getAllOf()
    }
}