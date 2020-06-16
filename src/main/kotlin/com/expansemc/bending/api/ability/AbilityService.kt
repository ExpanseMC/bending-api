package com.expansemc.bending.api.ability

import com.expansemc.bending.api.util.provide
import org.bukkit.Bukkit

interface AbilityService {
    val tickDelay: Long

    companion object {
        // TODO: better null handling
        @JvmStatic
        val instance: AbilityService
            get() = Bukkit.getServicesManager().provide()!!
    }
}