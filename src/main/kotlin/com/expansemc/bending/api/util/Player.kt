package com.expansemc.bending.api.util

import org.bukkit.entity.Player

inline val Player.isStale: Boolean
    get() = this.isDead || !this.isOnline