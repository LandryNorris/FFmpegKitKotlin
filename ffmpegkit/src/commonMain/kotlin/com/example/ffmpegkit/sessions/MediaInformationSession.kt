package com.example.ffmpegkit.sessions

import com.example.ffmpegkit.callbacks.LogCallback

class MediaInformationSession(arguments: List<String>,
                              logCallback: LogCallback? = null
): AbstractSession(arguments, logCallback) {

    override val isFFmpeg = false
    override val isMediaInformationSession = true
    override val isFFprobe = false

    var mediaInformation: Any? = null

    val hasMediaInformation: Boolean get() = mediaInformation != null
}
