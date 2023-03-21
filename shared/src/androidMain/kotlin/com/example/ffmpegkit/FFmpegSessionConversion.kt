package com.example.ffmpegkit

import com.arthenica.ffmpegkit.Statistics as FFmpegStatistics
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import com.arthenica.ffmpegkit.StatisticsCallback as FFmpegStatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.stats.Statistics
import com.arthenica.ffmpegkit.FFmpegSession as FFmpeg
import com.arthenica.ffmpegkit.LogCallback as FFmpegLogCallback
import com.arthenica.ffmpegkit.Log as FFmpegLog

fun FFmpeg.toShared() = FFmpegSession(arguments.asList(), logCallback.toShared())

fun FFmpegLogCallback.toShared() = LogCallback { log -> this@toShared.apply(log.toPlatform()) }

fun LogCallback.toPlatform() = FFmpegLogCallback { log ->
    log?.toShared()?.let { this@toPlatform.onLog(it) }
}

fun StatisticsCallback.toPlatform() = FFmpegStatisticsCallback { statistics ->
    statistics?.toShared()?.let { onStatistics(it) }
}

fun FFmpegStatisticsCallback.toShared() =
    StatisticsCallback { statistics -> this@toShared.apply(statistics.toPlatform()) }

fun FFmpegLog.toShared() = Log(sessionId, levelFrom(level.value), message)

fun Log.toPlatform() = FFmpegLog(id, com.arthenica.ffmpegkit.Level.from(level.value), message)

fun Statistics.toPlatform() = FFmpegStatistics(sessionId, videoFrameNumber, videoFps, videoQuality,
    size, time, bitrate, speed)

fun FFmpegStatistics.toShared() = Statistics(sessionId, videoFrameNumber, videoFps, videoQuality,
    size, time, bitrate, speed)
