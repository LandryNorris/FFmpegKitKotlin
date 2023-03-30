@file:Suppress("UNUSED")

package com.example.ffmpegkit

import com.example.ffmpegkit.sessions.FFmpegSession
import com.example.ffmpegkit.sessions.FFprobeSession
import com.example.ffmpegkit.sessions.MediaInformationSession
import com.example.ffmpegkit.sessions.Session

private val sessionsMap = mutableMapOf<Long, Session>()
private val sessions = mutableListOf<Session>()
private var historySize = 100

val FFmpegKitConfig.lastSession get() = sessions.last()
val FFmpegKitConfig.lastCompleted get() = sessions.lastOrNull { it.state == SessionState.COMPLETED }

val FFmpegKitConfig.ffmpegSessions get() = sessions.filterIsInstance<FFmpegSession>()
val FFmpegKitConfig.ffprobeSessions get() = sessions.filterIsInstance<FFprobeSession>()
val FFmpegKitConfig.mediaInformationSessions get() = sessions.filterIsInstance<MediaInformationSession>()

fun FFmpegKitConfig.setMaxHistorySize(newSize: Int) {
    historySize = newSize
    trimToSize()
}

fun FFmpegKitConfig.addSession(session: Session) {
    val sessionAlreadyAdded = sessionsMap.containsKey(session.sessionId)
    if(!sessionAlreadyAdded) {
        sessionsMap[session.sessionId] = session
        sessions.add(session)
        trimToSize()
    }
}

private fun FFmpegKitConfig.trimToSize() {
    while(sessions.size > historySize) {
        val oldest = if(sessions.isNotEmpty()) sessions.removeAt(0) else null
        if(oldest != null) {
            sessionsMap.remove(oldest.sessionId)
        }
    }
}

fun FFmpegKitConfig.clearSessions() {
    sessions.clear()
    sessionsMap.clear()
}
