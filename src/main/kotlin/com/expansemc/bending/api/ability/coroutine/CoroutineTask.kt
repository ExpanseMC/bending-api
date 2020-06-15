package com.expansemc.bending.api.ability.coroutine

import com.expansemc.bending.api.ability.*
import com.expansemc.bending.api.util.isStale
import org.bukkit.plugin.Plugin
import kotlin.coroutines.*

@RestrictsSuspension
class CoroutineTask(val task: AbilityTask, private val plugin: Plugin) : Continuation<Unit> {

    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    private var scheduler: TaskScheduler =
        DelayedTaskScheduler(this.task, this.plugin)

    suspend fun start(): Unit = suspendCoroutine { cont ->
        this.scheduler.forceNewTask(fun() {
            cont.resume(Unit)
        })
    }

    internal fun cleanup() {
        this.task.cancel()
    }

    override fun resumeWith(result: Result<Unit>) {
        cleanup()
        result.getOrThrow()
    }

    suspend fun delay(ticks: Long): Unit = suspendCoroutine { cont ->
        this.scheduler.delay(ticks, fun() {
            cont.resume(Unit)
        })
    }

    suspend fun yield(): Unit = suspendCoroutine { cont ->
        this.scheduler.yield(fun() {
            cont.resume(Unit)
        })
    }

    suspend fun repeating(tickInterval: Long): Unit = suspendCoroutine { cont ->
        this.scheduler = RepeatingTaskScheduler(this.task, this.plugin, tickInterval)
        this.scheduler.forceNewTask(fun() {
            cont.resume(Unit)
        })
    }

    suspend fun nonRepeating(): Unit = suspendCoroutine { cont ->
        this.scheduler = DelayedTaskScheduler(this.task, this.plugin)
        this.scheduler.forceNewTask(fun() {
            cont.resume(Unit)
        })
    }

    /**
     * Loops until the ability finishes, or until the ability loops [MAX_TICKS] times.
     */
    suspend inline fun abilityLoop(block: () -> Unit) {
        repeating(AbilityService.instance.tickDelay)
        for (tick: Int in 0 until MAX_TICKS) {
            if (this.task.player.isStale) {
                // End if the Player object is stale.
                break
            }

            block()
            yield()
        }
        nonRepeating()
    }

    /**
     * Loops indefinitely until the block returns from the parent function or throws an exception.
     * Use with care, otherwise this can cause the main thread to halt.
     */
    suspend inline fun abilityLoopUnsafe(block: () -> Unit): Nothing {
        repeating(AbilityService.instance.tickDelay)
        while (true) {
            block()
            yield()
        }
    }

    companion object {
        @PublishedApi
        internal const val MAX_TICKS: Int = 10000
    }
}
