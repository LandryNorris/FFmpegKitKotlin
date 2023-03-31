package com.example.ffmpegkit

actual object ArchDetect {
    actual fun getArch() = cocoapods.ffmpegkit.ArchDetect.getArch() ?: ""
}
