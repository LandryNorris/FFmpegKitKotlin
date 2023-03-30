package com.example.ffmpegkit

import com.example.ffmpegkit.callbacks.LogCallback
import com.example.ffmpegkit.callbacks.StatisticsCallback
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

    @OptIn(ExperimentalTime::class)
    suspend fun getMediaInformation(path: String, timeout: Int) {
        val args = createMediaInformationArgs(path)
        val session = executeWithArguments(args)

        val outputMeasured = measureTimedValue { session.getAllLogs(timeout) }
        val output = outputMeasured.value

        val outputString = output
            .joinToString("") { if(it.level == Level.AV_LOG_STDERR) it.message else "" }
        println("Getting output took ${outputMeasured.duration}")

        println("Output is $outputString")

        val ffprobeOutput = json.decodeFromString(FFprobeOutput.serializer(), outputString)

        println("FFprobe output: $ffprobeOutput")
    }

    private fun createMediaInformationArgs(path: String): List<String> {
        return listOf("-v", "error", "-hide_banner", "-print_format", "json", "-show_format",
            "-show_streams", "-show_chapters", "-i", path)
    }
}

private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}
