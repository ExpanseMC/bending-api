package com.expansemc.bending.api.protection

import com.expansemc.bending.api.ability.AbilityType
import com.expansemc.bending.api.registry.*
import com.expansemc.bending.api.util.Tristate
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface EntityProtection : CatalogType {

    fun isProtected(source: Player, target: Entity): Tristate

    interface Builder {

        fun key(key: NamespacedKey): Builder

        fun protected(predicate: (Player, Entity) -> Tristate): Builder

        fun build(): EntityProtection
    }

    companion object {
        @JvmStatic
        fun builder(): Builder = FactoryRegistry.instance.create()

        @JvmStatic
        operator fun get(key: NamespacedKey): EntityProtection? = CatalogRegistry.instance[key]

        @JvmStatic
        val all: Collection<AbilityType>
            get() = CatalogRegistry.instance.getAllOf()
    }
}