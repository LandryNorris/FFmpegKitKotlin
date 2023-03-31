package com.example.ffmpegkit.sessions

import com.example.ffmpegkit.*
import com.example.ffmpegkit.callbacks.LogCallback
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

abstract class AbstractSession(override val arguments: List<String>,
                               override val logCallback: LogCallback?
): Session {
    private val clock get() = Clock.System

    override val sessionId = idGenerator.inc()-1
    override val createTime = clock.now()
    override val logs = mutableListOf<Log>()
    override var state: SessionState = SessionState.CREATED

    override var startTime: Instant? = null
    override var endTime: Instant? = null
    override var returnCode: ReturnCode? = null
    override val duration get() = if(endTime != null && startTime != null) {
        (endTime!! - startTime!!).inWholeMilliseconds
    } else {
        0
    }

    override val command get() = argumentsToString(arguments)

    init {
        FFmpegKitConfig.addSession(this)
    }

    override suspend fun getAllLogs(timeout: Int): List<Log> {
        waitForAsynchronousMessagesInTransmit(timeout)
        return logs
    }

    override val allLogs: List<Log>
        get() = runBlocking { getAllLogs(DEFAULT_TIMEOUT_FOR_ASYNCHRONOUS_MESSAGES_IN_TRANSMIT) }

    override val output get() = allLogsAsString

    override val allLogsAsString: String
        get() = runBlocking {
            getAllLogsAsString(DEFAULT_TIMEOUT_FOR_ASYNCHRONOUS_MESSAGES_IN_TRANSMIT)
        }

    override suspend fun getAllLogsAsString(timeout: Int): String {
        waitForAsynchronousMessagesInTransmit(timeout)
        if(thereAreAsynchronousMessagesInTransmit()) {
            println("getAllLogsAsString was called to return all logs but there are still logs " +
                    "being transmitted for session id $sessionId.")
        }

        return getLogsAsString()
    }

    private fun getLogsAsString() = buildString {
        for(log in logs) {
            append(log.message)
        }
    }

    override fun addLog(log: Log) {
        logs.add(log)
    }

    suspend fun waitForAsynchronousMessagesInTransmit(timeout: Int) {
        val start = clock.now()

        while(thereAreAsynchronousMessagesInTransmit() &&
            clock.now().minus(start).inWholeMilliseconds < timeout) {
            delay(100)
        }
    }

    fun thereAreAsynchronousMessagesInTransmit() =
        FFmpegKitConfig.messagesInTransmit(sessionId) != 0

    fun startRunning() {
        state = SessionState.RUNNING
        startTime = clock.now()
    }

    fun complete(result: ReturnCode) {
        endTime = clock.now()
        returnCode = result
        state = SessionState.COMPLETED
    }

    fun fail(exception: Exception) {
        state = SessionState.FAILED
        endTime = clock.now()
    }

    override fun cancel() {
        FFmpegKit.cancel(sessionId)
    }
}

private val idGenerator by atomic(0L)
const val DEFAULT_TIMEOUT_FOR_ASYNCHRONOUS_MESSAGES_IN_TRANSMIT = 5000
