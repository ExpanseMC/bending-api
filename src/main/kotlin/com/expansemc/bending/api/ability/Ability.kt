package com.expansemc.bending.api.ability

interface Ability {
    val type: AbilityType

    /**
     * The amount of time (in milliseconds) that a bender must wait
     * before they can use the ability again.
     */
    val cooldown: Long

    fun validate(context: AbilityContext, executionType: AbilityExecutionType): Boolean = true

    fun execute(context: AbilityContext, executionType: AbilityExecutionType, task: AbilityTask)

    fun cleanup(context: AbilityContext, executionType: AbilityExecutionType) {}
}