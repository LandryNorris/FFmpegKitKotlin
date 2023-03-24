package com.example.ffmpegkit.sessions

import com.example.ffmpegkit.Log
import com.example.ffmpegkit.ReturnCode
import com.example.ffmpegkit.SessionState
import com.example.ffmpegkit.callbacks.LogCallback
import kotlinx.datetime.Instant

interface Session {
    val logCallback: LogCallback?
    val sessionId: Long
    val createTime: Instant
    val startTime: Instant?
    val endTime: Instant?
    val duration: Long?
    val arguments: List<String>
    val command: String
    val allLogs: List<Log>
    val logs: List<Log>
    val output: String
    val state: SessionState
    val returnCode: ReturnCode?

    val isFFmpeg: Boolean
    val isFFprobe: Boolean
    val isMediaInformationSession: Boolean

    val allLogsAsString: String

    suspend fun getAllLogsAsString(timeout: Int): String
    suspend fun getAllLogs(timeout: Int): List<Log>
    fun addLog(log: Log)
    fun cancel()
}