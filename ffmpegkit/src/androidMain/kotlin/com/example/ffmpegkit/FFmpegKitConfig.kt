package com.example.ffmpegkit

import android.content.Context
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.sessions.FFprobeSession

actual object FFmpegKitConfig {

    actual fun messagesInTransmit(id: Long): Int {
        return FFmpegKitConfig.messagesInTransmit(id)
    }

    actual fun enableRedirection() = FFmpegKitConfig.enableRedirection()

    actual fun disableRedirection() = FFmpegKitConfig.disableRedirection()

    actual fun setFontconfigConfigurationPath(path: String) = FFmpegKitConfig.setFontconfigConfigurationPath(path)

    actual fun setFontDirectory(context: Any, path: String, fontNameMapping: Map<String, String>) =
        FFmpegKitConfig.setFontDirectory(context as Context, path, fontNameMapping)

    actual fun registerNewFFmpegPipe(context: Any) =
        FFmpegKitConfig.registerNewFFmpegPipe(context as Context)

    actual fun closeFFmpegPipe(ffmpegPipePath: String) = FFmpegKitConfig.closeFFmpegPipe(ffmpegPipePath)

    actual fun getFFmpegVersion() = FFmpegKitConfig.getFFmpegVersion()

    actual fun getVersion() = FFmpegKitConfig.getVersion()

    actual fun isLTSBuild() = FFmpegKitConfig.isLTSBuild()

    actual fun getBuildDate() = FFmpegKitConfig.getBuildDate()

    actual fun setEnvironmentVariable(name: String, value: String) =
        FFmpegKitConfig.setEnvironmentVariable(name, value)

    actual fun ignoreSignal(signal: Signal) = FFmpegKitConfig.ignoreSignal(signal.toPlatform())

    private fun Signal.toPlatform() = when(this) {
        Signal.SigInt -> com.arthenica.ffmpegkit.Signal.SIGINT
        Signal.SigPipe -> com.arthenica.ffmpegkit.Signal.SIGPIPE
        Signal.SigQuit -> com.arthenica.ffmpegkit.Signal.SIGQUIT
        Signal.SigTerm -> com.arthenica.ffmpegkit.Signal.SIGTERM
        Signal.SigXCpu -> com.arthenica.ffmpegkit.Signal.SIGXCPU
    }

    actual fun ffmpegExecute(session: FFmpegSession) {
        val platformSession = session.toPlatform()
        session.startRunning()
        FFmpegKitConfig.ffmpegExecute(platformSession)
        session.complete(platformSession.returnCode.toShared())
    }

    actual fun cancelSession(id: Long) = com.arthenica.ffmpegkit.FFmpegKit.cancel(id)

    actual fun enableLogCallback(logCallback: LogCallback?) =
        FFmpegKitConfig.enableLogCallback {
            logCallback?.onLog(it.toShared())
        }

    actual fun enableStatisticsCallback(statisticsCallback: StatisticsCallback?) =
        FFmpegKitConfig.enableStatisticsCallback {
            statisticsCallback?.onStatistics(it.toShared())
        }

    actual fun ffprobeExecute(session: FFprobeSession) {
        val platformSession = session.toPlatform()
        session.startRunning()
        FFmpegKitConfig.ffprobeExecute(platformSession)
        session.complete(platformSession.returnCode.toShared())
    }

    actual fun setLogLevel(level: Level) = FFmpegKitConfig.setLogLevel(com.arthenica.ffmpegkit.Level.from(level.value))

    actual fun getPlatform() = "Android"

    actual fun getLogRedirectionStrategy() = FFmpegKitConfig.getLogRedirectionStrategy().toShared()

    actual fun setLogRedirectionStrategy(strategy: LogRedirectionStrategy) {
        FFmpegKitConfig.setLogRedirectionStrategy(strategy.toPlatform())
    }
}
