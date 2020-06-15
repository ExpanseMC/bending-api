package com.expansemc.bending.api.registry

import org.bukkit.Bukkit
import java.util.*

interface FactoryRegistry {
    fun <T: Any> register(type: Class<T>, supplier: () -> T): FactoryRegistry

    @Throws(NoSuchElementException::class)
    fun <T: Any> create(type: Class<T>): T

    companion object {
        // TODO: better null handling
        @JvmStatic
        val instance: FactoryRegistry
            get() =  Bukkit.getServicesManager().getRegistration(FactoryRegistry::class.java)!!.provider
    }
}

inline fun <reified T : Any> FactoryRegistry.register(noinline supplier: () -> T): FactoryRegistry =
    this.register(T::class.java, supplier)

@Throws(NoSuchElementException::class)
inline fun <reified T : Any> FactoryRegistry.create(): T =
    this.create(T::class.java)