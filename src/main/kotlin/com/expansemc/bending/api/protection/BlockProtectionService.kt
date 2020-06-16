package com.expansemc.bending.api.protection

import com.expansemc.bending.api.util.provide
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

interface BlockProtectionService {

    fun isProtected(source: Player, target: Location): Boolean

    companion object {
        @JvmStatic
        val instance: BlockProtectionService
            get() = Bukkit.getServicesManager().provide()!!
    }
}