package com.example.ffmpegkit.sessions

actual typealias PlatformSessionClass = com.arthenica.ffmpegkit.Session

actual class PlatformSession actual constructor(val platform: PlatformSessionClass) {
    actual fun thereAreAsynchronousMessagesInTransmit() =
        platform.thereAreAsynchronousMessagesInTransmit()
}