package com.example.ffmpegkit.sessions

import com.example.ffmpegkit.Log
import com.example.ffmpegkit.SessionState
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback

class FFmpegSession(arguments: List<String>,
                    logCallback: LogCallback? = null,
                    val statisticsCallback: StatisticsCallback? = null
): AbstractSession(arguments, logCallback) {
    override val allLogs = mutableListOf<Log>()
    override val isFFmpeg = true
    override val isFFprobe = false
    override val isMediaInformationSession = false

    override fun addLog(log: Log) {
        logs.add(log)
    }

    override fun cancel() {
        if(state == SessionState.RUNNING) {
            //cancel by ID.
        }
    }
}