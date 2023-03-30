package com.example.ffmpegkit

import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.arthenica.ffmpegkit.FFprobeKit
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.sessions.FFprobeSession
import com.example.ffmpegkit.sessions.MediaInformationSession
import kotlinx.coroutines.runBlocking

actual fun messagesInTransmit(id: Long): Int {
    return FFmpegKitConfig.messagesInTransmit(id)
}

actual fun enableRedirection() = FFmpegKitConfig.enableRedirection()

actual fun ffmpegExecute(session: FFmpegSession) {
    val platformSession = session.toPlatform()
    session.startRunning()
    FFmpegKitConfig.ffmpegExecute(platformSession)
    session.complete(platformSession.returnCode.toShared())
}

actual fun cancelSession(id: Long) = com.arthenica.ffmpegkit.FFmpegKit.cancel(id)

actual fun enableLogCallback(logCallback: LogCallback) =
    FFmpegKitConfig.enableLogCallback {
        logCallback.onLog(it.toShared())
    }

actual fun ffprobeExecute(session: FFprobeSession) {
    val platformSession = session.toPlatform()
    session.startRunning()
    FFmpegKitConfig.ffprobeExecute(platformSession)
    session.complete(platformSession.returnCode.toShared())
}
