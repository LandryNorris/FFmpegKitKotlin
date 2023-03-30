package com.example.ffmpegkit

import cocoapods.ffmpegkit.FFmpegKitConfig
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.sessions.FFprobeSession

actual fun messagesInTransmit(id: Long): Int {
    return FFmpegKitConfig.messagesInTransmit(id)
}

actual fun enableRedirection() = FFmpegKitConfig.enableRedirection()

actual fun ffmpegExecute(session: FFmpegSession) {
    val platformSession = session.toPlatform()
    session.startRunning()
    FFmpegKitConfig.ffmpegExecute(platformSession)
    session.complete(platformSession!!.getReturnCode()!!.toShared())
}

actual fun cancelSession(id: Long) = cocoapods.ffmpegkit.FFmpegKit.cancel(id)

actual fun enableLogCallback(logCallback: LogCallback) =
    FFmpegKitConfig.enableLogCallback(logCallback.toPlatform())

actual fun ffprobeExecute(session: FFprobeSession) {
    val platformSession = session.toPlatform()
    session.startRunning()
    FFmpegKitConfig.ffprobeExecute(platformSession)
    session.complete(platformSession!!.getReturnCode()!!.toShared())
}
