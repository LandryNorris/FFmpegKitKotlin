package com.example.ffmpegkit.sessions

actual typealias PlatformSessionClass = cocoapods.ffmpegkit.SessionProtocol

actual class PlatformSession actual constructor(val platform: PlatformSessionClass) {
    actual fun thereAreAsynchronousMessagesInTransmit() =
        platform.thereAreAsynchronousMessagesInTransmit()
}