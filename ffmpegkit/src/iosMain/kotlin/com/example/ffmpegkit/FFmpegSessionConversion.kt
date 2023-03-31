package com.example.ffmpegkit

import cocoapods.ffmpegkit.ReturnCode
import cocoapods.ffmpegkit.Statistics as FFmpegStatistics
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import cocoapods.ffmpegkit.StatisticsCallback as FFmpegStatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.sessions.FFprobeSession
import com.example.ffmpegkit.stats.Statistics
import cocoapods.ffmpegkit.FFmpegSession as FFmpeg
import cocoapods.ffmpegkit.LogCallback as FFmpegLogCallback
import cocoapods.ffmpegkit.Log as FFmpegLog

fun FFmpeg.toShared() = FFmpegSession(getArguments()?.filterIsInstance<String>()
    ?: listOf(), getLogCallback().toShared())

fun FFmpegSession.toPlatform() = cocoapods.ffmpegkit.FFmpegSession
    .create(arguments, {},
        { log ->
            val commonLog = log?.toShared()
            commonLog?.let {
                addLog(it)
                logCallback?.onLog(it)
            }
        },
        statisticsCallback?.toPlatform())

fun FFprobeSession.toPlatform() = cocoapods.ffmpegkit.FFprobeSession
    .create(arguments, {}, { log ->
        val commonLog = log?.toShared()
        commonLog?.let {
            addLog(it)
            logCallback?.onLog(it)
        }
    })

fun cocoapods.ffmpegkit.LogRedirectionStrategy.toShared() = when(this) {
    cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyNeverPrintLogs -> LogRedirectionStrategy.NeverPrintLogs
    cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyAlwaysPrintLogs -> LogRedirectionStrategy.AlwaysPrintLogs
    cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyPrintLogsWhenGlobalCallbackNotDefined -> LogRedirectionStrategy.PrintLogsWhenGlobalCallbackNotDefined
    cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyPrintLogsWhenNoCallbacksDefined -> LogRedirectionStrategy.PrintLogsWhenNoCallbackDefined
    cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyPrintLogsWhenSessionCallbackNotDefined -> LogRedirectionStrategy.PrintLogsWhenSessionCallbackNotDefined
    else -> error("Invalid log redirection strategy $this")
}

fun LogRedirectionStrategy.toPlatform() = when(this) {
    LogRedirectionStrategy.NeverPrintLogs -> cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyNeverPrintLogs
    LogRedirectionStrategy.AlwaysPrintLogs -> cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyAlwaysPrintLogs
    LogRedirectionStrategy.PrintLogsWhenGlobalCallbackNotDefined -> cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyPrintLogsWhenGlobalCallbackNotDefined
    LogRedirectionStrategy.PrintLogsWhenNoCallbackDefined -> cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyPrintLogsWhenNoCallbacksDefined
    LogRedirectionStrategy.PrintLogsWhenSessionCallbackNotDefined -> cocoapods.ffmpegkit.LogRedirectionStrategy.LogRedirectionStrategyPrintLogsWhenSessionCallbackNotDefined
}

fun FFmpegLogCallback.toShared() = LogCallback { log -> this@toShared?.invoke(log.toPlatform()) }

fun LogCallback.toPlatform(): FFmpegLogCallback =
    { log -> log?.toShared()?.let { it -> onLog(it) }
}

fun StatisticsCallback.toPlatform(): FFmpegStatisticsCallback =
    { stats -> stats?.toShared()?.let { onStatistics(it) } }

fun FFmpegStatisticsCallback.toShared() =
    StatisticsCallback { statistics -> this@toShared?.invoke(statistics.toPlatform()) }

fun FFmpegLog.toShared() = Log(getSessionId(), levelFrom(getLevel()), getMessage() ?: "")

fun Log.toPlatform() = FFmpegLog(id, level.value, message)

fun Statistics.toPlatform() = FFmpegStatistics(sessionId, videoFrameNumber, videoFps, videoQuality,
    size, time, bitrate, speed)

fun FFmpegStatistics.toShared() = Statistics(getSessionId(), getVideoFrameNumber(), getVideoFps(),
    getVideoQuality(), getSize(), getTime(), getBitrate(), getSpeed())

fun ReturnCode.toShared() = com.example.ffmpegkit.ReturnCode(getValue())
