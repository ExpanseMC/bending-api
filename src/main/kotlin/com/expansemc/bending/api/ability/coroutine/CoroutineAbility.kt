package com.expansemc.bending.api.ability.coroutine

import com.expansemc.bending.api.ability.Ability
import com.expansemc.bending.api.ability.AbilityContext
import com.expansemc.bending.api.ability.AbilityExecutionType
import com.expansemc.bending.api.ability.AbilityTask
import org.bukkit.plugin.Plugin
import kotlin.coroutines.startCoroutine

interface CoroutineAbility : Ability {

    val plugin: Plugin

    suspend fun CoroutineTask.activate(context: AbilityContext, executionType: AbilityExecutionType)

    override fun execute(context: AbilityContext, executionType: AbilityExecutionType, task: AbilityTask) {
        val coroutine = CoroutineTask(task, this.plugin)
        val block: suspend CoroutineTask.() -> Unit = {
            try {
                this.start()
                this.activate(context, executionType)
            } finally {
                this.cleanup()
            }
        }
        block.startCoroutine(coroutine, coroutine)
    }
}