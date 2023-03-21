package com.example.ffmpegkit

import cocoapods.ffmpegkit.Level
import cocoapods.ffmpegkit.Statistics as FFmpegStatistics
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import cocoapods.ffmpegkit.StatisticsCallback as FFmpegStatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.stats.Statistics
import cocoapods.ffmpegkit.FFmpegSession as FFmpeg
import cocoapods.ffmpegkit.LogCallback as FFmpegLogCallback
import cocoapods.ffmpegkit.Log as FFmpegLog

fun FFmpeg.toShared() = FFmpegSession(getArguments()?.filterIsInstance<String>()
    ?: listOf(), getLogCallback().toShared())

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
