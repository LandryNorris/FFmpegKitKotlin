package com.example.ffmpegkit

import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.sessions.FFprobeSession

expect object FFmpegKitConfig {

    fun messagesInTransmit(id: Long): Int

    fun enableRedirection()

    fun disableRedirection()

    fun setFontconfigConfigurationPath(path: String): Int

    fun setFontDirectory(context: Any, path: String, fontNameMapping: Map<String, String>)

    fun registerNewFFmpegPipe(context: Any): String

    fun closeFFmpegPipe(ffmpegPipePath: String)

    fun getFFmpegVersion(): String

    fun getVersion(): String

    fun isLTSBuild(): Boolean

    fun getBuildDate(): String

    fun setEnvironmentVariable(name: String, value: String): Int

    fun ignoreSignal(signal: Signal)

    fun ffmpegExecute(session: FFmpegSession)

    fun cancelSession(id: Long)

    fun enableLogCallback(logCallback: LogCallback?)

    fun enableStatisticsCallback(statisticsCallback: StatisticsCallback?)

    fun ffprobeExecute(session: FFprobeSession)

    fun setLogLevel(level: Level)

    fun getPlatform(): String

    fun getLogRedirectionStrategy(): LogRedirectionStrategy

    fun setLogRedirectionStrategy(strategy: LogRedirectionStrategy)
}

fun FFmpegKitConfig.sessionStateToString(state: SessionState) = when(state) {
    is SessionState.RUNNING -> "RUNNING"
    is SessionState.CREATED -> "CREATED"
    is SessionState.FAILED -> "FAILED"
    is SessionState.COMPLETED -> "COMPLETED"
}

var logCallback: LogCallback? = null
var globalLogRedirectionStrategy: LogRedirectionStrategy =
    LogRedirectionStrategy.PrintLogsWhenNoCallbackDefined
