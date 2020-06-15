package com.expansemc.bending.api.ability

import com.expansemc.bending.api.bender.Bender
import com.expansemc.bending.api.registry.CatalogRegistry
import com.expansemc.bending.api.registry.getLazy
import com.expansemc.bending.api.util.NamespacedKeys.bending
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

object AbilityContextKeys {
    @JvmStatic
    val AFFECTED_LOCATIONS: AbilityContextKey<Collection<Location>>
            by CatalogRegistry.instance.getLazy(bending("affected_locations"))

    @JvmStatic
    val AFFECTED_ENTITIES: AbilityContextKey<Collection<Entity>>
            by CatalogRegistry.instance.getLazy(bending("affected_entities"))

    @JvmStatic
    val BENDER: AbilityContextKey<Bender>
            by CatalogRegistry.instance.getLazy(bending("bender"))

    @JvmStatic
    val CURRENT_LOCATION: AbilityContextKey<Location>
            by CatalogRegistry.instance.getLazy(bending("current_location"))

    @JvmStatic
    val DIRECTION: AbilityContextKey<Vector>
            by CatalogRegistry.instance.getLazy(bending("direction"))

    @JvmStatic
    val EXECUTION_TYPE: AbilityContextKey<AbilityExecutionType>
            by CatalogRegistry.instance.getLazy(bending("execution_type"))

    @JvmStatic
    val FALL_DISTANCE: AbilityContextKey<Float>
            by CatalogRegistry.instance.getLazy(bending("fall_distance"))

    @JvmStatic
    val ORIGIN: AbilityContextKey<Location>
            by CatalogRegistry.instance.getLazy(bending("origin"))

    @JvmStatic
    val PLAYER: AbilityContextKey<Player>
            by CatalogRegistry.instance.getLazy(bending("player"))
}