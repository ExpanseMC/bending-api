package com.expansemc.bending.api.util

import com.expansemc.bending.api.BendingService
import org.bukkit.NamespacedKey

object NamespacedKeys {
    @JvmStatic
    fun bending(key: String): NamespacedKey {
        return NamespacedKey(BendingService.instance.plugin, key)
    }

    @JvmStatic
    fun of(raw: String, defaultNamespace: String = BendingService.instance.plugin.name): NamespacedKey {
        if (':' in raw) {
            val (namespace, key) = raw.split(':', limit = 2)
            @Suppress("DEPRECATION")
            return NamespacedKey(namespace.toLowerCase(), key.toLowerCase())
        } else {
            @Suppress("DEPRECATION")
            return NamespacedKey(defaultNamespace.toLowerCase(), raw.toLowerCase())
        }
    }
}