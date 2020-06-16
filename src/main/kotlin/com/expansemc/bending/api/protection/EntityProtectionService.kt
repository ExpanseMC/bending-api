package com.expansemc.bending.api.protection

import com.expansemc.bending.api.util.provide
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface EntityProtectionService {

    fun isProtected(source: Player, target: Entity): Boolean

    companion object {
        @JvmStatic
        val instance: EntityProtectionService
            get() = Bukkit.getServicesManager().provide()!!
    }
}