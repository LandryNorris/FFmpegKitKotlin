package com.example.ffmpegkit.sessions

expect interface PlatformSessionClass

expect class PlatformSession(platform: PlatformSessionClass) {
    fun thereAreAsynchronousMessagesInTransmit(): Boolean
}