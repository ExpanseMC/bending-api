package com.expansemc.bending.api.bender

import com.expansemc.bending.api.ability.Ability
import com.expansemc.bending.api.ability.AbilityExecutionType
import com.expansemc.bending.api.ability.AbilityTask
import com.expansemc.bending.api.ability.AbilityType
import com.expansemc.bending.api.element.Element
import com.expansemc.bending.api.util.HotbarList
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

interface Bender {

    val uniqueId: UUID

    val player: Player?

    val equipped: HotbarList<Ability>

    var selected: Ability?

    val passives: Collection<Ability>

    fun getPassive(type: AbilityType): Ability?

    fun addPassive(ability: Ability): Boolean

    fun removePassive(ability: Ability): Boolean

    val running: Collection<AbilityTask>

    fun execute(ability: Ability, executionType: AbilityExecutionType): AbilityTask?

    fun waitForExecution(type: AbilityType, executionType: AbilityExecutionType): CompletableFuture<Void?>

    fun cancel(type: AbilityType): Boolean

    fun hasCooldown(type: AbilityType): Boolean

    fun setCooldown(type: AbilityType, milliseconds: Long): Boolean

    fun removeCooldown(type: AbilityType): Long

    val elements: Collection<Element>

    fun addElement(element: Element): Boolean

    fun removeElement(element: Element): Boolean
}