package com.expansemc.bending.api.ability

import com.expansemc.bending.api.registry.CatalogRegistry
import com.expansemc.bending.api.registry.getLazy
import com.expansemc.bending.api.util.NamespacedKeys.bending

object AbilityExecutionTypes {
    @JvmStatic
    val FALL: AbilityExecutionType by CatalogRegistry.instance.getLazy(bending("fall"))

    @JvmStatic
    val JUMP: AbilityExecutionType by CatalogRegistry.instance.getLazy(bending("jump"))

    @JvmStatic
    val LEFT_CLICK: AbilityExecutionType by CatalogRegistry.instance.getLazy(bending("left_click"))

    @JvmStatic
    val PASSIVE: AbilityExecutionType by CatalogRegistry.instance.getLazy(bending("passive"))

    @JvmStatic
    val SNEAK: AbilityExecutionType by CatalogRegistry.instance.getLazy(bending("sneak"))

    @JvmStatic
    val SPRINT_OFF: AbilityExecutionType by CatalogRegistry.instance.getLazy(bending("sprint_off"))

    @JvmStatic
    val SPRINT_ON: AbilityExecutionType by CatalogRegistry.instance.getLazy(bending("sprint_on"))
}