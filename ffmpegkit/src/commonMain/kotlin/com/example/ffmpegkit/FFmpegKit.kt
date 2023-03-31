package com.example.ffmpegkit

import com.example.ffmpegkit.FFmpegKitConfig.ffmpegExecute
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

object FFmpegKit {
    private val sessions = mutableListOf<FFmpegSession>()

    fun executeWithArgumentsBlocking(arguments: List<String>,
                                     logCallback: LogCallback? = null,
                                     statisticsCallback: StatisticsCallback? = null
    ): FFmpegSession {
        val session = FFmpegSession(arguments, logCallback, statisticsCallback = statisticsCallback)
        sessions.add(session)
        ffmpegExecute(session)
        return session
    }

    fun executeBlocking(command: String,
                        logCallback: LogCallback? = null,
                        statisticsCallback: StatisticsCallback? = null): FFmpegSession {
        return executeWithArgumentsBlocking(parseArguments(command),
            logCallback, statisticsCallback)
    }

    /**
     * Execute a ffmpeg command using coroutines.
     */
    suspend fun executeWithArguments(arguments: List<String>,
                                     logCallback: LogCallback? = null,
                                     statisticsCallback: StatisticsCallback? = null
    ): FFmpegSession {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                val session = executeWithArgumentsBlocking(arguments, logCallback,
                    statisticsCallback)
                continuation.resumeWith(Result.success(session))
            }
        }
    }

    suspend fun execute(command: String,
                        logCallback: LogCallback? = null,
                        statisticsCallback: StatisticsCallback? = null): FFmpegSession {
        return executeWithArguments(parseArguments(command), logCallback, statisticsCallback)
    }

    fun listFFmpegSessions() = FFmpegKitConfig.ffmpegSessions

    fun cancel(id: Long = 0) = FFmpegKitConfig.cancelSession(id)

    fun listSessions(): List<FFmpegSession> {
        return sessions
    }
}

fun parseArguments(command: String): List<String> {
    val argumentList: MutableList<String> = ArrayList()
    var currentArgument = StringBuilder()
    var singleQuoteStarted = false
    var doubleQuoteStarted = false
    for (i in command.indices) {
        val previousChar = if (i > 0) {
            command[i - 1]
        } else {
            null
        }
        val currentChar = command[i]
        if (currentChar == ' ') {
            if (singleQuoteStarted || doubleQuoteStarted) {
                currentArgument.append(currentChar)
            } else if (currentArgument.isNotEmpty()) {
                argumentList.add(currentArgument.toString())
                currentArgument = StringBuilder()
            }
        } else if (currentChar == '\'' && (previousChar == null || previousChar != '\\')) {
            if (singleQuoteStarted) {
                singleQuoteStarted = false
            } else if (doubleQuoteStarted) {
                currentArgument.append(currentChar)
            } else {
                singleQuoteStarted = true
            }
        } else if (currentChar == '\"' && (previousChar == null || previousChar != '\\')) {
            if (doubleQuoteStarted) {
                doubleQuoteStarted = false
            } else if (singleQuoteStarted) {
                currentArgument.append(currentChar)
            } else {
                doubleQuoteStarted = true
            }
        } else {
            currentArgument.append(currentChar)
        }
    }
    if (currentArgument.isNotEmpty()) {
        argumentList.add(currentArgument.toString())
    }
    return argumentList
}
