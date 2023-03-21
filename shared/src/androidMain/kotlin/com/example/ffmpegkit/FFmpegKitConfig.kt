package com.example.ffmpegkit

import com.arthenica.ffmpegkit.FFmpegKitConfig

actual fun messagesInTransmit(id: Long): Int {
    return FFmpegKitConfig.messagesInTransmit(id)
}
