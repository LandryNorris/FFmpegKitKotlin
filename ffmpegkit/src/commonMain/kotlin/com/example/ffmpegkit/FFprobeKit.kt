package com.example.ffmpegkit

import com.example.ffmpegkit.FFmpegKitConfig.ffprobeExecute
import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.sessions.DEFAULT_TIMEOUT_FOR_ASYNCHRONOUS_MESSAGES_IN_TRANSMIT
import com.example.ffmpegkit.sessions.FFprobeSession
import com.example.ffmpegkit.sessions.MediaInformationSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.coroutines.suspendCoroutine
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

object FFprobeKit {
    fun executeWithArgumentsBlocking(arguments: List<String>,
                                     logCallback: LogCallback? = null
    ): FFprobeSession {
        val session = FFprobeSession(arguments, logCallback)
        ffprobeExecute(session)
        return session
    }

    fun executeBlocking(command: String,
                        logCallback: LogCallback? = null): FFprobeSession {
        return executeWithArgumentsBlocking(parseArguments(command),
            logCallback)
    }

    /**
     * Execute a ffprobe command using coroutines.
     */
    suspend fun executeWithArguments(arguments: List<String>,
                                     logCallback: LogCallback? = null
    ): FFprobeSession {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                val session = executeWithArgumentsBlocking(arguments, logCallback)
                continuation.resumeWith(Result.success(session))
            }
        }
    }

    suspend fun execute(command: String,
                        logCallback: LogCallback? = null): FFprobeSession {
        return executeWithArguments(parseArguments(command), logCallback)
    }

    suspend fun getMediaInformationFromCommand(command: String,
                                               logCallback: LogCallback? = null
    ): MediaInformationSession {
        val args = parseArguments(command)
        val session = MediaInformationSession(args, logCallback)
        getMediaInformation(session, DEFAULT_TIMEOUT_FOR_ASYNCHRONOUS_MESSAGES_IN_TRANSMIT)

        return session
    }

    suspend fun getMediaInformation(path: String, logCallback: LogCallback? = null,
                                    timeout: Int): MediaInformationSession {
        val args = createMediaInformationArgs(path)
        val session = MediaInformationSession(args, logCallback)
        getMediaInformation(session, timeout)

        return session
    }

    suspend fun getMediaInformation(session: MediaInformationSession, timeout: Int) {
        session.startRunning()
        val ffprobeSession = executeWithArguments(session.arguments,
            logCallback = session.logCallback)
        session.complete(ffprobeSession.returnCode!!)

        if(ffprobeSession.returnCode?.isSuccess == true) {
            val output = ffprobeSession.getAllLogs(timeout)

            val outputString = output
                .joinToString("") { if(it.level == Level.AV_LOG_STDERR) it.message else "" }

            val ffprobeOutput = json.decodeFromString(FFprobeOutput.serializer(), outputString)

            session.mediaInformation = mediaInformation(ffprobeOutput.format, ffprobeOutput.streams)
        }
    }

    fun listFFprobeSessions() = FFmpegKitConfig.ffprobeSessions
    fun listMediaInformationSessions() = FFmpegKitConfig.mediaInformationSessions

    private fun createMediaInformationArgs(path: String): List<String> {
        return listOf("-v", "error", "-hide_banner", "-print_format", "json", "-show_format",
            "-show_streams", "-show_chapters", "-i", path)
    }
}

private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}
