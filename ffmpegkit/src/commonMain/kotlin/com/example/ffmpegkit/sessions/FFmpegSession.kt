package com.example.ffmpegkit.sessions

import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback

class FFmpegSession(arguments: List<String>,
                    logCallback: LogCallback? = null,
                    val statisticsCallback: StatisticsCallback? = null
): AbstractSession(arguments, logCallback) {
    override val isFFmpeg = true
    override val isFFprobe = false
    override val isMediaInformationSession = false
}
