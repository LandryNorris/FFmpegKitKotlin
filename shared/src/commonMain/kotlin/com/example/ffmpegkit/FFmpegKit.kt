package com.example.ffmpegkit

import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession

expect object FFmpegKit {
    fun executeWithArgumentsBlocking(arguments: List<String>): FFmpegSession
    suspend fun executeWithArguments(arguments: List<String>): FFmpegSession
    suspend fun executeWithArguments(arguments: List<String>,
                                     logCallback: LogCallback,
                                     statisticsCallback: StatisticsCallback): FFmpegSession
    fun executeBlocking(command: String): FFmpegSession
    suspend fun execute(command: String): FFmpegSession
    suspend fun execute(command: String,
                        logCallback: LogCallback,
                        statisticsCallback: StatisticsCallback): FFmpegSession
    fun cancel(id: Long = 0)
    fun listSessions(): List<FFmpegSession>
}