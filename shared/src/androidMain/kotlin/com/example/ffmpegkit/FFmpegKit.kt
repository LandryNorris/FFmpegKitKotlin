package com.example.ffmpegkit

import com.arthenica.ffmpegkit.FFmpegKit as FFmpeg
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import kotlin.coroutines.suspendCoroutine

actual object FFmpegKit {
    actual fun executeWithArgumentsBlocking(arguments: List<String>): FFmpegSession =
        FFmpeg.executeWithArguments(arguments.toTypedArray()).toShared()

    actual suspend fun executeWithArguments(arguments: List<String>): FFmpegSession =
        suspendCoroutine { cont ->
        FFmpeg.executeWithArgumentsAsync(arguments.toTypedArray()) {
            cont.resumeWith(Result.success(it.toShared()))
        }
    }

    actual suspend fun executeWithArguments(
        arguments: List<String>,
        logCallback: LogCallback,
        statisticsCallback: StatisticsCallback
    ): FFmpegSession = suspendCoroutine { cont ->
        FFmpeg.executeWithArgumentsAsync(
            arguments.toTypedArray(),
            { session -> cont.resumeWith(Result.success(session!!.toShared())) },
            logCallback.toPlatform(),
            statisticsCallback.toPlatform())
    }

    actual fun executeBlocking(command: String) = FFmpeg.execute(command).toShared()

    actual suspend fun execute(command: String) = suspendCoroutine { cont ->
        FFmpeg.executeAsync(command) {
            cont.resumeWith(Result.success(it.toShared()))
        }
    }

    actual suspend fun execute(
        command: String,
        logCallback: LogCallback,
        statisticsCallback: StatisticsCallback
    ) = suspendCoroutine { cont ->
        FFmpeg.executeAsync(command,
            { session -> cont.resumeWith(Result.success(session!!.toShared())) },
            logCallback.toPlatform(),
            statisticsCallback.toPlatform(),
        )
    }

    actual fun cancel(id: Long) = FFmpeg.cancel(id)

    actual fun listSessions(): List<FFmpegSession> = FFmpeg.listSessions().map { it.toShared() }
}