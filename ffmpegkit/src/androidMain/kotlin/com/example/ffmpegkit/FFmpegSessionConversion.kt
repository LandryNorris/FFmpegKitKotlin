package com.example.ffmpegkit

import com.arthenica.ffmpegkit.Statistics as FFmpegStatistics
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import com.arthenica.ffmpegkit.StatisticsCallback as FFmpegStatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.stats.Statistics
import kotlin.math.log
import com.arthenica.ffmpegkit.FFmpegSession as FFmpeg
import com.arthenica.ffmpegkit.LogCallback as FFmpegLogCallback
import com.arthenica.ffmpegkit.Log as FFmpegLog

fun FFmpegSession.toPlatform() = com.arthenica.ffmpegkit.FFmpegSession
    .create(arguments.toTypedArray(), {}, { log ->
        val commonLog = log.toShared()
        addLog(commonLog)
        logCallback?.onLog(commonLog)
    }, statisticsCallback?.toPlatform())

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

fun ReturnCode.toPlatform() = com.arthenica.ffmpegkit.ReturnCode(value)

fun com.arthenica.ffmpegkit.ReturnCode.toShared() = ReturnCode(value)
