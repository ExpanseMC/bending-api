package com.expansemc.bending.api.util

import org.bukkit.plugin.ServicesManager

fun <T> ServicesManager.provide(service: Class<T>): T? = this.getRegistration(service)?.provider

inline fun <reified T> ServicesManager.provide(): T? = this.provide(T::class.java)