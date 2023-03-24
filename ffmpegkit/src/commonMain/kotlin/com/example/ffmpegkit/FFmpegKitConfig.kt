package com.example.ffmpegkit

import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.sessions.FFmpegSession

expect fun messagesInTransmit(id: Long): Int

expect fun enableRedirection()

expect fun ffmpegExecute(session: FFmpegSession)

expect fun cancelSession(id: Long)

expect fun enableLogCallback(logCallback: LogCallback)
