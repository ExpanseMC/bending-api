package com.expansemc.bending.api

import com.expansemc.bending.api.util.provide
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

interface BendingService {
    val plugin: Plugin

    companion object {
        // TODO: better null handling
        @JvmStatic
        val instance: BendingService
            get() = Bukkit.getServicesManager().provide<BendingService>()!!
    }
}