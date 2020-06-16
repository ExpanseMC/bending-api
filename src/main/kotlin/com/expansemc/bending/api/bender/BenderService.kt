package com.expansemc.bending.api.bender

import com.expansemc.bending.api.util.provide
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

interface BenderService {
    val benders: Collection<Bender>

    fun getBender(uniqueId: UUID): Bender?

    fun getOrCreateBender(player: Player): Bender

    companion object {
        // TODO: better null handling
        @JvmStatic
        val instance: BenderService
            get() = Bukkit.getServicesManager().provide()!!
    }
}