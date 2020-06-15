package com.expansemc.bending.api.registry

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey

interface CatalogRegistry {

    operator fun <T : CatalogType> get(type: Class<T>, key: NamespacedKey): T?

    fun <T: NamedCatalogType> getByName(type: Class<T>, name: String, ignoreCase: Boolean = false): Collection<T>

    fun <T: CatalogType> getLazy(type: Class<T>, key: NamespacedKey): Lazy<T> = lazy {
        this[type, key] ?: throw NoSuchElementException("No ${type.name} is registered for $key")
    }

    fun <T : CatalogType> getAllOf(type: Class<T>): Collection<T>

    fun <T : CatalogType> register(type: Class<T>, catalogType: T): CatalogRegistry

    fun <T : CatalogType> registerAll(type: Class<T>, vararg catalogTypes: T): CatalogRegistry {
        for (catalogType in catalogTypes) {
            this.register(type, catalogType)
        }
        return this
    }

    companion object {
        // TODO: better null handling
        @JvmStatic
        val instance: CatalogRegistry
            get() = Bukkit.getServicesManager().getRegistration(CatalogRegistry::class.java)!!.provider
    }
}

inline operator fun <reified T : CatalogType> CatalogRegistry.get(key: NamespacedKey): T? =
    this[T::class.java, key]

inline fun <reified T: NamedCatalogType> CatalogRegistry.getByName(name: String, ignoreCase: Boolean = false): Collection<T> =
    this.getByName(T::class.java, name, ignoreCase)

inline fun <reified T : CatalogType> CatalogRegistry.getLazy(key: NamespacedKey): Lazy<T> =
    this.getLazy(T::class.java, key)

inline fun <reified T : CatalogType> CatalogRegistry.getAllOf(): Collection<T> =
    this.getAllOf(T::class.java)

inline fun <reified T : CatalogType> CatalogRegistry.register(catalogType: T): CatalogRegistry =
    this.register(T::class.java, catalogType)

inline fun <reified T : CatalogType> CatalogRegistry.registerAll(vararg catalogTypes: T): CatalogRegistry =
    this.registerAll(T::class.java, *catalogTypes)