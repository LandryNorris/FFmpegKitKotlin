package com.example.ffmpegkit

import cocoapods.ffmpegkit.FFmpegKitConfig
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.sessions.FFprobeSession
import platform.posix.*

actual object FFmpegKitConfig {
    actual fun messagesInTransmit(id: Long): Int {
        return FFmpegKitConfig.messagesInTransmit(id)
    }

    actual fun enableRedirection() = FFmpegKitConfig.enableRedirection()

    actual fun disableRedirection() = FFmpegKitConfig.disableRedirection()

    actual fun setFontconfigConfigurationPath(path: String) = FFmpegKitConfig.setFontconfigConfigurationPath(path)

    actual fun setFontDirectory(context: Any, path: String, fontNameMapping: Map<String, String>) =
        FFmpegKitConfig.setFontDirectory(path, fontNameMapping.mapKeys { it })

    actual fun registerNewFFmpegPipe(context: Any) =
        FFmpegKitConfig.registerNewFFmpegPipe() ?: ""

    actual fun closeFFmpegPipe(ffmpegPipePath: String) = FFmpegKitConfig.closeFFmpegPipe(ffmpegPipePath)

    actual fun getFFmpegVersion() = FFmpegKitConfig.getFFmpegVersion() ?: ""

    actual fun getVersion() = FFmpegKitConfig.getVersion() ?: ""

    actual fun isLTSBuild() = FFmpegKitConfig.isLTSBuild() != 0

    actual fun getBuildDate() = FFmpegKitConfig.getBuildDate() ?: ""

    actual fun setEnvironmentVariable(name: String, value: String) =
        FFmpegKitConfig.setEnvironmentVariable(name, value)

    actual fun ignoreSignal(signal: Signal) =
        FFmpegKitConfig.ignoreSignal(signal.toPlatform().toULong())

    private fun Signal.toPlatform() = when(this) {
        Signal.SigInt -> SIGINT
        Signal.SigPipe -> SIGPIPE
        Signal.SigQuit -> SIGQUIT
        Signal.SigTerm -> SIGTERM
        Signal.SigXCpu -> SIGXCPU
    }

    actual fun ffmpegExecute(session: FFmpegSession) {
        val platformSession = session.toPlatform()
        session.startRunning()
        FFmpegKitConfig.ffmpegExecute(platformSession)
        session.complete(platformSession!!.getReturnCode()!!.toShared())
    }

    actual fun cancelSession(id: Long) = cocoapods.ffmpegkit.FFmpegKit.cancel(id)

    actual fun enableLogCallback(logCallback: LogCallback?) =
        FFmpegKitConfig.enableLogCallback(logCallback?.toPlatform())
    
    actual fun enableStatisticsCallback(statisticsCallback: StatisticsCallback?) =
        FFmpegKitConfig.enableStatisticsCallback(statisticsCallback?.toPlatform())

    actual fun ffprobeExecute(session: FFprobeSession) {
        val platformSession = session.toPlatform()
        session.startRunning()
        FFmpegKitConfig.ffprobeExecute(platformSession)
        session.complete(platformSession!!.getReturnCode()!!.toShared())
    }

    actual fun setLogLevel(level: Level) = FFmpegKitConfig.setLogLevel(level.value)

    actual fun getPlatform() = "iOS"

    actual fun getLogRedirectionStrategy() = FFmpegKitConfig.getLogRedirectionStrategy().toShared()

    actual fun setLogRedirectionStrategy(strategy: LogRedirectionStrategy) {
        FFmpegKitConfig.setLogRedirectionStrategy(strategy.toPlatform())
    }
}
