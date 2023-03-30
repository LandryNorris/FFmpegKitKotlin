package com.example.ffmpegkit.sessions

import com.example.ffmpegkit.callbacks.LogCallback

class FFprobeSession(arguments: List<String>,
                    logCallback: LogCallback? = null
): AbstractSession(arguments, logCallback) {
    override val isFFmpeg = false
    override val isFFprobe = true
    override val isMediaInformationSession = false
}
