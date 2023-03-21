package com.example.ffmpegkit.sessions

import com.example.ffmpegkit.Log
import com.example.ffmpegkit.SessionState
import com.example.ffmpegkit.callbacks.LogCallback

class FFmpegSession(arguments: List<String>,
                    logCallback: LogCallback
): AbstractSession(arguments, logCallback) {
    override val allLogs: List<Log>
        get() = TODO("Not yet implemented")
    override val output: String
        get() = TODO("Not yet implemented")
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