package com.expansemc.bending.api.protection

import com.expansemc.bending.api.ability.AbilityType
import com.expansemc.bending.api.registry.*
import com.expansemc.bending.api.util.Tristate
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player

/**
 * A build protection checker.
 */
interface BlockProtection : CatalogType {

    fun isProtected(source: Player, target: Location): Tristate

    interface Builder {

        fun key(key: NamespacedKey): Builder

        fun protected(predicate: (Player, Location) -> Tristate): Builder

        fun build(): BlockProtection
    }

    companion object {
        @JvmStatic
        fun builder(): Builder = FactoryRegistry.instance.create()

        @JvmStatic
        operator fun get(key: NamespacedKey): BlockProtection? = CatalogRegistry.instance[key]

        @JvmStatic
        val all: Collection<AbilityType>
            get() = CatalogRegistry.instance.getAllOf()
    }
}