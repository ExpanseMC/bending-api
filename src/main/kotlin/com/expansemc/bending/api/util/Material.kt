package com.expansemc.bending.api.util

import org.bukkit.Material

inline val Material.isLiquid: Boolean
    get() = this.isWater || this.isLava

inline val Material.isWater: Boolean
    get() = this == Material.WATER

inline val Material.isLava: Boolean
    get() = this == Material.LAVA

inline val Material.isSnow: Boolean
    get() = this == Material.SNOW || this == Material.SNOW_BLOCK