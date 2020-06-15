package com.expansemc.bending.api.ability.coroutine

import com.expansemc.bending.api.ability.AbilityTask
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

internal interface TaskScheduler {

    val task: AbilityTask

    val plugin: Plugin

    fun delay(ticks: Long, block: () -> Unit)

    fun yield(block: () -> Unit)

    fun forceNewTask(block: () -> Unit)
}

internal class DelayedTaskScheduler(override val task: AbilityTask, override val plugin: Plugin) :
    TaskScheduler {

    override fun delay(ticks: Long, block: () -> Unit) {
        val task: BukkitTask = Bukkit.getScheduler().runTaskLater(this.plugin, block, ticks)
        this.task.currentTask = task
    }

    override fun yield(block: () -> Unit) {
        this.forceNewTask(block)
    }

    override fun forceNewTask(block: () -> Unit) {
        val task: BukkitTask = Bukkit.getScheduler().runTask(this.plugin, block)
        this.task.currentTask = task
    }
}

internal class RepeatingTaskScheduler(
    override val task: AbilityTask,
    override val plugin: Plugin,
    private val tickInterval: Long
) : TaskScheduler {

    private var next: RepeatingContinuation? = null

    override fun delay(ticks: Long, block: () -> Unit) {
        this.next = RepeatingContinuation(block, ticks)
    }

    override fun yield(block: () -> Unit) {
        this.next = RepeatingContinuation(block, 0)
    }

    override fun forceNewTask(block: () -> Unit) {
        this.yield(block)
        val task: BukkitTask = Bukkit.getScheduler()
            .runTaskTimer(this.plugin, Runnable { this.next?.resume(this.tickInterval) }, 0, this.tickInterval)
        this.task.currentTask = task
    }

    private class RepeatingContinuation(val block: () -> Unit, val ticks: Long) {
        private var passedTicks: Long = 0L
        private var resumed: Boolean = false

        fun resume(passedTicks: Long) {
            check(!this.resumed) { "Already resumed" }
            this.passedTicks += passedTicks
            if (this.passedTicks >= this.ticks) {
                this.resumed = true
                this.block()
            }
        }
    }
}