package com.example.ffmpegkit

import cocoapods.ffmpegkit.FFmpegKitConfig

actual fun messagesInTransmit(id: Long): Int {
    return FFmpegKitConfig.messagesInTransmit(id)
}
